package kr.richard.itemservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    ItemEntity findByItemId(String itemId);
}
