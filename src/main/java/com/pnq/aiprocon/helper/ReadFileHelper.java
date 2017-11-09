package com.pnq.aiprocon.helper;



import com.pnq.aiprocon.model.PolygonImpl;
import com.pnq.aiprocon.render.DrawPolyPanel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFileHelper {
    public List<PolygonImpl> readFile(String filePath) {
        List<PolygonImpl> polygons = new ArrayList<>();
        try {
            System.out.println(filePath);

            Scanner s = new Scanner(new File(filePath));

            int arr_length = Integer.parseInt(s.nextLine());
            System.out.println(arr_length);
            for (int i = 0; i <= arr_length; i++) {
                String[] strings = s.nextLine().split(" ");
                int totalPoints = Integer.valueOf(strings[1]);
                int[] x_points = new int[totalPoints];
                int[] y_points = new int[totalPoints];
                for (int j = 2; j < strings.length; j++) {
                    if (j % 2 == 0) {
                        x_points[j / 2 - 1] = Integer.parseInt(strings[j]);
                    } else {
                        y_points[j / 2 - 1] = Integer.parseInt(strings[j]);
                    }
                }
                PolygonImpl polygon = new PolygonImpl(x_points, y_points, totalPoints);
                polygons.add(polygon);

                // polygon cuoi cung la khung hinh, khong phai da giac
            }

        } catch (Exception ex) {
            try {
                throw ex;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } finally {
        }
        return polygons;
    }

    public static void main(String[] args) {
        ReadFileHelper readFileHelper = new ReadFileHelper();

        String filePath = "/home/javis/Desktop/procon.txt";
        List polygons = readFileHelper.readFile(filePath);

        // render
        DrawPolyPanel drawPolyPanel = new DrawPolyPanel();
        drawPolyPanel.setPolygons(polygons);
        drawPolyPanel.displayPolygons(5);


    }
}