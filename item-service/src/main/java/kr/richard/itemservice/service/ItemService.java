package kr.richard.itemservice.service;

import kr.richard.itemservice.jpa.ItemEntity;

public interface ItemService {
    Iterable<ItemEntity> getAllItems();
}
