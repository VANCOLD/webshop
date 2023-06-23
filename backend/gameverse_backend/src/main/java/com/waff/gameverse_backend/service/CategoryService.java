package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.Category;
import com.waff.gameverse_backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository catRepo;

    public CategoryService(CategoryRepository catRepo) { this.catRepo = catRepo;}


    public Optional<Category> findCategoryById(Long cid) { return this.catRepo.findById(cid); }
    public List<Category> findAllCategories() { return this.catRepo.findAll(); }
    public Category saveCategory(Category category) {

        Optional<Category> checkStatus = this.catRepo.findById(category.getCid());

        if( checkStatus.isEmpty() ) {
            return this.catRepo.save(category);
        } else {
            return null;
        }
    }

    public Optional<Category> deleteCategory(Long cid) {

        Optional<Category> returnCategory = this.findCategoryById(cid);

        if( returnCategory.isPresent() ) {
            this.catRepo.deleteById(cid);
        }

        return returnCategory;

    }
}
