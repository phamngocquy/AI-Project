package com.pnq.aiprocon.evaluator;

import com.pnq.aiprocon.helper.MergePolygon;
import com.pnq.aiprocon.model.EvaluateObject;
import com.pnq.aiprocon.model.PolygonImpl;
import com.pnq.aiprocon.model.PolygonPos;

import java.awt.*;
import java.awt.geom.Line2D;

public class EvaluatePolygon3 {
    // is 90:true : chi con goc 90 do
    public EvaluateObject execEvaluate2(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, PolygonPos posList
            , boolean is90) {

        EvaluateObject evaluateObject = new EvaluateObject();
        evaluateObject.setMark(-100);
//
//                        for (int j = 0; j < polygon2.xpoints.length; j++) {
//                        // lay mot goc cua polygon 1,,, duyet tung goc polygon 2 de xet
//                        if{
//                            // tinh diem phu hop cua goc i va j
//                            EvaluateObject object = caculateMark2Vertices(polygon1, pos1, polygon2, j);
//
//
//                            // lay EvaluateObject co diem phu hop cao nhat
//                            if (object.getMark() > evaluateObject.getMark()) {
//                                evaluateObject = object;
//                            }
//                        }
//
//                    }
//
        double restVertices = 360 - polygon1.getVertices()[pos1];
//        for (int i = 0; i < polygon1.xpoints.length; i++) {
        for (int j = 0; j < polygon2.xpoints.length; j++) {
            // lay mot goc cua polygon 1,,, duyet tung goc polygon 2 de xet

            boolean tmpBoolean = true;
            for (int k = 0; k < posList.getPos2().size(); k++) {
//                    System.out.print(posList.getPos2().get(k) + " ");
//                    System.out.println(j + " " + (posList.getPos2().get(k)==3));
                if (j == posList.getPos2().get(k)) {
                    tmpBoolean = false;
                    break;
                }
            }
//                if (posList.getPos2().size() != 0)
//                    System.out.println("###############################");
            if (tmpBoolean && ((polygon2.getVertices()[j] < restVertices + 1 && polygon2.getVertices()[j] + 1 > restVertices)
                    || (polygon2.getVertices()[j] < restVertices / 2 + 1))) {
                // tinh diem phu hop cua goc i va j
                EvaluateObject object = caculateMark2Vertices(polygon1, pos1, polygon2, j, is90);


                if (object.getMark() > evaluateObject.getMark()) {
                    evaluateObject = object;
                }
            }

            // lay EvaluateObject co diem phu hop cao nhat


        }
//        }

        return evaluateObject;
    }


//
//    boolean test = false;
//    public EvaluateObject execEvaluate2(PolygonImpl polygon1, int pos1, PolygonImpl polygon2) {
//
//        EvaluateObject evaluateObject = new EvaluateObject();
//        double restVertices = 360 - polygon1.getVertices()[pos1];
//        for (int j = 0; j < polygon2.xpoints.length; j++) {
//            // lay mot goc cua polygon 1,,, duyet tung goc polygon 2 de xet
//            if((polygon2.getVertices()[j] < restVertices + 1 && polygon2.getVertices()[j] + 1 > restVertices)
//                    || (polygon2.getVertices()[j] < restVertices/2 + 1 )){
//            // tinh diem phu hop cua goc i va j
//                EvaluateObject object = caculateMark2Vertices(polygon1, pos1, polygon2, j);
//
//
//                // lay EvaluateObject co diem phu hop cao nhat
//                if (object.getMark() > evaluateObject.getMark()) {
//                    evaluateObject = object;
//                }
//            }
//
//        }
//
//        return evaluateObject;
//    }


