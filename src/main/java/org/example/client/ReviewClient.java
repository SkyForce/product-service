package org.example.client;

import feign.Param;
import feign.RequestLine;
import org.example.model.Review;

public interface ReviewClient {
    @RequestLine("GET /{productId}")
    Review getReview(@Param("productId") String productId);
}
