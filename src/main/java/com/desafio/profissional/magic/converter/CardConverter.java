package com.desafio.profissional.magic.converter;

import com.desafio.profissional.magic.domain.Card;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.domain.record.CardRecordRes;
import org.springframework.stereotype.Component;

@Component
public class CardConverter {

    public static Card fromCardApiToCard(CardAPI record){
        Card card =  Card.builder().id(record.id())
                .cmc(record.cmc())
                .name(record.name())
                .type_line(record.type_line())
                .oracle_text(record.oracle_text())
                .mana_cost(record.mana_cost())
                .released_at(record.released_at())
                .build();
        card.setColors(record.colors());
        card.setColor_identity(record.color_identity());
        card.setProduced_mana(record.produced_mana());
        return card;
    }

    public static CardRecordRes toResFromCard(Card card){
        return new CardRecordRes(
                card.getId(),
                card.getName(),
                card.getCmc(),
                card.getType_line(),
                card.getColors(),
                card.getColor_identity()
        );
    }
}
