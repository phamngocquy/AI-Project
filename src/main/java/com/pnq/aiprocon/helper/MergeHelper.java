package com.pnq.aiprocon.helper;

import com.pnq.aiprocon.model.PolygonImpl;
import com.pnq.aiprocon.render.DrawPolyPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MergeHelper {

    /**
     *
     * @param p1
     * @param pos1
     * @param p2
     * @param pos2
     * @param times gia tri tu 0 - 7
     * @return
     */
    public static PolygonImpl mapP2toP1(Object p1, int pos1, Object p2, int pos2, int times) {
        PolygonImpl polygon1 = (PolygonImpl) p1;
        PolygonImpl polygon2 = (PolygonImpl) p2;

        // thuc hien xoay [times] buoc
        // 0 khong xoay
        // 1 xoay 90 do
        // 2 xoay 180
        // 3 xoay 270
        // 4 lat
        // 5 xoay 90
        // 6 xoay 180
        // 7 xoay 270
        PolygonImpl polygon_step1 = polygon2;
        for (int i = 1; i <= times; i++) {
            if (i == 4 ) {
                polygon_step1 = polygon2.flipOx();
            } else {
                polygon_step1 = polygon_step1.rolatic();
            }

        }

        // di chuyen hinh den vi tri dich

        Point point1 = new Point(polygon1.xpoints[pos1], polygon1.ypoints[pos1]);
        Point point2 = new Point(polygon_step1.xpoints[pos2], polygon_step1.ypoints[pos2]);
        int x_tmp = point1.x - point2.x;
        int y_tmp = point1.y - point2.y;

        System.out.println(x_tmp + ":" + y_tmp);

        // step 1  : di chuyen hinh den toa do xac dinh
        polygon_step1 = polygon_step1.move(x_tmp, y_tmp);

        // step 2 : quay hinh [times] lan


        return polygon_step1;
    }

    public static void main(String[] args) {
        ReadFileHelper readFileHelper = new ReadFileHelper();

        String filePath = "C:\\Users\\Capricorn.uet\\Desktop\\input.txt";
        java.util.List polygons = readFileHelper.readFile(filePath);
        //System.out.println(Integer.MA);
        // render
        DrawPolyPanel drawPolyPanel = new DrawPolyPanel();

        List polygon_tmp = new ArrayList();
        polygon_tmp.add(polygons.get(0));
        polygon_tmp.add(polygons.get(1));
       // polygon_tmp.add(polygons.get(2));
        //polygon_tmp.add(mapP2toP1(polygons.get(0), 3, polygons.get(2), 1, 8));
      /*  PolygonImpl polygonImpl = ((PolygonImpl) polygons.get(0)).move(0, 20);
        polygon_tmp.add(polygonImpl);
        PolygonImpl polygonImpl2 = ((PolygonImpl) polygons.get(0)).flipOx().move(0, 40);
        polygon_tmp.add(polygonImpl2);
        PolygonImpl polygonImpl3 = ((PolygonImpl) polygons.get(0)).flipOy().move(50, 0);
        polygon_tmp.add(polygonImpl3);
        PolygonImpl polygonImpl4 = ((PolygonImpl) polygons.get(0)).rolatic().move(40, 45);
        polygon_tmp.add(polygonImpl4);*/


        drawPolyPanel.setPolygons(polygon_tmp);
        drawPolyPanel.displayPolygons(5);

    }
}
