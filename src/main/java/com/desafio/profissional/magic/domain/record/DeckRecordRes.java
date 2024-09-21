package com.desafio.profissional.magic.domain.record;

import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeckRecordRes(String deckName, CardRecordRes commander, List<CardRecordRes> cards) {
}
