package com.waff.gameverse_backend.service;

import java.util.List;
import java.util.Optional;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.repository.*;
import jakarta.persistence.Id;
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

    private GenreRepository genreRepo;

    private ConsoleGenerationRepository conGenRepo;


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



    public Optional<Product> findProductById(long Id) {return prodRepo.findById(Id);}

    public Optional<Console> findConsoleById(long Id) {return conRepo.findById(Id);}

    public Optional<Accessory> findAccessoryById(long Id) {return accRepo.findById(Id);}

    public Optional<Merchandise> findMerchandiseById(long Id) {return merchRepo.findById(Id);}

    public Optional<Giftcard> findGiftcardById(long Id) {return giftRepo.findById(Id);}

    public Optional<Game> findGamesById(long Id) {return gameRepo.findById(Id);}



    public Product saveProduct(Product product) {return (Product) prodRepo.save(product);}

    public Console saveConsole(Console console) {return conRepo.save(console);}

    public Accessory saveAccessory(Accessory accessory) {return accRepo.save(accessory);}

    public Merchandise saveMerchandise(Merchandise merchandise) {return merchRepo.save(merchandise);}

    public Giftcard saveGiftcard(Giftcard giftcard) {return giftRepo.save(giftcard);}

    public Game saveGame(Game game) {return gameRepo.save(game);}



    public Product deleteProduct(Product product)
    {
        prodRepo.delete(product);
        return product;
    }

    public Console deleteConsole(Console console) {
        conRepo.delete(console);
        return console;
    }

    public Accessory deleteAccessory(Accessory accessory) {
        accRepo.delete(accessory);
        return accessory;
    }

    public Merchandise deleteMerchandise(Merchandise merchandise) {
        merchRepo.delete(merchandise);
        return merchandise;
    }

    public Giftcard deleteGiftcard(Giftcard giftcard) {
        giftRepo.delete(giftcard);
        return giftcard;
    }

    public Game deleteGame(Game game) {
        gameRepo.delete(game);
        return game;
    }
}
