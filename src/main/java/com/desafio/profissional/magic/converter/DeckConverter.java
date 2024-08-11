package com.desafio.profissional.magic.converter;

import com.desafio.profissional.magic.domain.record.DeckRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DeckConverter(String email, DeckRecord deck) {
}
