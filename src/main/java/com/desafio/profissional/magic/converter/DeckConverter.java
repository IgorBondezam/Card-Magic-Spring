package com.desafio.profissional.magic.converter;

import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.record.DeckRecordReq;
import com.desafio.profissional.magic.domain.record.DeckRecordRes;

import java.util.Objects;

public class DeckConverter {

    public static DeckRecordRes toResFromDeck(Deck deck) {
        return new DeckRecordRes(
                deck.getName(),
                Objects.nonNull(deck.getCommander()) ? CardConverter.toResFromCard(deck.getCommander()) : null,
                deck.getCards().stream().map(CardConverter::toResFromCard).toList()
        );
    }

    public static Deck fromReqToDeck(DeckRecordReq req) {
        Deck deck = new Deck();
        deck.setName(req.name());
        return deck;
    }
}
