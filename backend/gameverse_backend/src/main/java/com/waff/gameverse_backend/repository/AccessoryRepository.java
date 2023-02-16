package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Accessory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier @Repository
public interface AccessoryRepository extends ProductRepository<Accessory>
{
}
