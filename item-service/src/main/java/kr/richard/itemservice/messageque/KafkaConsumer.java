package kr.richard.itemservice.messageque;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.richard.itemservice.jpa.ItemEntity;
import kr.richard.itemservice.jpa.ItemRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementPermission;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ItemRepository repository;

    @KafkaListener(topics = "item-qty")
    public void updateQty(String kafkaMessage){

        log.info(">>>>kafka Message(item-qty): -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        ItemEntity entity = repository.findByItemId((String) map.get("itemId"));
        if (entity != null){
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            repository.save(entity);
        }

    }

}
