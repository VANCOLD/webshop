package com.waff.gameverse_backend.service;

import java.util.List;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.AccessoryRepository;
import com.waff.gameverse_backend.repository.MerchandiseRepository;
import com.waff.gameverse_backend.repository.GameRepository;
import com.waff.gameverse_backend.repository.ConsoleRepository;
import com.waff.gameverse_backend.repository.GiftcardRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @NoArgsConstructor
public class ProductService
{

    private ProductRepository prodRepo;
    private ConsoleRepository conRepo;
    private AccessoryRepository accRepo;
    private MerchandiseRepository merchRepo;
    private GiftcardRepository  giftRepo;
    private GameRepository gameRepo;


    public List<Product> findAllProducts() {
        return prodRepo.findAll();
    }

    public List<Console> findAllConsole() {
        return conRepo.findAll();
    }
    public List<Accessory> findAllAccessory() {
        return accRepo.findAll();
    }

    public List<Merchandise> findAllMerchandise() {
        return merchRepo.findAll();
    }

    public List<Giftcard> findAllGiftcard() {
        return giftRepo.findAll();
    }

    public List<Game> findAllGames() {
        return gameRepo.findAll();
    }

}
