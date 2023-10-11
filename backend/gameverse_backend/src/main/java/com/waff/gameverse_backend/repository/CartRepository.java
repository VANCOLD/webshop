package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
