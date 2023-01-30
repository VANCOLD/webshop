package com.waff.gameverse_backend.Repository;

import com.waff.gameverse_backend.Datenmodell.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
