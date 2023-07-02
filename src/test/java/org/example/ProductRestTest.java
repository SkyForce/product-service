package org.example;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.example.client.AdidasClient;
import org.example.client.ReviewClient;
import org.example.model.ProductWithReview;
import org.example.model.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductRestTest {
    @LocalServerPort
    private int port;

    private RequestSpecification req;

    @MockBean
    private ReviewClient reviewClient;

    @MockBean
    private AdidasClient adidasClient;

    @Autowired
    private Map<String, String> productsCache;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        req = given();
    }

    @AfterEach
    public void clearCache() {
        productsCache.clear();
    }

    @Test
    public void testGet() {
        Mockito.when(reviewClient.getReview(ArgumentMatchers.eq("qwe"))).thenReturn(new Review("qwe", 0.5, 3));
        Mockito.when(adidasClient.getProduct(ArgumentMatchers.eq("qwe"))).thenReturn("product");
        ProductWithReview response = req.get("/product/qwe")
                .then()
                .statusCode(200)
                .extract().body().as(ProductWithReview.class);

        assertEquals(new ProductWithReview("product", new Review("qwe", 0.5, 3)), response);
    }

    @Test
    public void testGetNoReview() {
        Mockito.when(reviewClient.getReview(ArgumentMatchers.eq("qwe"))).thenThrow(NoSuchElementException.class);
        Mockito.when(adidasClient.getProduct(ArgumentMatchers.eq("qwe"))).thenReturn("product");
        ProductWithReview response = req.get("/product/qwe")
                .then()
                .statusCode(200)
                .extract().body().as(ProductWithReview.class);

        assertEquals(new ProductWithReview("product", null), response);
    }

    @Test
    public void testGetNoProduct() {
        Mockito.when(adidasClient.getProduct(ArgumentMatchers.eq("qwe"))).thenThrow(NoSuchElementException.class);
        Review review = new Review("qwe", 0.5, 3);
        Mockito.when(reviewClient.getReview(ArgumentMatchers.eq("qwe"))).thenReturn(review);
        ProductWithReview response = req.get("/product/qwe")
                .then()
                .statusCode(200)
                .extract().body().as(ProductWithReview.class);

        assertEquals(new ProductWithReview(null, review), response);
    }

    @Test
    public void testGetNoProductNoReview() {
        Mockito.when(adidasClient.getProduct(ArgumentMatchers.eq("qwe"))).thenThrow(NoSuchElementException.class);
        Mockito.when(reviewClient.getReview(ArgumentMatchers.eq("qwe"))).thenThrow(NoSuchElementException.class);;
        req.get("/product/qwe")
                .then()
                .statusCode(404);
    }
}
