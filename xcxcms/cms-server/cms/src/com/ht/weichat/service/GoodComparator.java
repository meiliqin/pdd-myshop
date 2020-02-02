package com.ht.weichat.service;

import java.util.Comparator;

/**
 * created by n3207
 * on 2020/1/2
 */
public class GoodComparator implements Comparator<GoodItem> {
    @Override
    public int compare(GoodItem goodItem1, GoodItem goodItem2) {
        int diff = goodItem1.sale_count - goodItem2.sale_count;
        if (diff > 0) {
            return -1;
        } else if (diff < 0) {
            return 1;
        } else {
            Double diff2 = goodItem1.pay_amount - goodItem2.pay_amount;
            if (diff2 > 0) {
                return -1;
            } else if (diff2 < 0) {
                return 1;
            }
            return 0;
        }
    }
}
