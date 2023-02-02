package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Game;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController extends ProductController<Game>{}