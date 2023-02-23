package com.hansol.eCommerce.repository;

import com.hansol.eCommerce.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
