package com.pnq.aiprocon.evaluator;

import com.pnq.aiprocon.helper.MergePolygon;
import com.pnq.aiprocon.model.EvaluateObject;
import com.pnq.aiprocon.model.PolygonImpl;

public class EvaluatePolygon {

    public EvaluateObject execEvaluate(PolygonImpl polygon1, PolygonImpl polygon2) {

        EvaluateObject evaluateObject = new EvaluateObject();
        evaluateObject.setMark(Double.MIN_VALUE);


        for (int i = 0; i < polygon1.xpoints.length; i++) {
            for (int j = 0; j < polygon2.xpoints.length; j++) {
                // lay mot goc cua polygon 1,,, duyet tung goc polygon 2 de xet

                // tinh diem phu hop cua goc i va j
                EvaluateObject object = caculateMark2Vertices(polygon1, i, polygon2, j);


                // lay EvaluateObject co diem phu hop cao nhat
                if (object.getMark() > evaluateObject.getMark()) {
                    evaluateObject = object;
                }

            }
        }

        return evaluateObject;
    }

    // hinh 2 ghep vao hinh 1
    private EvaluateObject caculateMark2Vertices(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2) {
        EvaluateObject evaluateObject = new EvaluateObject();

        evaluateObject.setMark(Double.MIN_VALUE);
        evaluateObject.setPolygon1(polygon1);
        evaluateObject.setVertex1(pos1);
        evaluateObject.setVertex2(pos2);

        // xoay thu tu 1 - 7 lan, tra ve hinh da ghep
        for (int i = 1; i <= 7; i++) {
            // polygon thu 2 sau khi da ghep
            PolygonImpl polygon = MergePolygon.mapP2toP1(polygon1, pos1, polygon2, pos2, i);

            // tinh diem cua hinh1,goc pos1, voi hinh2(moi),goc pos2
            // hai hinh da duoc ghep
            double mark = generateMark(polygon1, pos1, polygon, pos2, i);


            // lay diem lon nhat, tra ve so lan quay
            if (mark > evaluateObject.getMark()) {
                evaluateObject.setMark(mark);
                evaluateObject.setPolygon2(polygon);
                evaluateObject.setNumOfRotate(i);
            }
        }
        return evaluateObject;
    }

