package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
