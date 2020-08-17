package com.ht.weichat.service;

import java.util.Comparator;

/**
 * created by n3207
 * on 2020/1/2
 */
public class GoodStockComparator implements Comparator<StockResult.GoodItem> {
    @Override
    public int compare(StockResult.GoodItem goodItem1, StockResult.GoodItem goodItem2) {
        int diff = goodItem1.ave_day_sale_count - goodItem2.ave_day_sale_count;
        if (diff > 0) {
            return -1;
        } else if (diff < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
