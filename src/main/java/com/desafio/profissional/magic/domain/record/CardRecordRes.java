package com.desafio.profissional.magic.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardRecordRes(
        UUID id,
        String name,
        Double cmc,
        String type_line,
        List<String> colors
) {
}
