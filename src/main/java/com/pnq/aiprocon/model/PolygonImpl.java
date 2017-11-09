package com.pnq.aiprocon.model;

import com.pnq.aiprocon.helper.PolygonHelper;

import java.awt.*;

public class PolygonImpl extends Polygon {
    private Point[] points; //diem
    private double[] vertices; // goc
    private double[] edges; // canh

    public PolygonImpl(int xpoint[], int[] ypoint, int npoint) {
        super(xpoint, ypoint, npoint);

        vertices = new double[npoint];
        edges = new double[npoint];
        points = new Point[npoint];

        // point
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point();
            points[i].x = xpoint[i];
            points[i].y = ypoint[i];
        }

        // add Edges
        for (int i = 0; i < points.length; i++) {
            xpoint[i] = points[i].x;
            ypoint[i] = points[i].y;
            if (i != points.length - 1) {
                edges[i] = PolygonHelper.getEdge(points[i], points[i + 1]);
            } else {
                edges[i] = PolygonHelper.getEdge(points[i], points[0]);
            }
        }

        // add vertices
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                vertices[i] = PolygonHelper.getVertice(points[points.length - 1], points[i], points[i + 1], points);
            } else if (i == points.length - 1) {
                vertices[i] = PolygonHelper.getVertice(points[i - 1], points[i], points[0], points);

            } else {
                vertices[i] = PolygonHelper.getVertice(points[i - 1], points[i], points[i + 1], points);
            }
        }

        // create Polygon


    }

    public Point[] getPoints() {
        return points;
    }

    public double[] getVertices() {
        return vertices;
    }

    public double[] getEdges() {
        return edges;
    }

}
