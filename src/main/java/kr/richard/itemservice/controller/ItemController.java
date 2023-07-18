package kr.richard.itemservice.controller;

import kr.richard.itemservice.jpa.ItemEntity;
import kr.richard.itemservice.service.ItemService;
import kr.richard.itemservice.vo.ResponseItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ItemController {

    private final Environment environment;
    private final ItemService itemService;

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Item Service"
                + " / port(local.server.port)="+ environment.getProperty("local.server.port")
                + " / port(server.port)="+ environment.getProperty("server.port")
                + " / token secret="+ environment.getProperty("token.secret")
                + " / token expiration time="+ environment.getProperty("token.expiration_time"));
    }

    @GetMapping("/item")
    public ResponseEntity<List<ResponseItem>> getCatalogs(){
        Iterable<ItemEntity> itemList = itemService.getAllItems();

        List<ResponseItem> result = new ArrayList<>();
        itemList.forEach(v -> result.add(new ModelMapper().map(v, ResponseItem.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
