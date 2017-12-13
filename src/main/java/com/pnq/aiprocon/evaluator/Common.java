package com.pnq.aiprocon.evaluator;

public class Common {
    // diem o giua la tdiem goc
    public double generateMaxLength(int x1, int y1, int x2, int y2, int x3, int y3) {

        int a = y1 - y2;
        int b = x2 - x1;
        int c = (y2 - y1) * x1 + (x1 - x2) * y1;
        if (a * x3 + b * y3 + c == 0) {
            if ((x1 - x2) * (x3 - x2) >= 0 && (y1 - y2) * (y3 - y2) >= 0) {
                // thoa man tat ca dieu kien
                double length1 = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                double length2 = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
                if (length1 > length2) {
                    return length1;
                }
                return length2;
            }


        }

        return 0;
    }
}
