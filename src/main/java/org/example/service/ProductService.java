package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.client.AdidasClient;
import org.example.client.ReviewClient;
import org.example.model.ProductWithReview;
import org.example.model.Review;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final AdidasClient adidasClient;
    private final ReviewClient reviewClient;
    private final Map<String, String> productsCache;

    public ProductWithReview getProductWithReview(String productId) {
        String product = Optional.ofNullable(productsCache.get(productId))
                .orElseGet(() -> {
                    String productDesc = handleNotFoundException(adidasClient::getProduct, productId);
                    if (productDesc != null) {
                        productsCache.put(productId, productDesc);
                    }
                    return productDesc;
                });
        Review review = handleNotFoundException(reviewClient::getReview, productId);

        if (product == null && review == null) {
            throw new NoSuchElementException();
        }

        return ProductWithReview.builder()
                .product(product)
                .review(review)
                .build();
    }

    <T> T handleNotFoundException(Function<String, T> func, String arg) {
        T result;
        try {
            result = func.apply(arg);
        }
        catch (NoSuchElementException e) {
            result = null;
        }
        return result;
    }
}
