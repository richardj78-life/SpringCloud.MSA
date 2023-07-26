package kr.richard.orderservice.messageque;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.richard.orderservice.dto.Field;
import kr.richard.orderservice.dto.KafkaOrderDto;
import kr.richard.orderservice.dto.OrderDto;
import kr.richard.orderservice.dto.Payload;
import kr.richard.orderservice.dto.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string",true,"order_id"),
            new Field("string",true,"user_id"),
            new Field("string",true,"item_id"),
            new Field("int32",true,"qty"),
            new Field("int32",true,"unit_price"),
            new Field("int32",true,"total_price"));

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public OrderDto send(String topic, OrderDto orderDto){
        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .item_id(orderDto.getItemId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDto);

        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info(">>>> kafka Producer(create-order) : " + kafkaOrderDto);

        return orderDto;
    }
}
