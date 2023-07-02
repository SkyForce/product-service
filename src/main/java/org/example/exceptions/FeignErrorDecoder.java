package org.example.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.util.NoSuchElementException;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            return new NoSuchElementException();
        }
        return defaultDecoder.decode(s, response);
    }
}
