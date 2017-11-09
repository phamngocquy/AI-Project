package com.pnq.aiprocon.model;

public class EvaluateObject {
    double mark; // diem
    int vertex1; // goc polygon 1
    int vertex2; // goc polygon 2

    public EvaluateObject() {
    }

    public EvaluateObject(double mark, int vertex1, int vertex2) {
        this.mark = mark;
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
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
}
