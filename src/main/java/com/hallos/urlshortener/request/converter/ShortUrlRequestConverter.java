package com.hallos.urlshortener.request.converter;

import com.hallos.urlshortener.model.ShortUrl;
import com.hallos.urlshortener.request.ShortUrlRequest;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlRequestConverter {

    public ShortUrl convert(ShortUrlRequest shortUrlRequest) {
        return ShortUrl.builder()
                .url(shortUrlRequest.getUrl())
                .code(shortUrlRequest.getCode())
                .build();
    }
}
