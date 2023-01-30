package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface ProductRepository<T extends Product> extends JpaRepository<T,  Long> {}
