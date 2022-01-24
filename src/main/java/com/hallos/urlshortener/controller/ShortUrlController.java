package com.hallos.urlshortener.controller;

import com.hallos.urlshortener.dto.ShortUrlDto;
import com.hallos.urlshortener.dto.converter.ShortUrlDtoConverter;
import com.hallos.urlshortener.model.ShortUrl;
import com.hallos.urlshortener.request.ShortUrlRequest;
import com.hallos.urlshortener.request.converter.ShortUrlRequestConverter;
import com.hallos.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping
public class ShortUrlController {

    private final ShortUrlDtoConverter shortUrlDtoConverter;
    private final ShortUrlRequestConverter shortUrlRequestConverter;
    private final ShortUrlService urlService;

    public ShortUrlController(ShortUrlDtoConverter shortUrlDtoConverter, ShortUrlRequestConverter shortUrlRequestConverter, ShortUrlService urlService) {
        this.shortUrlDtoConverter = shortUrlDtoConverter;
        this.shortUrlRequestConverter = shortUrlRequestConverter;
        this.urlService = urlService;
    }

    @GetMapping
    public ResponseEntity<List<ShortUrlDto>> getAllUrls() {
        return new ResponseEntity<List<ShortUrlDto>>(
                shortUrlDtoConverter.convert(urlService.getAllUrls()), HttpStatus.OK
        );
    }

    @GetMapping("/show/{code}")
    public ResponseEntity<ShortUrlDto> getUrlByCode(@Valid @NotEmpty @PathVariable String code) {
        return new ResponseEntity<ShortUrlDto>(
                shortUrlDtoConverter.convert(urlService.getUrlByCode(code)), HttpStatus.OK
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<ShortUrlDto> redirect(@Valid @NotEmpty @PathVariable String code) throws URISyntaxException {

        ShortUrl shortUrl = urlService.getUrlByCode(code);

        URI uri = new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(
                httpHeaders, HttpStatus.SEE_OTHER
        );
    }

    @PostMapping
    public ResponseEntity<ShortUrlDto> create(@Valid @RequestBody ShortUrlRequest shortUrlRequest) {
        ShortUrl shortUrl = shortUrlRequestConverter.convert(shortUrlRequest);
        return new ResponseEntity<ShortUrlDto>(shortUrlDtoConverter.convert(urlService.create(shortUrl)), HttpStatus.OK);
    }
}
