package com.desafio.profissional.magic.domain.record;

public record ImportDeckMessage(Long userId, String deckName, String cards) {
}
