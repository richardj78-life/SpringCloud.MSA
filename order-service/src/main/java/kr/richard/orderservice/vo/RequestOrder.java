package kr.richard.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {
    private String itemId;
    private Integer qty;
    private Integer unitPrice;
}
