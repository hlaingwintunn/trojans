package com.hlaing.ecom.trojans.controller;

import com.hlaing.ecom.trojans.common.ApiResponse;
import com.hlaing.ecom.trojans.dto.product.ProductDto;
import com.hlaing.ecom.trojans.model.Category;
import com.hlaing.ecom.trojans.model.Product;
import com.hlaing.ecom.trojans.service.CategoryService;
import com.hlaing.ecom.trojans.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> productList() {
        List<Product> productList = productService.productList();
        return new ResponseEntity<>(productList , HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category is invalid"), HttpStatus.CONFLICT);
        }
        productService.addProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "Product created"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Product product) {
        if(Objects.nonNull(productService.readProduct(productId))) {
            productService.updateProduct(productId, product);
            return new ResponseEntity<>(new ApiResponse(true, "Product Updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Product Not Found"), HttpStatus.NOT_FOUND);
    }
}
