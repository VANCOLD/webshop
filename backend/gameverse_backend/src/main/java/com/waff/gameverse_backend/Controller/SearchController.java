package com.waff.gameverse_backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SearchController {
// das ist mein Kommentar

    @GetMapping("/search")
    public ArrayList<Object> searchFor()
    {
        return new ArrayList<Object>();
    }
}