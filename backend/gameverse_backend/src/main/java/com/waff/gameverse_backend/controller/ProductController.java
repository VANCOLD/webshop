package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.*;
import com.waff.gameverse_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProductController
{

    private ProductService prodService;

    @GetMapping
    public List<Product> findAllProducts() { return prodService.findAllProducts(); }

    @GetMapping("/consoles")
    public List<Console> findAllConsole() {
        return prodService.findAllConsole();
    }

    @GetMapping("/accessories")
    public List<Accessory> findAllAccessory() {
        return prodService.findAllAccessory();
    }

    @GetMapping("/merchandise")
    public List<Merchandise> findAllMerchandise() {
        return prodService.findAllMerchandise();
    }

    @GetMapping("/giftcards")
    public List<Giftcard> findAllGiftcard() {
        return prodService.findAllGiftcard();
    }

    @GetMapping("/games")
    public List<Game> findAllGames() {
        return prodService.findAllGames();
    }

/* ------------------------------------------------------------------------------------------------------------------*/

    @GetMapping("/{id}")
    public Optional<Product> findProductById(@PathVariable long Id) { return prodService.findProductById(Id); }

    @GetMapping("/consoles/{id}")
    public Optional<Console> findConsoleById(@PathVariable long Id) {
        return prodService.findConsoleById(Id);
    }

    @GetMapping("/accessories/{id}")
    public Optional<Accessory> findAccessoryById(@PathVariable long Id) {
        return prodService.findAccessoryById(Id);
    }

    @GetMapping("/merchandise/{id}")
    public Optional<Merchandise> findMerchandiseById(@PathVariable long Id) { return prodService.findMerchandiseById(Id); }

    @GetMapping("/giftcards/{id}")
    public Optional<Giftcard> findGiftcardById(@PathVariable long Id) { return prodService.findGiftcardById(Id); }

    @GetMapping("/games/{id}")
    public Optional<Game> findGamesById(@PathVariable long Id) { return prodService.findGamesById(Id); }


    @PostMapping
    public Product saveProduct(@RequestBody @Valid Product product) {return (Product) prodService.saveProduct(product);}

    @PostMapping("/console")
    public Console saveConsole(@RequestBody @Valid Console console) {return prodService.saveConsole(console);}

    @PostMapping("/accessory")
    public Accessory saveAccessory(@RequestBody @Valid Accessory accessory) {return prodService.saveAccessory(accessory);}

    @PostMapping("/merchandise")
    public Merchandise saveMerchandise(@RequestBody @Valid Merchandise merchandise) {return prodService.saveMerchandise(merchandise);}

    @PostMapping("/giftcard")
    public Giftcard saveGiftcard(@RequestBody @Valid Giftcard giftcard) {return prodService.saveGiftcard(giftcard);}

    @PostMapping("/game")
    public Game saveGames(@RequestBody @Valid Game game) {return prodService.saveGame(game);}

    


    @DeleteMapping
    public Product deleteProduct(@RequestBody @Valid Product product) {
        return prodService.deleteProduct(product);
    }

    @DeleteMapping("/console")
    public Console deleteConsole(@RequestBody @Valid Console console) {
        return prodService.deleteConsole(console);
    }

    @DeleteMapping("/accessory")
    public Accessory deleteAccessory(@RequestBody @Valid Accessory accessory) {
        return prodService.deleteAccessory(accessory);
    }

    @DeleteMapping("/merchandise")
    public Merchandise deleteMerchandise(@RequestBody @Valid Merchandise merchandise) {
        return prodService.deleteMerchandise(merchandise);
    }

    @DeleteMapping("/giftcard")
    public Giftcard deleteGiftcard(@RequestBody @Valid Giftcard giftcard) {
        return prodService.deleteGiftcard(giftcard);
    }

    @DeleteMapping("/game")
    public Game deleteGame(@RequestBody @Valid Game game) {
        return prodService.deleteGame(game);
    }
}