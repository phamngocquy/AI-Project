package com.pnq.aiprocon;

import com.pnq.aiprocon.model.PolygonImpl;

import java.awt.*;

public class Application {

    public static void main(String[] args) {

        PolygonImpl polygon = new PolygonImpl(new int[]{0,0,2,4,4},new int[]{0,4,2,4,0},5);
        System.out.println("///////////////////");
        for (int i=0;i<5;i++)
        {
            System.out.println(polygon.getEdges()[i]);
            System.out.println(polygon.getVertices()[i]);
            System.out.println("====================");
        }
    }
}
