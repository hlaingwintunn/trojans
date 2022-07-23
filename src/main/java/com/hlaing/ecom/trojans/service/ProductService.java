package com.hlaing.ecom.trojans.service;

import com.hlaing.ecom.trojans.dto.product.ProductDto;
import com.hlaing.ecom.trojans.model.Category;
import com.hlaing.ecom.trojans.model.Product;
import com.hlaing.ecom.trojans.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> productList() {
        return productRepository.findAll();
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Product readProduct(String productName) {
        return productRepository.findProductByName(productName);
    }

    public Optional<Product> readProduct(Integer productId) {
        return productRepository.findById(productId);
    }

    public Product updateProduct(Integer productId, Product newProduct) {
        Product product = productRepository.findById(productId).get();
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        product.setImageUrl(newProduct.getImageUrl());
        product.setDescription(newProduct.getDescription());
        return product;
    }

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }

    private Product getProductFromDto(ProductDto productDto, Category category) {
        return Product.builder()
                .category(category)
                .description(productDto.getDescription())
                .imageUrl(productDto.getImageURL())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
    }
}
