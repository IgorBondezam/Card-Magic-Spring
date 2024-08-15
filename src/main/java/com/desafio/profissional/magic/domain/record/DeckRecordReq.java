package com.desafio.profissional.magic.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeckRecordReq(String name) {
}
