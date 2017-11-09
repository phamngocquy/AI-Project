package com.pnq.aiprocon.model;

import java.awt.*;

public class PolygonHelper {

    public static double getEdge(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    public static double getVertice(Point p1, Point p2, Point p3, Point[] points) {

        Point p1p2 = new Point(p2.x - p1.x, p2.y - p1.y);
        Point p3p2 = new Point(p2.x - p3.x, p2.y - p3.y);
        double cosVertice = (p1p2.x * p1p2.y + p3p2.x * p3p2.y) / (Math.hypot(p1p2.x, p1p2.y) * Math.hypot(p3p2.x, p3p2.y));

        Polygon polygon = new Polygon();
        for (Point p : points) {
            if (equals(p, p2)) polygon.addPoint(p.x, p.y);
        }

        /*goc lom
        * goc loi*/
        if (polygon.contains(p2)) {
            return 360 - Math.acos(cosVertice * 180 / Math.PI);
        } else {
            return Math.acos(cosVertice * 180 / Math.PI);
        }
    }

    private static boolean equals(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

}
