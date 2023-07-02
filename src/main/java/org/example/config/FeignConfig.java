package org.example.config;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import org.example.client.AdidasClient;
import org.example.client.ReviewClient;
import org.example.exceptions.FeignErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${adidas.client.url}")
    private String adidasUrl;

    @Value("${review.client.url}")
    private String reviewUrl;

    @Bean
    public AdidasClient adidasClient() {
        return Feign.builder()
                .encoder(new Encoder.Default())
                .decoder(new Decoder.Default())
                .errorDecoder(new FeignErrorDecoder())
                .target(AdidasClient.class, adidasUrl);
    }

    @Bean
    public ReviewClient reviewClient() {
        return Feign.builder()
                .encoder(new Encoder.Default())
                .decoder(new JacksonDecoder())
                .errorDecoder(new FeignErrorDecoder())
                .target(ReviewClient.class, reviewUrl);
    }
}
