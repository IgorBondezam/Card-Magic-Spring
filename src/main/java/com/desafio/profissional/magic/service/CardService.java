package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.Card;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.domain.record.CardRecordRes;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.exception.UserException;
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

    public List<CardRecordRes> findAll() {
        return repository.findAll().stream().map(CardConverter::toResFromCard).toList();
    }

    public CardAPI getCommanderByName(String name) throws MagicValidatorException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String uri = "https://api.scryfall.com/cards/named?fuzzy="+name;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri,Object.class);
        CardAPI card = objectMapper.convertValue(response.getBody(), CardAPI.class);
        if(!card.type_line().contains("Legendary Creature")) {
            throw new MagicValidatorException("This card isn't a commander");
        }
        repository.save(CardConverter.fromCardApiToCard(card));
        return card;
    }

    public List<CardAPI> populetedByCommanderColor(String colorsFilter) throws IOException {
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

    public List<CardAPI> getCardLimitDeckByCommander(Card commander) throws IOException {
        String colors = String.join("", commander.getColors());
        return populetedByCommanderColor(colors);
    }

    public void createJsonCardsByUserId(Long userId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Card> cards = findCardByUserId(userId);
        cards.add(findCommanderByUserId(userId));
        objectMapper.writeValue(new File("card.json"), cards);
    }

    private List<Card> findCardByUserId(Long userId) {
        return repository.findCardByUserId(userId);
    }

    private Card findCommanderByUserId(Long userId) {
        return repository.findCommanderByUserId(userId);
    }
}
