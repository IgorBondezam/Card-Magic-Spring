package com.desafio.profissional.magic.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardRecordRes(
        UUID id,
        String name,
        String imageUrl,
        Double cmc,
        String text,
        List<String> superTypes,
        List<String> types,
        List<String> subTypes,
        List<String> colors,
        List<String> color_identity
) {
}
