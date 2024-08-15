package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.repository.CardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public CardAPI getCommanderByName(String name) throws IOException, MagicValidatorException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String uri = "https://api.scryfall.com/cards/named?fuzzy="+name;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri,Object.class);
        CardAPI card = objectMapper.convertValue(response.getBody(), CardAPI.class);
        if(!card.type_line().contains("Legendary Creature")) {
            throw new MagicValidatorException("This card isn't a commander");
        }
        objectMapper.writeValue(new File("card.json"), card);
        repository.save(CardConverter.fromCardApiToCard(card));
        return card;
    }

    public List<CardAPI> getCardByCommanderColor(String colorsFilter) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String uri = "https://api.scryfall.com/cards/search?q=commander:"+colorsFilter;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri,Object.class);
        List<CardAPI> cards = Arrays.asList(objectMapper.convertValue(((LinkedHashMap<String, Object>)response.getBody())
                .get("data"), CardAPI[].class));
        objectMapper.writeValue(new File("card.json"), cards);
        cards.forEach(c -> repository.save(CardConverter.fromCardApiToCard(c)));
        return cards;
    }
}
