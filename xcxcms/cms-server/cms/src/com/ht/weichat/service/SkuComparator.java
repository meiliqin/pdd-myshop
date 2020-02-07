package com.ht.weichat.service;

import java.util.Comparator;

/**
 * created by n3207
 * on 2020/1/2
 */
public class SkuComparator implements Comparator<SaleResult.SkuItem> {
    @Override
    public int compare(SaleResult.SkuItem skuItem1, SaleResult.SkuItem skuItem2) {
        int diff = skuItem1.sale_count - skuItem2.sale_count;
        if (diff > 0) {
            return -1;
        } else if (diff < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
