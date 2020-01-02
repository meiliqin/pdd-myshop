import com.pdd.pop.ext.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * created by n3207
 * on 2020/1/2
 */
public class SaleResult {
    @JsonProperty("daySale")
    List<GoodItem>daySale;
    @JsonProperty("date")
    String date;
    @JsonProperty("description")
    String description;
    @JsonProperty("pay_amount")
    Double pay_amount;

    public SaleResult(String date, String description) {
        this.date = date;
        this.description = description;
        daySale=new ArrayList<>();
        pay_amount=0.0;
    }
}
