package com.pnq.aiprocon;

import com.pnq.aiprocon.evaluator.EvaluatePolygon;
import com.pnq.aiprocon.helper.ReadFileHelper;
import com.pnq.aiprocon.model.PolygonImpl;

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

        EvaluatePolygon helper = new EvaluatePolygon();
        /*double a =  helper.generateMark((PolygonImpl) polygons.get(0), 0, (PolygonImpl) polygons.get(1) , 3, 4);
        System.out.println(a);
        System.out.println(((PolygonImpl) polygons.get(0)).getEdges().length);*/
        PolygonImpl polygonImp11 = ((PolygonImpl) polygons.get(0)).flipOx();

        polygons.add(polygonImp11);

    }
}
