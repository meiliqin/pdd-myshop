import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

public class SkuItem {
    @JsonProperty("spec")
    String spec;
    @JsonProperty("sku_id")
    String sku_id;
    @JsonProperty("sale_count")
    int sale_count;

    public SkuItem() {
        sale_count=0;
    }
}
