import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * created by n3207
 * on 2020/1/3
 */
public class CostResult {
    @JsonProperty("good_cost_list")
    List<GoodItem>goodCostList;
    @JsonProperty("per_order_postage")
    Double per_order_postage;


    public CostResult() {
        goodCostList=new ArrayList<>();

    }

    public static class GoodItem {
        @JsonProperty("thumb_url")
        String thumb_url;
        @JsonProperty("goods_name")
        String goods_name;
        @JsonProperty("goods_id")
        long goods_id;
        @JsonProperty("sku_list")
        List<SkuItem> sku_list;

        public GoodItem() {
            sku_list = new ArrayList<>();
        }
    }

    public static class SkuItem {
        @JsonProperty("spec")
        String spec;
        @JsonProperty("sku_id")
        long sku_id;
        @JsonProperty("purchase_price")
        Double purchase_price;

    }
}
