package com.desafio.profissional.magic.converter;

import com.desafio.profissional.magic.domain.Card;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import org.springframework.stereotype.Component;

@Component
public class CardConverter {

    public static Card from(CardAPI record){
        return Card.builder().id(record.id())
                .cmc(record.cmc())
                .name(record.name())
                .color_identity(record.color_identity())
                .type_line(record.type_line())
                .oracle_text(record.oracle_text())
                .colors(record.colors())
                .mana_cost(record.mana_cost())
                .produced_mana(record.produced_mana())
                .released_at(record.released_at())
                .build();
    }
}
