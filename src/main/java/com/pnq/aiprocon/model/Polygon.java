package com.pnq.aiprocon.model;

public class Polygon {
    private Point[] points; //diem
    private double[] vertices; // goc
    private double[] edges; // canh

    public Polygon(Point[] points, double[] vertices, double[] edges) {
        this.points = points;
        this.vertices = vertices;
        this.edges = edges;
    }

    public Polygon() {
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public double[] getVertices() {
        return vertices;
    }

    public void setVertices(double[] vertices) {
        this.vertices = vertices;
    }

    public double[] getEdges() {
        return edges;
    }

    public void setEdges(double[] edges) {
        this.edges = edges;
    }
}
