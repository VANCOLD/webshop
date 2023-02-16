package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.repository.*;
import com.waff.gameverse_backend.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/products")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProductController
{

    private ProductService prodService;

}