    // hinh 2 ghep vao hinh 1
    private EvaluateObject caculateMark2Vertices(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2, boolean is90) {
        EvaluateObject evaluateObject = new EvaluateObject();

        evaluateObject.setMark(-100);
        evaluateObject.setPolygon1(polygon1);
        evaluateObject.setVertex1(pos1);
        evaluateObject.setVertex2(pos2);

        // xoay thu tu 1 - 7 lan, tra ve hinh da ghep
        for (int i = 0; i <= 7; i++) {
            // polygon thu 2 sau khi da ghep
            PolygonImpl polygon = MergePolygon.mapP2toP1(polygon1, pos1, polygon2, pos2, i);

            // tinh diem cua hinh1,goc pos1, voi hinh2(moi),goc pos2
            // hai hinh da duoc ghep
            double mark;
            if (is90){
                // chi con goc 90 do
                mark = generateMark90(polygon1, pos1, polygon, pos2, i);
            }else {
                mark = generateMark(polygon1, pos1, polygon, pos2, i);
            }



            // lay diem lon nhat, tra ve so lan quay
            if (mark > evaluateObject.getMark()) {
                evaluateObject.setMark(mark);
                evaluateObject.setPolygon2(polygon);
                evaluateObject.setNumOfRotate(i);
            }
        }
        return evaluateObject;
    }

    private double generateMark90(PolygonImpl polygon1, int pos1, PolygonImpl polygon, int pos2, int i) {
        // pos1 : vi tri goc duoc ghep cua polygon 1
        // pos2 :  vi tri goc duoc ghep cua polygon 2
        // todo [Namdv] ham tinh diem khi chi con cac goc 90 do
        return 0;
    }

    public double generateMark(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2, int flip) {

        // pos1 : vi tri goc duoc ghep cua polygon 1
        // pos2 :  vi tri goc duoc ghep cua polygon 2
        // todo [Namdv] viet ham tinh diem phu hop 2 polygon (2 polygon da duoc ghep,)
        double mark = 0;

        if ((polygon1.getVertices()[pos1] + polygon2.getVertices()[pos2]) <= 360
                && checkDe(polygon1, polygon2) == false) {
            if ((polygon2.getVertices()[pos2] + polygon1.getVertices()[pos1]) >= 359
                    && (polygon2.getVertices()[pos2] + polygon1.getVertices()[pos1]) <= 360) {
                mark += 50;
                if (polygon1.getVertices()[pos1] == 90 || polygon1.getVertices()[pos1] == 270) {
                    mark -= 10;
                }
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

                boolean result_tmp = isDe(polygon1, polygon2, point1_1, point1_2, point2_1, point2_2);

           /*     if (polygon1.contains(new Point2D.Double(point2_1.x, point2_1.y)) ||
                        polygon2.contains(new Point2D.Double(point1_1.x, point1_1.y))) {
                    return true;
                }*/

                if (result_tmp) {
                    // hai doan thang cat nhau tai mot diem khac dau mut --> de
                    return true;
                }
            }
        }
        return false;
    }

