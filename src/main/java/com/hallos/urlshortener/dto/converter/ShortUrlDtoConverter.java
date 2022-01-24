package com.hallos.urlshortener.dto.converter;

import com.hallos.urlshortener.dto.ShortUrlDto;
import com.hallos.urlshortener.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShortUrlDtoConverter {

    public ShortUrlDto convert(ShortUrl from) {
        return ShortUrlDto.builder()
                .id(from.getId())
                .url(from.getUrl())
                .code(from.getCode())
                .build();
    }

    public List<ShortUrlDto> convert(List<ShortUrl> shortUrls) {
        return shortUrls.stream().map(u -> convert(u)).collect(Collectors.toList());
    }
}
