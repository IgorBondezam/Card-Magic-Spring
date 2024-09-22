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
                .text(record.text())
                .manaCost(record.manaCost())
                .imageUrl(record.imageUrl())
                .build();
        card.setSuperTypes(record.supertypes());
        card.setTypes(record.types());
        card.setSubTypes(record.subtypes());
        card.setColors(record.colors());
        card.setColorIdentity(record.colorIdentity());
        return card;
    }

    public static CardAPI fromCardToCardApi(Card card){
        return new CardAPI(
                card.getId(), card.getName(), card.getImageUrl(), card.getCmc(),
                card.getManaCost(), card.getText(), card.getSuperTypes(),
                card.getTypes(), card.getSubTypes(), card.getColorIdentity(),
                card.getColors()
        );
    }

    public static CardRecordRes toResFromCard(Card card){
        return new CardRecordRes(
                card.getId(),
                card.getName(),
                card.getImageUrl(),
                card.getCmc(),
                card.getText(),
                card.getSuperTypes(),
                card.getTypes(),
                card.getSubTypes(),
                card.getColors(),
                card.getColorIdentity()
        );
    }
}
