package com.pnq.aiprocon.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawPolyPanel extends JPanel {
    List<Polygon> polygons;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Polygon polygon : polygons) {
            g.drawPolygon(polygon);
        }

        for (int i = 0; i < polygons.size(); i++) {
            if (i % 2 == 0) {
                g.setColor(Color.blue);

            } else {
                g.setColor(Color.RED);
            }
            if (i == polygons.size() - 1) {
                g.setColor(Color.orange);
            }
            g.drawPolygon(polygons.get(i));

        }

    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public static void main(String[] args) {

    }

    private List<Polygon> xNSize(List<Polygon> polygons, int n) {
        try {
            List<Polygon> result = new ArrayList<>();
            for (Polygon polygon : polygons) {
                int[] xpoints = polygon.xpoints;
                int[] ypoints = polygon.ypoints;
                Polygon polygon_tmp = new Polygon();
                for (int i = 0; i < xpoints.length; i++) {
                    polygon_tmp.addPoint(xpoints[i] * n, ypoints[i] * n);
                }
                result.add(polygon_tmp);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return polygons;
        }
    }

    public void displayPolygons(int zoomX) {
        JFrame frame = new JFrame();
        frame.setTitle("DrawPoly");
        frame.setSize(700, 600);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        DrawPolyPanel drawPolyPanel = new DrawPolyPanel();
        drawPolyPanel.setPolygons(this.xNSize(this.polygons, zoomX));
        Container contentPane = frame.getContentPane();
        contentPane.add(drawPolyPanel);
        frame.show();
    }
}
