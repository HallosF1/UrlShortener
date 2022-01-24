package com.hallos.urlshortener.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortUrlRequest {

    @NotEmpty
    @NotNull
    private String url;

    private String code;
}
