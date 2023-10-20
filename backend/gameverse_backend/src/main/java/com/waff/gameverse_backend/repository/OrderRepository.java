package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.enums.OrderStatus;
import com.waff.gameverse_backend.model.Order;
import com.waff.gameverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserAndOrderStatusEquals(User user, OrderStatus orderStatus);
}
