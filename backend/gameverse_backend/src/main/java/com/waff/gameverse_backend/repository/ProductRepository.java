package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {}
