package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProductController
{

    private ProductService prodService;

}
