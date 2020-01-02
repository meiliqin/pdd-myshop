import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GoodItem {
//    @JsonProperty("thumb_url")
//    String thumb_url;
    @JsonProperty("goods_name")
    String goods_name;
    @JsonProperty("goods_id")
    String goods_id;
    @JsonProperty("sale_count")
    int sale_count;
    @JsonProperty("pay_amount")
    Double pay_amount;
    @JsonProperty("sku_list")
    List<SkuItem> sku_list ;

    public GoodItem() {
        sku_list= new ArrayList<>();
        pay_amount=0.0;
        sale_count=0;
    }
}
