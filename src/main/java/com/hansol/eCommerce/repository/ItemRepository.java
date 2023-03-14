package com.hansol.eCommerce.repository;

import com.hansol.eCommerce.constant.ItemSellStatus;
import com.hansol.eCommerce.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    List<Item> findByItemNm(String itemNn);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    List<Item> findByPriceGreaterThan(Integer price);

    List<Item> findByItemSellStatus(ItemSellStatus sellStartTime);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc ")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
