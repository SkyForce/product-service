package org.example.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({
        "Connection: keep-alive",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36",
        "Upgrade-Insecure-Requests: 1",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Language: en-US,en;q=0.9",
        "Accept-Encoding: gzip, deflate"
})
public interface AdidasClient {
    @RequestLine("GET /{productId}")
    String getProduct(@Param("productId") String productId);
}
