package com.hansol.eCommerce.repository;

import com.hansol.eCommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
