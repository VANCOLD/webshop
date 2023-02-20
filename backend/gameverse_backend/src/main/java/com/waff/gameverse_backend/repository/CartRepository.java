package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>
{
}
