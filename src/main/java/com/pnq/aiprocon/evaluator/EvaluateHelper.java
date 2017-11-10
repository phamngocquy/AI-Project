package com.pnq.aiprocon.evaluator;

import com.pnq.aiprocon.helper.MergeHelper;
import com.pnq.aiprocon.model.EvaluateObject;
import com.pnq.aiprocon.model.PolygonImpl;

import java.util.ArrayList;
import java.util.List;

public class EvaluateHelper {

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
            PolygonImpl polygon = MergeHelper.mapP2toP1(polygon1, pos1, polygon2, pos2, i);

            // tinh diem cua hinh1,goc pos1, voi hinh2(moi),goc pos2
            // hai hinh da duoc ghep
            double mark = generateMark(polygon1, pos1, polygon, pos2);


            // lay diem lon nhat, tra ve so lan quay
            if (mark > evaluateObject.getMark()) {
                evaluateObject.setMark(mark);
                evaluateObject.setPolygon2(polygon);
                evaluateObject.setNumOfRotate(i);
            }
        }
        return evaluateObject;
    }

    private double generateMark(PolygonImpl polygon1, int pos1, PolygonImpl polygon2, int pos2) {

        // pos1 : vi tri goc duoc ghep cua polygon 1
        // pos2 :  vi tri goc duoc ghep cua polygon 2
        // todo [Namdv] viet ham tinh diem phu hop 2 polygon (2 polygon da duoc ghep,)


        return 0;
    }

}
