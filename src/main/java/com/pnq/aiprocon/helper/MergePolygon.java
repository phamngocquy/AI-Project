package com.pnq.aiprocon.helper;

import com.pnq.aiprocon.model.PolygonImpl;
import com.pnq.aiprocon.render.DrawPolyPanel;
import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.LocaleServiceProvider;

public class MergeHelper {

    /**
     * @param p1
     * @param pos1
     * @param p2
     * @param pos2
     * @param times gia tri tu 0 - 7
     * @return
     */
    public static PolygonImpl mapP2toP1(Object p1, int pos1, Object p2, int pos2, int times) {
        PolygonImpl polygon1 = (PolygonImpl) p1;
        PolygonImpl polygon2 = (PolygonImpl) p2;

        // thuc hien xoay [times] buoc
        // 0 khong xoay
        // 1 xoay 90 do
        // 2 xoay 180
        // 3 xoay 270
        // 4 lat
        // 5 xoay 90
        // 6 xoay 180
        // 7 xoay 270
        PolygonImpl polygon_step1 = polygon2;
        for (int i = 1; i <= times; i++) {
            if (i == 4) {
                polygon_step1 = polygon2.flipOx();
            } else {
                polygon_step1 = polygon_step1.rolatic();
            }

        }

        // di chuyen hinh den vi tri dich

        Point point1 = new Point(polygon1.xpoints[pos1], polygon1.ypoints[pos1]);
        Point point2 = new Point(polygon_step1.xpoints[pos2], polygon_step1.ypoints[pos2]);
        int x_tmp = point1.x - point2.x;
        int y_tmp = point1.y - point2.y;

        System.out.println(x_tmp + ":" + y_tmp);

        // step 1  : di chuyen hinh den toa do xac dinh
        polygon_step1 = polygon_step1.move(x_tmp, y_tmp);

        // step 2 : quay hinh [times] lan


        return polygon_step1;
    }


    
//    public static boolean checkboundary(Object obj, int x, int y){
//    	PolygonImpl polygonImpl = (PolygonImpl)obj;
//    	for(int i = 0; i < polygonImpl.xpoints.length-1; i++) {
//    		if( Math.abs(Math.hypot(x-polygonImpl.xpoints[i], y - polygonImpl.ypoints[i]) + Math.hypot(x-polygonImpl.xpoints[i+1], y-polygonImpl.ypoints[i+1]) - polygonImpl.getEdges()[i]) < 1)
//    			return true;
//    	}
//    	if( Math.abs(Math.hypot(x-polygonImpl.xpoints[polygonImpl.xpoints.length-1], y - polygonImpl.ypoints[polygonImpl.ypoints.length-1]) + Math.hypot(x-polygonImpl.xpoints[0], y-polygonImpl.ypoints[0]) - polygonImpl.getEdges()[polygonImpl.xpoints.length-1]) < 1)
//    		return true;
//    	return false;
//    }
    public static boolean checkNotInLine(int x1, int y1, int x2, int y2, int x3, int y3) {
    	int a = y1 - y2;
    	int b = x2 - x1;
    	int c = (y2-y1)*x1 + (x1 - x2)*y1;
    	if(a*x3 + b*y3 + c == 0 ) {
    		if((x3-x1)*(x2-x1) > 0 || (y2-y1)*(y2-y1) > 0)
    			return false;
    	}
  		return true;
    }
    
