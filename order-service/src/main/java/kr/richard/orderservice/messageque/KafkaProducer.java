package kr.richard.orderservice.messageque;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.richard.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderDto send(String topic, OrderDto orderDto){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = mapper.writeValueAsString(orderDto);

        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info(">>>> kafka producer send message(topic: item-qty) : " + orderDto);

        return orderDto;
    }
}
