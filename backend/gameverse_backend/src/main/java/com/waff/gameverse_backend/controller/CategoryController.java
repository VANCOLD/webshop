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
            return new ResponseEntity<>("The category-list is empty!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGereById(@PathVariable Long id)
    {
        Optional<Category> returnValue = categoryService.findCategoryById(id);

        if( returnValue.isPresent()  )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("No category found with the id " + id, HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id)
    {

        if(id < 0)
            return new ResponseEntity<>("A category with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Optional<Category> returnValue = this.categoryService.deleteCategory(id);

        if( returnValue.isPresent() )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("The category with the id " + id + " doesn't exist!", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody Category category)
    {
        if(category.getCid() < 0)
            return new ResponseEntity<>("A category with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Category returnValue = this.categoryService.saveCategory(category);

        if( returnValue != null )
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        else
            return new ResponseEntity<>("A category with the id " + category.getCid() + " already exists!", HttpStatus.CONFLICT);
    }


    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category)
    {
        Long cid = category.getCid();
        if(cid < 0)
            return new ResponseEntity<>("A category with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);


        Optional<Category> findValue = this.categoryService.findCategoryById(cid);
        Category returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return new ResponseEntity<>("The category with the id " + cid + " you are trying to update doesn't exist!", HttpStatus.CONFLICT);

        returnValue.setCid(cid);
        returnValue.setName(category.getName());

        return new ResponseEntity<>( this.categoryService.saveCategory(returnValue), HttpStatus.OK );
    }
}