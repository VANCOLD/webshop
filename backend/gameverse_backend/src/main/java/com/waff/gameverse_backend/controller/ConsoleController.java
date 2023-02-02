package com.waff.gameverse_backend.controller;

import org.springframework.web.bind.annotation.*;
import com.waff.gameverse_backend.datamodel.Console;

@RestController
@RequestMapping("/consoles")
public class ConsoleController extends ProductController<Console> {}
