package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Category;
import com.waff.gameverse_backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAllCategories()
    {
        List<Category> response = this.categoryService.findAllCategories();

        if (response.isEmpty() )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The category-list is empty!");
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGereById(@PathVariable Long id)
    {
        Optional<Category> returnValue = categoryService.findCategoryById(id);

        if( returnValue.isPresent()  )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No category found with the id " + id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id)
    {

        if(id < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A category with an id smaller than 0 can not exist!");

        Optional<Category> returnValue = this.categoryService.deleteCategory(id);

        if( returnValue.isPresent() )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The category with the id " + id + " doesn't exist!");
    }


    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody Category category)
    {
        if(category.getCid() < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A category with an id smaller than 0 can not exist!");

        Category returnValue = this.categoryService.saveCategory(category);

        if( returnValue != null )
            return ResponseEntity.ok(returnValue);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A category with the id " + category.getCid() + " already exists!");
    }


    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category)
    {
        Long cid = category.getCid();
        if(cid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A category with an id smaller than 0 can not exist!");


        Optional<Category> findValue = this.categoryService.findCategoryById(cid);
        Category returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The category with the id " + cid + " you are trying to update doesn't exist!");

        returnValue.setCid(cid);
        returnValue.setName(category.getName());

        return ResponseEntity.ok( this.categoryService.saveCategory(returnValue));
    }
}