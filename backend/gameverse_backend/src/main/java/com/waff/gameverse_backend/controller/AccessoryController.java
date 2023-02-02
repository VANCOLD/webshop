package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Accessory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accessories")
public class AccessoryController extends ProductController<Accessory> {}
