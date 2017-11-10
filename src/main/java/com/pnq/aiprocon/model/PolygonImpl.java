package com.pnq.aiprocon.model;

import com.pnq.aiprocon.helper.PolygonHelper;

import java.awt.*;

public class PolygonImpl extends Polygon {
    private Point[] points; //diem
    private double[] vertices; // goc
    private double[] edges; // canh

    public PolygonImpl(int xpoint[], int[] ypoint, int npoint) {
        super(xpoint, ypoint, npoint);

        vertices = new double[npoint];
        edges = new double[npoint];
        points = new Point[npoint];

        // point
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point();
            points[i].x = xpoint[i];
            points[i].y = ypoint[i];
        }

        // add Edges
        for (int i = 0; i < points.length; i++) {
            xpoint[i] = points[i].x;
            ypoint[i] = points[i].y;
            if (i != points.length - 1) {
                edges[i] = PolygonHelper.getEdge(points[i], points[i + 1]);
            } else {
                edges[i] = PolygonHelper.getEdge(points[i], points[0]);
            }
        }

        // add vertices
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                vertices[i] = PolygonHelper.getVertice(points[points.length - 1], points[i], points[i + 1], points);
            } else if (i == points.length - 1) {
                vertices[i] = PolygonHelper.getVertice(points[i - 1], points[i], points[0], points);

            } else {
                vertices[i] = PolygonHelper.getVertice(points[i - 1], points[i], points[i + 1], points);
            }
        }

        // create Polygon


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
    
    public PolygonImpl move(int x,int y) {
    	PolygonImpl movePolygon = new PolygonImpl(this.xpoints, this.ypoints, this.npoints);
    	for(int i = 0; i < movePolygon.points.length; i++) {
  			movePolygon.xpoints[i] += x;
   			movePolygon.ypoints[i] += y;
   		}
    	return movePolygon;
    }

    public PolygonImpl flipOy() {
    	PolygonImpl flipPolygon = new PolygonImpl(this.xpoints, this.ypoints, this.npoints);
    	int xMax = flipPolygon.xpoints[1] , xMin = flipPolygon.xpoints[1];
    	for(int i = 0; i < flipPolygon.points.length; i++) {
    		if (xMax < flipPolygon.xpoints[i])
   				xMax = flipPolygon.xpoints[i];
   			if (xMin > flipPolygon.xpoints[i])
   				xMin = flipPolygon.xpoints[i];
   		}
    	for(int i = 0; i < flipPolygon.points.length; i++) {
    		flipPolygon.xpoints[i] = -flipPolygon.xpoints[i]+xMax+xMin;
   		}
    	return flipPolygon;
    }
    public PolygonImpl flipOx() {
    	PolygonImpl flipPolygon = new PolygonImpl(this.xpoints, this.ypoints, this.npoints);
    	int yMax = flipPolygon.ypoints[1], yMin = flipPolygon.ypoints[1];
    	for(int i = 0; i < flipPolygon.points.length; i++) {
    		if (yMax < flipPolygon.ypoints[i])
   				yMax = flipPolygon.ypoints[i];
   			if (yMin > flipPolygon.ypoints[i])
   				yMin = flipPolygon.ypoints[i];
   		}
    	for(int i = 0; i < flipPolygon.points.length; i++) {
    		flipPolygon.ypoints[i] = -flipPolygon.ypoints[i]+yMax+yMin;
   		}
    	return flipPolygon;
    }
    public PolygonImpl rolatic() {
    	PolygonImpl rolaticPolygon = new PolygonImpl(this.xpoints, this.ypoints, this.npoints);
    	int xMaxOld = rolaticPolygon.xpoints[1] , yMaxOld = rolaticPolygon.ypoints[1];
    	for(int i = 0; i < rolaticPolygon.points.length; i++) {
			if (xMaxOld < rolaticPolygon.xpoints[i])
				xMaxOld = rolaticPolygon.xpoints[i];
			if (yMaxOld < rolaticPolygon.ypoints[i])
				yMaxOld = rolaticPolygon.ypoints[i];
    	}
    		for(int i = 0; i < points.length; i++) {
    			int tmp;
    			tmp = rolaticPolygon.xpoints[i];
    			rolaticPolygon.xpoints[i] = rolaticPolygon.ypoints[i];
    			rolaticPolygon.ypoints[i] = tmp;
    		}
    	int  xMaxNew = rolaticPolygon.xpoints[1], yMaxNew = rolaticPolygon.ypoints[1];
    	for(int i = 0; i < rolaticPolygon.points.length; i++) {
			if (xMaxNew < rolaticPolygon.xpoints[i])
				xMaxNew = rolaticPolygon.xpoints[i];
			if (yMaxNew < rolaticPolygon.ypoints[i])
				yMaxNew = rolaticPolygon.ypoints[i];
    	}
    	System.out.println(xMaxNew + " " + xMaxOld + " " + yMaxNew + " " + yMaxOld);
    	return rolaticPolygon.flipOy().move(xMaxOld-xMaxNew, yMaxOld-yMaxNew);
    }
    
}
