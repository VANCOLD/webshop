package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Giftcard;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier @Repository
public interface GiftcardRepository extends ProductRepository<Giftcard>
{
}
