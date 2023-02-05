package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Console;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consoles")
public class ConsoleController extends  ProductController<Console> {

}
