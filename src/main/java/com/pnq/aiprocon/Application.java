package com.pnq.aiprocon;

import com.pnq.aiprocon.helper.ReadFileHelper;
import com.pnq.aiprocon.model.PolygonImpl;

import java.awt.*;
import java.util.List;

public class Application {

    public static void main(String[] args) {

       // PolygonImpl polygon = new PolygonImpl(new int[]{0,0,2,4,4},new int[]{0,4,2,4,0},5);
        ReadFileHelper readFileHelper = new ReadFileHelper();

        String filePath = "C:\\Users\\Capricorn.uet\\Desktop\\input.txt";
        List polygons = readFileHelper.readFile(filePath);
        System.out.println("///////////////////");
        /*for (int i=0;i<5;i++)
        {
            System.out.println(polygon.getEdges()[i]);
            System.out.println(polygon.getVertices()[i]);
            System.out.println("====================");
        }*/
        for(int i=0; i< polygons.size(); i++){
            PolygonImpl polygon = (PolygonImpl) polygons.get(i);
            for (int j=0;j<polygon.xpoints.length;j++)
            {
                System.out.println(polygon.getEdges()[j]);
                System.out.println(polygon.getVertices()[j]);
                System.out.println("====================");
            }
        }
    }
}
