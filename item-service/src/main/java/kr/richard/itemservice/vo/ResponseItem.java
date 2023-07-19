package kr.richard.itemservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseItem {
    private String itemId;
    private String itemName;
    private Integer stock;
    private Integer unitPrice;
    private Date createdAt;
}
