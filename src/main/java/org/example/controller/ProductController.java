package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.ProductWithReview;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    ProductWithReview getProductAndReview(@PathVariable String productId) {
        return productService.getProductWithReview(productId);
    }
}