    public double generateMark(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2, int flip) {

        // pos1 : vi tri goc duoc ghep cua polygon 1
        // pos2 :  vi tri goc duoc ghep cua polygon 2
        // todo [Namdv] viet ham tinh diem phu hop 2 polygon (2 polygon da duoc ghep,)
        double mark = 0;
        //tinh diem theo goc
        //neu 2 goc xap xi bang nhau thi se cong 50 diem,
        //con lai se tinh theo cong thuc mark = 50 * (goc nho/ goc lon)
        if((polygon1.getVertices()[pos1] + polygon2.getVertices()[pos2])<=360 ){
            if((polygon2.getVertices()[pos2] + polygon1.getVertices()[pos1]) >= 359
                    ||(polygon2.getVertices()[pos2] + polygon1.getVertices()[pos1]) <=360){
                mark += 50;
            }else {
                if(polygon1.getVertices()[pos1]<= polygon2.getVertices()[pos2] ) {
                    mark += 50 * (1 - polygon1.getVertices()[pos1] / polygon2.getVertices()[pos2]);
                } else {
                    mark += 50 * (1 - polygon2.getVertices()[pos2] / polygon1.getVertices()[pos1]);
                }
            }
            // tinh diem theo canh
            if(flip == 1 || flip ==2 || flip== 3){
                if(pos1 == 0 && pos2 != 0 ){
                    if(polygon1.getEdges()[pos1] >=  polygon2.getEdges()[pos2-1]){
                        mark +=25 * (polygon2.getEdges()[pos2-1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2-1]);
                    }

                    if(polygon1.getEdges()[polygon1.getEdges().length -1] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[polygon1.getEdges().length -1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[polygon1.getEdges().length -1] / polygon2.getEdges()[pos2]);
                    }
                } else if(pos1 == 0 && pos2 == 0){
                    if(polygon1.getEdges()[pos1] >= polygon2.getEdges()[polygon2.getEdges().length -1]){
                        mark +=25 * (polygon2.getEdges()[polygon2.getEdges().length -1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[polygon2.getEdges().length -1]);
                    }

                    if(polygon1.getEdges()[polygon1.getEdges().length -1 ] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[polygon1.getEdges().length -1 ]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[polygon1.getEdges().length -1] / polygon2.getEdges()[pos2]);
                    }
                } else if(pos1 != 0 && pos2 == 0){
                    if(polygon1.getEdges()[pos1] >=  polygon2.getEdges()[polygon2.getEdges().length-1]){
                        mark +=25 * (polygon2.getEdges()[polygon2.getEdges().length-1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[polygon2.getEdges().length-1]);
                    }

                    if(polygon1.getEdges()[pos1-1] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1-1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1 -1] / polygon2.getEdges()[pos2]);
                    }
                } else if(pos1 !=0 && pos2 !=0){
                    if(polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2-1]){
                        mark +=25 * (polygon2.getEdges()[pos2-1] / polygon1.getEdges()[pos1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2-1]);
                    }

                    if(polygon1.getEdges()[pos1-1] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1-1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1 -1] / polygon2.getEdges()[pos2]);
                    }

                }
            } else if( flip == 4 || flip ==5 ||flip == 6 || flip == 7){
                if(pos1 == 0 && pos2 != 0 ){
                    if(polygon1.getEdges()[pos1] >=  polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }

                    if(polygon1.getEdges()[polygon1.getEdges().length -1] >= polygon2.getEdges()[pos2-1]){
                        mark +=25 * (polygon2.getEdges()[pos2-1] / polygon1.getEdges()[polygon1.getEdges().length -1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[polygon1.getEdges().length -1] / polygon2.getEdges()[pos2-1]);
                    }
                } else if(pos1 == 0 && pos2 == 0){
                    if(polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }

                    if(polygon1.getEdges()[polygon1.getEdges().length -1 ] >= polygon2.getEdges()[polygon2.getEdges().length]){
                        mark +=25 * (polygon2.getEdges()[polygon2.getEdges().length -1] / polygon1.getEdges()[polygon1.getEdges().length -1 ]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[polygon1.getEdges().length -1] / polygon2.getEdges()[polygon2.getEdges().length -1]);
                    }
                } else if(pos1 != 0 && pos2 == 0){
                    if(polygon1.getEdges()[pos1-1] >=  polygon2.getEdges()[polygon2.getEdges().length-1]){
                        mark +=25 * (polygon2.getEdges()[polygon2.getEdges().length-1] / polygon1.getEdges()[pos1-1]);
                    } else {
                        mark += 25 * (polygon1.getEdges()[pos1 -1] / polygon2.getEdges()[polygon2.getEdges().length-1]);
                    }

                    if(polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }
                } else if(pos1 != 0 && pos2 !=0){
                    if(polygon1.getEdges()[pos1] >= polygon2.getEdges()[pos2]){
                        mark +=25 * (polygon2.getEdges()[pos2] / polygon1.getEdges()[pos1]);
                    }else {
                        mark +=25 * (polygon1.getEdges()[pos1] / polygon2.getEdges()[pos2]);
                    }

                    if(polygon1.getEdges()[pos1-1] >= polygon2.getEdges()[pos2-1]){
                        mark +=25 * (polygon2.getEdges()[pos2 -1] / polygon1.getEdges()[pos1-1]);
                    } else {
                        mark +=25 * (polygon1.getEdges()[pos1 -1] / polygon2.getEdges()[pos2-1]);
                    }
                }
            }

        } else {
            // todo: hai hinh de nhau
        }

        return mark;
    }

}
