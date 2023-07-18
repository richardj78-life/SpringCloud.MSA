package kr.richard.itemservice.service;

import kr.richard.itemservice.jpa.ItemEntity;
import kr.richard.itemservice.jpa.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public Iterable<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }
}
