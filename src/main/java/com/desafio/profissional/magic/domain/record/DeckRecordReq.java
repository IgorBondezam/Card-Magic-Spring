package com.desafio.profissional.magic.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeckRecordReq(String name) implements Serializable {
}
