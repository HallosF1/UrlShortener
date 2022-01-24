package com.hallos.urlshortener.service;

import com.hallos.urlshortener.exception.CodeAlreadyExists;
import com.hallos.urlshortener.exception.ShortUrlNotFoundException;
import com.hallos.urlshortener.model.ShortUrl;
import com.hallos.urlshortener.repository.ShortUrlRepository;
import com.hallos.urlshortener.util.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final RandomStringGenerator generator;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, RandomStringGenerator generator) {
        this.shortUrlRepository = shortUrlRepository;
        this.generator = generator;
    }

    public List<ShortUrl> getAllUrls() {
        return shortUrlRepository.findAll();
    }

    public ShortUrl getUrlByCode(String code) {
        return shortUrlRepository.findAllByCode(code).orElseThrow(
                () -> new ShortUrlNotFoundException("Could not find")
        );
    }

    public ShortUrl create(ShortUrl shortUrl) {
        if (shortUrl.getCode() == null || shortUrl.getCode().isEmpty()) {
            shortUrl.setCode(generateCode());
        } else if (shortUrlRepository.findAllByCode(shortUrl.getCode()).isPresent()) {
            throw new CodeAlreadyExists("Already exists");
        }

        shortUrl.setCode(shortUrl.getCode().toUpperCase());
        return shortUrlRepository.save(shortUrl);
    }


    private String generateCode() {
        String code;

        do {
            code = generator.generateRandomString();
        } while (shortUrlRepository.findAllByCode(code).isPresent());

        return code;
    }
}
