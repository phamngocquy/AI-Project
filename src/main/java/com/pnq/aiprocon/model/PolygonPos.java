package com.pnq.aiprocon.model;

import java.util.ArrayList;

public class PolygonPos {
    ArrayList<Integer> pos2;

    public PolygonPos(ArrayList<Integer> pos2) {
        this.pos2 = pos2;
    }

    public PolygonPos() {
        pos2 = new ArrayList<Integer>();
    }


    public  ArrayList<Integer> getPos2() {
        return pos2;
    }

    public void setPos2( ArrayList<Integer> pos2) {
        this.pos2 = pos2;
    }

}
