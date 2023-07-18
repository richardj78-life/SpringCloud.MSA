package kr.richard.itemservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemDto implements Serializable {

    private String itemId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

}
