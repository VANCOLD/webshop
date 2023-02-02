package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Merchandise;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/merch")
public class MerchandiseController extends ProductController<Merchandise> {}