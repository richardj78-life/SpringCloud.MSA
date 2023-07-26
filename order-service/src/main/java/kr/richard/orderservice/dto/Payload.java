package kr.richard.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private String order_id;
    private String user_id;
    private String item_id;
    private int qty;
    private int unit_price;
    private int total_price;
}
