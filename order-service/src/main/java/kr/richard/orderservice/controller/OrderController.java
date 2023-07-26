package kr.richard.orderservice.controller;

import kr.richard.orderservice.dto.OrderDto;
import kr.richard.orderservice.jpa.OrderEntity;
import kr.richard.orderservice.messageque.KafkaProducer;
import kr.richard.orderservice.messageque.OrderProducer;
import kr.richard.orderservice.service.OrderService;
import kr.richard.orderservice.vo.RequestOrder;
import kr.richard.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class OrderController {
    private final Environment environment;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Order Service"
                + " / port(local.server.port)="+ environment.getProperty("local.server.port")
                + " / port(server.port)="+ environment.getProperty("server.port")
                + " / token secret="+ environment.getProperty("token.secret")
                + " / token expiration time="+ environment.getProperty("token.expiration_time"));
    }

    @PostMapping("{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId")String userId, @RequestBody RequestOrder orderDetails, ModelMapper mapper){

        log.info(">>>> ZIPKIN Before add post orders data");
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        /*jpa - create order*/
        OrderDto createdOrder = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        /*kafka - */
        /*orderDto.setOrderId(UUID.randomUUID().toString()); //주문 id 생성
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice()); //주문가격 = 수량*가격*/


        /*kafka - send order*/
        /*kafkaProducer.send("item-qty", orderDto);
        orderProducer.send("create_order", orderDto);

        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);*/
        log.info("<<<< ZIPKIN After add post orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);

    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId")String userId){

        log.info(">>>> ZIPKIN Before retrieved get orders data");
        Iterable<OrderEntity> orderList = orderService.getOrderByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> result.add(new ModelMapper().map(v, ResponseOrder.class)));
        log.info("<<<< ZIPKIN after retrieved get orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
