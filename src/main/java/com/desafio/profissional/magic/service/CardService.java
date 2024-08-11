package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    public CardAPI getCommanderByName(String name) throws IOException, MagicValidatorException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String uri = "https://api.scryfall.com/cards/named?fuzzy="+name;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri,Object.class);
        CardAPI cards = objectMapper.convertValue(response.getBody(), CardAPI.class);
        if(!cards.type_line().contains("Legendary Creature")) {
            throw new MagicValidatorException("This card isn't a commander");
        }
        objectMapper.writeValue(new File("card.json"), cards);
        return cards;
    }

    public List<CardAPI> getCardByCommanderColor(String colorsFilter) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String uri = "https://api.scryfall.com/cards/search?q=commander:"+colorsFilter;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri,Object.class);
        List<CardAPI> cards = Arrays.asList(objectMapper.convertValue(((LinkedHashMap<String, Object>)response.getBody())
                .get("data"), CardAPI[].class));
        objectMapper.writeValue(new File("card.json"), cards);
        return cards;

    }
}
