package com.pnq.aiprocon.model;

public class EvaluateObject {
    double mark; // diem
    int vertex1; // goc polygon 1
    int vertex2; // goc polygon 2
    PolygonImpl polygon1; // vertex1 ung voi polygon1
    PolygonImpl polygon2;   // vertex2 ung voi polygon2
    int numOfRotate;// so lan quay

    public EvaluateObject() {
    }

    public EvaluateObject(double mark, int vertex1, int vertex2, PolygonImpl polygon1, PolygonImpl polygon2, int numOfRotate) {
        this.mark = mark;
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.polygon1 = polygon1;
        this.polygon2 = polygon2;
        this.numOfRotate = numOfRotate;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getVertex1() {
        return vertex1;
    }

    public void setVertex1(int vertex1) {
        this.vertex1 = vertex1;
    }

    public int getVertex2() {
        return vertex2;
    }

    public void setVertex2(int vertex2) {
        this.vertex2 = vertex2;
    }

    public PolygonImpl getPolygon1() {
        return polygon1;
    }

    public void setPolygon1(PolygonImpl polygon1) {
        this.polygon1 = polygon1;
    }

    public PolygonImpl getPolygon2() {
        return polygon2;
    }

    public void setPolygon2(PolygonImpl polygon2) {
        this.polygon2 = polygon2;
    }

    public int getNumOfRotate() {
        return numOfRotate;
    }

    public void setNumOfRotate(int numOfRotate) {
        this.numOfRotate = numOfRotate;
    }
}
