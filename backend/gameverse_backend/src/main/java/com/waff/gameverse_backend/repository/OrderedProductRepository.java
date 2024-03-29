package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
