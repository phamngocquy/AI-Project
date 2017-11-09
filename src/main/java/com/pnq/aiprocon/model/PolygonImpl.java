package com.pnq.aiprocon.model;

import java.awt.*;

public class PolygonImpl extends Polygon {
    private Point[] points; //diem
    private double[] vertices; // goc
    private double[] edges; // canh

    public PolygonImpl(Point[] points) {
        vertices = new double[points.length];
        edges = new double[points.length];

        // add Edges
        for (int i = 0; i < points.length; i++) {
            if (i != points.length - 1) {
                edges[i] = PolygonHelper.getEdge(points[i], points[i + 1]);
            } else {
                edges[i] = PolygonHelper.getEdge(points[i], points[0]);
            }
        }
        // add vertices
        for (int i = 0; i < points.length; i++) {
            if (i < points.length - 2) {
                vertices[i] = PolygonHelper.getVertice(points[i], points[i + 1], points[i + 2], points);
            } else if (i < points.length - 1) {
                vertices[i] = PolygonHelper.getVertice(points[i], points[i + 1], points[0], points);
            } else {
                vertices[i] = PolygonHelper.getVertice(points[i], points[0], points[1], points);
            }
        }

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