    // true : hai hinh de len nhau, false hai hinh khong de
    // point1_1 , point1_2 : polygon1
    private boolean isDe(PolygonImpl polygon1, PolygonImpl polygon2, Point point1_1, Point point1_2, Point point2_1, Point point2_2) {
        // neu hai duong thang cat nhau tai mot diem khong phai dau mut thi la de

        int x_ba = point1_2.x - point1_1.x;
        int y_ba = point1_2.y - point1_1.y;

        int x_dc = point2_2.x - point2_1.x;
        int y_dc = point2_2.y - point2_1.y;


        Line2D line_1 = new Line2D.Double(point1_1, point1_2);
        Line2D line_2 = new Line2D.Double(point2_1, point2_2);


        boolean result = false;
        if (polygon1.contains(point2_1)) {
            if (!isOnEdges(polygon1, point2_1)) {
                result = true;
            }
        }
        if (polygon1.contains(point2_2)) {
            if (!isOnEdges(polygon1, point2_2)) {
                result = true;
            }
        }
        if (polygon2.contains(point1_1)) {
            if (!isOnEdges(polygon2, point1_1)) {
                result = true;
            }

        }
        if (polygon2.contains(point1_2)) {
            if (!isOnEdges(polygon2, point1_2)) {
                result = true;
            }
        }
        //return result;

        if (result == false) {
            // khong co diem nao nam trong da giac
            if (x_ba * y_dc == y_ba * x_dc) {

            }

            // //
            // xet diem trung binh cua canh
            // something wrong
            //     Point point_1 = new Point((point1_1.x + point1_2.x) / 2, (point1_1.y + point1_2.y) / 2);
            double x1 = (double) (point1_1.x + point1_2.x) / 2;
            double y1 = (double) (point1_1.y + point1_2.y) / 2;
            if (polygon2.contains(x1, y1)) {
                if (!isOnEdgesV2(polygon2, x1, y1)) {

                    // diem nam trong polygon --> de

                    return true;

                }
            }
            double x2 = (double) (point2_1.x + point2_2.x) / 2;
            double y2 = (double) (point2_1.y + point2_2.y) / 2;

            // Point point_2 = new Point(, );

            if (polygon1.contains(x2, y2)) {
                if (!isOnEdgesV2(polygon1, x2, y2)) {

                    // diem nam trong polygon --> de

                    return true;

                }
            }


            /////


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


                //    return true;
            } else {
                return false;
            }
        }
        return true;




/*
        if (x_ba * y_dc == y_ba * x_dc) {
            // return false;
            // xet xem diem co nam trong da giac hay khong
            boolean result = false;
            if (polygon1.contains(point2_1)) {
                if (!isOnEdges(polygon1, point2_1)) {
                    result = true;
                }
            }
            if (polygon1.contains(point2_2)) {
                if (!isOnEdges(polygon1, point2_2)) {
                    result = true;
                }
            }
            if (polygon2.contains(point1_1)) {
                if (!isOnEdges(polygon2, point1_1)) {
                    result = true;
                }

            }
            if (polygon2.contains(point1_2)) {
                if (!isOnEdges(polygon2, point1_2)) {
                    result = true;
                }
            }
            return result;

        }

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
        }*/
    }

    // true : diem nam tren canh
    // false : diem khong nam tren canh
    public boolean isOnEdges(PolygonImpl polygon, Point point) {
        for (int i = 0; i < polygon.npoints; i++) {
            int i_plus = i + 1;
            if (i == polygon.npoints - 1) {
                i_plus = 0;
            }

            Point point1 = new Point(polygon.xpoints[i], polygon.ypoints[i]); // (b)
            Point point2 = new Point(polygon.xpoints[i_plus], polygon.ypoints[i_plus]); // (c)
            // (a) point
            int x1 = point1.x - point.x;
            int y1 = point1.y - point.y;
            int x2 = point2.x - point.x;
            int y2 = point2.y - point.y;
            if (x1 * x2 + y1 * y2 <= 0) {
                // diem nam trong
                // xet xem diem co nam trong duong thang khong
                // phuong trinh duong thang giua hai diem
                if ((point2.x - point1.x) * (point.y - point1.y) - (point2.y - point1.y) * (point.x - point1.x) == 0) {
                    // diem thuoc canh
                    return true;
                }
            }

        }

        return false;
    }

    public boolean isOnEdgesV2(PolygonImpl polygon, double x, double y) {
        for (int i = 0; i < polygon.npoints; i++) {
            int i_plus = i + 1;
            if (i == polygon.npoints - 1) {
                i_plus = 0;
            }

            Point point1 = new Point(polygon.xpoints[i], polygon.ypoints[i]); // (b)
            Point point2 = new Point(polygon.xpoints[i_plus], polygon.ypoints[i_plus]); // (c)
            // (a) point
            double x1 = point1.x - x;
            double y1 = point1.y - y;
            double x2 = point2.x - x;
            double y2 = point2.y - y;
            if (x1 * x2 + y1 * y2 <= 0) {
                // diem nam trong
                // xet xem diem co nam trong duong thang khong
                // phuong trinh duong thang giua hai diem
                if ((point2.x - point1.x) * (y - point1.y) - (point2.y - point1.y) * (x - point1.x) == 0) {
                    // diem thuoc canh
                    return true;
                }
            }

        }

        return false;
    }

    public static void main(String[] args) {


    }

}
