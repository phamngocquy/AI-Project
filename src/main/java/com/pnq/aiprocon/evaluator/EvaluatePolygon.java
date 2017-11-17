package com.pnq.aiprocon.evaluator;

import com.pnq.aiprocon.helper.MergePolygon;
import com.pnq.aiprocon.helper.ReadFileHelper;
import com.pnq.aiprocon.model.EvaluateObject;
import com.pnq.aiprocon.model.PolygonImpl;
import com.pnq.aiprocon.render.DrawPolyPanel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class EvaluatePolygon {

    public EvaluateObject execEvaluate(PolygonImpl polygon1, PolygonImpl polygon2) {

        EvaluateObject evaluateObject = new EvaluateObject();
        evaluateObject.setMark(Double.MIN_VALUE);


        for (int i = 0; i < polygon1.xpoints.length; i++) {
            for (int j = 0; j < polygon2.xpoints.length; j++) {
                // lay mot goc cua polygon 1,,, duyet tung goc polygon 2 de xet

                // tinh diem phu hop cua goc i va j
                EvaluateObject object = caculateMark2Vertices(polygon1, i, polygon2, j);


                // lay EvaluateObject co diem phu hop cao nhat
                if (object.getMark() > evaluateObject.getMark()) {
                    evaluateObject = object;
                }

            }
        }

        return evaluateObject;
    }

    // hinh 2 ghep vao hinh 1
    private EvaluateObject caculateMark2Vertices(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2) {
        EvaluateObject evaluateObject = new EvaluateObject();

        evaluateObject.setMark(Double.MIN_VALUE);
        evaluateObject.setPolygon1(polygon1);
        evaluateObject.setVertex1(pos1);
        evaluateObject.setVertex2(pos2);

        // xoay thu tu 1 - 7 lan, tra ve hinh da ghep
        for (int i = 1; i <= 7; i++) {
            // polygon thu 2 sau khi da ghep
            PolygonImpl polygon = MergePolygon.mapP2toP1(polygon1, pos1, polygon2, pos2, i);

            // tinh diem cua hinh1,goc pos1, voi hinh2(moi),goc pos2
            // hai hinh da duoc ghep
            double mark = generateMark(polygon1, pos1, polygon, pos2, i);


            // lay diem lon nhat, tra ve so lan quay
            if (mark > evaluateObject.getMark()) {
                evaluateObject.setMark(mark);
                evaluateObject.setPolygon2(polygon);
                evaluateObject.setNumOfRotate(i);
            }
        }
        return evaluateObject;
    }

    public double generateMark(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2, int flip) {

        // pos1 : vi tri goc duoc ghep cua polygon 1
        // pos2 :  vi tri goc duoc ghep cua polygon 2
        // todo [Namdv] viet ham tinh diem phu hop 2 polygon (2 polygon da duoc ghep,)
        double mark = 0;
        //tinh diem theo goc
        //neu 2 goc xap xi bang nhau thi se cong 50 diem,
        //con lai se tinh theo cong thuc mark = 50 * (goc nho/ goc lon)
        if ((polygon1.getVertices()[pos1] + polygon2.getVertices()[pos2]) <= 360
                && checkDe(polygon1, polygon2) == false) {
            if ((polygon2.getVertices()[pos2] + polygon1.getVertices()[pos1]) >= 359
                    || (polygon2.getVertices()[pos2] + polygon1.getVertices()[pos1]) <= 360) {
                mark += 50;
            } else {
                if (polygon1.getVertices()[pos1] <= polygon2.getVertices()[pos2]) {
                    mark += 50 * (1 - polygon1.getVertices()[pos1] / polygon2.getVertices()[pos2]);
                } else {
                    mark += 50 * (1 - polygon2.getVertices()[pos2] / polygon1.getVertices()[pos1]);
                }
            }
            // tinh diem theo canh
            if (flip == 0 || flip == 1 || flip == 2 || flip == 3) {
                if (pos1 == 0 && pos2 != 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2 - 1]) {
                        mark += 25 * (polygon2.getEdges()[pos2 - 1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2 - 1]);
                    }

                    if (polygon1.getEdges()[polygon1.getEdges().length - 1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[polygon1.getEdges().length - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[polygon1.getEdges().length - 1] / polygon2.getEdges()[pos2]);
                    }
                } else if (pos1 == 0 && pos2 == 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[polygon2.getEdges().length - 1]) {
                        mark += 25 * (polygon2.getEdges()[polygon2.getEdges().length - 1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[polygon2.getEdges().length - 1]);
                    }

                    if (polygon1.getEdges()[polygon1.getEdges().length - 1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[polygon1.getEdges().length - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[polygon1.getEdges().length - 1] / polygon2.getEdges()[pos2]);
                    }
                } else if (pos1 != 0 && pos2 == 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[polygon2.getEdges().length - 1]) {
                        mark += 25 * (polygon2.getEdges()[polygon2.getEdges().length - 1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[polygon2.getEdges().length - 1]);
                    }

                    if (polygon1.getEdges()[pos1 - 1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1 - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1 - 1] / polygon2.getEdges()[pos2]);
                    }
                } else if (pos1 != 0 && pos2 != 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2 - 1]) {
                        mark += 25 * (polygon2.getEdges()[pos2 - 1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2 - 1]);
                    }

                    if (polygon1.getEdges()[pos1 - 1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1 - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1 - 1] / polygon2.getEdges()[pos2]);
                    }

                }
            } else if (flip == 4 || flip == 5 || flip == 6 || flip == 7) {
                if (pos1 == 0 && pos2 != 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }

                    if (polygon1.getEdges()[polygon1.getEdges().length - 1] >= polygon2.getEdges()[pos2 - 1]) {
                        mark += 25 * (polygon2.getEdges()[pos2 - 1] / polygon1.getEdges()[polygon1.getEdges().length - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[polygon1.getEdges().length - 1] / polygon2.getEdges()[pos2 - 1]);
                    }
                } else if (pos1 == 0 && pos2 == 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }

                    if (polygon1.getEdges()[polygon1.getEdges().length - 1] >= polygon2.getEdges()[polygon2.getEdges().length - 1]) {
                        mark += 25 * (polygon2.getEdges()[polygon2.getEdges().length - 1] / polygon1.getEdges()[polygon1.getEdges().length - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[polygon1.getEdges().length - 1] / polygon2.getEdges()[polygon2.getEdges().length - 1]);
                    }
                } else if (pos1 != 0 && pos2 == 0) {
                    if (polygon1.getEdges()[pos1 - 1] >= polygon2.getEdges()[polygon2.getEdges().length - 1]) {
                        mark += 25 * (polygon2.getEdges()[polygon2.getEdges().length - 1] / polygon1.getEdges()[pos1 - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1 - 1] / polygon2.getEdges()[polygon2.getEdges().length - 1]);
                    }

                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }
                } else if (pos1 != 0 && pos2 != 0) {
                    if (polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]) {
                        mark += 25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }

                    if (polygon1.getEdges()[pos1 - 1] >= polygon2.getEdges()[pos2 - 1]) {
                        mark += 25 * (polygon2.getEdges()[pos2 - 1] / polygon1.getEdges()[pos1 - 1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1 - 1] / polygon2.getEdges()[pos2 - 1]);
                    }
                }
            }

        } else {
            mark = 0;
        }

        return mark;
    }

    // neu de : tra ve true;
    // khong de : tra ve false
    public boolean checkDe(PolygonImpl polygon1, PolygonImpl polygon2) {
        // todo [Phuong] viet ham check de cua 2 polygon.
        for (int i = 0; i < polygon1.npoints; i++) {
            for (int j = 0; j < polygon2.npoints; j++) {

                int i_plus = (i == polygon1.npoints - 1) ? 0 : i + 1;
                int j_plus = (j == polygon2.npoints - 1) ? 0 : j + 1;

                Point point1_1 = new Point(polygon1.xpoints[i], polygon1.ypoints[i]);
                Point point1_2 = new Point(polygon1.xpoints[i_plus], polygon1.ypoints[i_plus]);

                Point point2_1 = new Point(polygon2.xpoints[j], polygon2.ypoints[j]);
                Point point2_2 = new Point(polygon2.xpoints[j_plus], polygon2.ypoints[j_plus]);

                boolean result_tmp = isDe(point1_1, point1_2, point2_1, point2_2);
                if (result_tmp) {
                    // hai doan thang cat nhau tai mot diem khac dau mut --> de
                    return true;
                }
            }
        }
        return false;
    }

    // true : hai hinh de len nhau, false hai hinh khong de
    private boolean isDe(Point point1_1, Point point1_2, Point point2_1, Point point2_2) {
        // neu hai duong thang cat nhau tai mot diem khong phai dau mut thi la de

        int x_ba = point1_2.x - point1_1.x;
        int y_ba = point1_2.y - point1_1.y;

        int x_dc = point2_2.x - point2_1.x;
        int y_dc = point2_2.y - point2_1.y;


        if (x_ba * y_dc == y_ba * x_dc) {
            return false;
        }

        Line2D line_1 = new Line2D.Double(point1_1, point1_2);
        Line2D line_2 = new Line2D.Double(point2_1, point2_2);

        if (line_1.intersectsLine(line_2)) {
            // phuong trinh duong thang thu nhat
            if (x_ba * (point2_1.y - point1_1.y) - y_ba * (point2_1.x - point1_1.x) == 0) {
                // dau mut nam tren duong thang
                return false;
            }
            if (x_ba * (point2_2.y - point1_1.y) - y_ba * (point2_2.x - point1_1.x) == 0) {
                // dau mut nam tren duong thang
                return false;
            }

            if (x_dc * (point1_1.y - point2_1.y) - y_dc * (point1_1.x - point2_1.x) == 0) {
                // dau mut nam tren duong thang
                return false;
            }
            if (x_dc * (point1_2.y - point2_1.y) - y_dc * (point1_2.x - point2_1.x) == 0) {
                // dau mut nam tren duong thang
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        ReadFileHelper readFileHelper = new ReadFileHelper();
        EvaluatePolygon evaluatePolygon = new EvaluatePolygon();
        String filePath = "/home/javis/Desktop/procon.txt";

        List polygons = readFileHelper.readFile(filePath);


        //  System.out.println(evaluatePolygon.checkDe((PolygonImpl) polygons.get(4), (PolygonImpl) polygons.get(5)));
        System.out.println(evaluatePolygon.execEvaluate((PolygonImpl) polygons.get(1), (PolygonImpl) polygons.get(2)).getMark());

        DrawPolyPanel drawPolyPanel = new DrawPolyPanel();
        drawPolyPanel.setPolygons(polygons.subList(1, 3));
        drawPolyPanel.displayPolygons(5);
    }

}
