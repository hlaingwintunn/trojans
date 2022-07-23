package com.hlaing.ecom.trojans.controller;

import com.hlaing.ecom.trojans.common.ApiResponse;
import com.hlaing.ecom.trojans.model.Category;
import com.hlaing.ecom.trojans.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.listCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
        if(Objects.nonNull(categoryService.readCategory(category.getCategoryName()))) {
            return new ResponseEntity<>(new ApiResponse(false, "Category already exists"), HttpStatus.CONFLICT);
        }
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Created the category"),HttpStatus.CREATED);
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Integer categoryId, @Valid @RequestBody Category category) {
        if (Objects.nonNull(categoryService.readCategory(categoryId))) {
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(new ApiResponse(true, "Updated the category"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(false, "Category does not found"), HttpStatus.NOT_FOUND);
    }
}
