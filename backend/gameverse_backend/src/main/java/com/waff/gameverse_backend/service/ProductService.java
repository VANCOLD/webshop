package com.waff.gameverse_backend.service;

import java.util.List;
import java.util.Optional;

import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.AccessoryRepository;
import com.waff.gameverse_backend.repository.MerchandiseRepository;
import com.waff.gameverse_backend.repository.GameRepository;
import com.waff.gameverse_backend.repository.ConsoleRepository;
import com.waff.gameverse_backend.repository.GiftcardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class ProductService {


    private ProductRepository prodRepo;
    private ConsoleRepository conRepo;
    private AccessoryRepository accRepo;
    private MerchandiseRepository merchRepo;
    private GiftcardRepository  giftRepo;
    private GameRepository gameRepo;

}