    public static PolygonImpl mergeP2WithP1(Object p1, int pos1, Object p2, int pos2, boolean isFlip) {
        PolygonImpl polygon1 = (PolygonImpl) p1;
        PolygonImpl polygon2 = (PolygonImpl) p2;
        List<Point> points = new ArrayList<>();
        	

        try {
            for (int i = 0; i < polygon1.xpoints.length; i++) {
            	int number = 0;
                Point point = new Point(polygon1.xpoints[i], polygon1.ypoints[i]);
               
                	for(int j = 0; j < polygon2.xpoints.length; j++)
                		if(polygon1.xpoints[i] == polygon2.xpoints[j] && polygon1.ypoints[i] == polygon2.ypoints[j] ){
                			int iFront = i - 1;
                			int iBack = i + 1;
                			int jFront = j - 1;
                			int jBack = j + 1;
                			if(i == 0)
                				iFront = polygon1.xpoints.length - 1;
                			if(i == polygon1.xpoints.length - 1 )
                				iBack = 0;
                			if(j == 0)
                				jFront = polygon2.xpoints.length - 1;
                			if(j == polygon2.xpoints.length - 1 )
                				jBack = 0;
                			if (
//                					(isFlip &&  checkNotInLine(polygon1.xpoints[i], polygon1.ypoints[i],
//                					polygon1.xpoints[iFront], polygon1.ypoints[iFront], 
//                					polygon2.xpoints[jFront], polygon2.ypoints[jFront]) 
//                					&& checkNotInLine(polygon1.xpoints[i], polygon1.ypoints[i],
//                        			polygon1.xpoints[iBack], polygon1.ypoints[iBack], 
//                        			polygon2.xpoints[jBack], polygon2.ypoints[jBack])) ||
                				(isFlip == false &&  checkNotInLine(polygon1.xpoints[i], polygon1.ypoints[i],
                        			polygon1.xpoints[iFront], polygon1.ypoints[iFront], 
                        			polygon2.xpoints[jBack], polygon2.ypoints[jBack]) 
//                      				&& checkNotInLine(polygon1.xpoints[i], polygon1.ypoints[i],
//                            		polygon1.xpoints[iBack], polygon1.ypoints[iBack], 
//                           			polygon2.xpoints[jFront], polygon2.ypoints[jFront])
                				))
                					{
                                	points.add(point);
                                	System.out.println(i + " " + j);
                				}
                					number++;
                			break;
                		}
                	if(number == 0) {
                    	points.add(point);
                	}
                	
                if(i == pos1){

                    //int start = 0;
                    for (int j = pos2 + 1; j < pos2 + polygon2.xpoints.length; j++) {
                        int addj = j;
                        int numberj = 0;
                        if (addj >= polygon2.xpoints.length) {
                            addj = j - polygon2.xpoints.length;
                        }
                        Point point2 = new Point(polygon2.xpoints[addj], polygon2.ypoints[addj]);
                        for(int k = 0; k < polygon1.xpoints.length; k++)
                        	if(polygon1.xpoints[k] == polygon2.xpoints[addj] && polygon1.ypoints[k] == polygon2.ypoints[addj] ){
                    			int kFront = k - 1;
                    			int kBack = k + 1;
                    			int addjFront = addj - 1;
                    			int addjBack = addj + 1;
                    			if(k == 0)
                    				kFront = polygon1.xpoints.length - 1;
                    			if(k == polygon1.xpoints.length - 1 )
                    				kBack = 0;
                    			if(addj == 0)
                    				addjFront = polygon2.xpoints.length - 1;
                    			if(addj == polygon2.xpoints.length - 1 )
                    				addjBack = 0;
                    			if ((isFlip &&  checkNotInLine(polygon2.xpoints[addj], polygon2.ypoints[addj],
                    					polygon1.xpoints[kFront], polygon1.ypoints[kFront], 
                    					polygon2.xpoints[addjFront], polygon2.ypoints[addjFront]) 
                    					&& checkNotInLine(polygon2.xpoints[addj], polygon2.ypoints[addj],
                            			polygon1.xpoints[kBack], polygon1.ypoints[kBack], 
                            			polygon2.xpoints[addjBack], polygon2.ypoints[addjBack])) ||
                    				(isFlip == false &&  checkNotInLine(polygon2.xpoints[addj], polygon2.ypoints[addj],
                            			polygon1.xpoints[kFront], polygon1.ypoints[kFront], 
                            			polygon2.xpoints[addjBack], polygon2.ypoints[addjBack]) 
                          				&& checkNotInLine(polygon2.xpoints[addj], polygon2.ypoints[addj],
                                		polygon1.xpoints[kBack], polygon1.ypoints[kBack], 
                               			polygon2.xpoints[addjFront], polygon2.ypoints[addjFront])))
                    					{
                                    	points.add(point2);
                    				}
	                				numberj++;
	                				break;
	                		}
                        if (numberj == 0) {
                        	points.add(point2);
                        }
//                        if(addj == 3) {
//                        	for(int k = 0; k < polygon1.xpoints.length; k++)
//                        		System.out.println(polygon1.contains(polygon1.xpoints[k],polygon1.ypoints[k]));
//                        	
//                        	
//                        }
                    }
                }
            }

            int lengths = points.size();
            int[] xpoints = new int[lengths];
            int[] ypoints = new int[lengths];


            for (int i = 0; i < lengths; i++) {
                xpoints[i] = points.get(i).x;
                ypoints[i] = points.get(i).y;
            }

            PolygonImpl polygon = new PolygonImpl(xpoints, ypoints, lengths);
            return polygon;
        } catch (Exception e) {
            e.printStackTrace();
            PolygonImpl polygon = new PolygonImpl(new int[0], new int[0], 0);
            return polygon;
        }
    }

    
    public static boolean shouldMerge(Object p1, int pos1, Object p2, int pos2, boolean isFlip) {
    	
    	PolygonImpl polygonImpl = mergeP2WithP1(p1, pos1, p2, pos2, isFlip);
    	for(int i = 0; i < polygonImpl.xpoints.length - 1; i++)
    		for(int j = i+1; j < polygonImpl.xpoints.length; j++) {
    			if(polygonImpl.xpoints[i] == polygonImpl.xpoints[j] && polygonImpl.ypoints[i] == polygonImpl.ypoints[j])
    				return false;
    		}
    	return true;
    }
    
    public static void main(String[] args) {
        ReadFileHelper readFileHelper = new ReadFileHelper();

        String filePath = "C:\\Users\\dell\\Desktop\\hoc tap\\AI\\procon_input.txt";
        java.util.List polygons = readFileHelper.readFile(filePath);
        DrawPolyPanel drawPolyPanel = new DrawPolyPanel();
       // PolygonImpl polygon = new PolygonImpl(new int[] {10, 30, 30, 20, 20, 30, 30, 50, 50, 10}, new int[] {50, 50, 40 , 40 , 30 , 30 , 20 , 20 , 10 , 10}, 10);
        List polygon_tmp = new ArrayList();      
        PolygonImpl polygon1 = (PolygonImpl) polygons.get(0);
        polygon_tmp.add(polygon1.move(40, 40));
        PolygonImpl polygon2 = (PolygonImpl) polygons.get(1);
        polygon_tmp.add(polygon2.move(40, 40).move(14, -9));
    	System.out.println(shouldMerge(polygon1, 4 , polygon2.move(14, -9), 5, false));
        PolygonImpl polygonImpl = mergeP2WithP1(polygon1, 4 , polygon2.move(14, -9), 5, false);
        polygon_tmp.add(polygonImpl);
       //checkNotInLine(-2, 1, -5, 3, 1, 2);
        drawPolyPanel.setPolygons(polygon_tmp);
        drawPolyPanel.displayPolygons(5);
        //System.out.println(checkNotInLine(39, 52, 39, 57, 39, 55));
    }
}
