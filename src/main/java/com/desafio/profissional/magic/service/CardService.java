package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.Card;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.domain.record.CardRecordRes;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.repository.CardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Value("${api.utils.totalPage}")
    private Long totalPage;

    public List<CardRecordRes> findAll() {
        return repository.findAll().stream().map(CardConverter::toResFromCard).toList();
    }

    public Card getCommanderByName(String name) throws MagicValidatorException {

        List<Card> commander = repository.findCommanderByName(name);
        if (commander.isEmpty()) {
            throw new MagicValidatorException("This card isn't a commander");
        }
        return commander.get(0);
    }

    public List<Card> getCardsByCommandColor(String colorsFilter) {
        List<Card> cards = new ArrayList<>();
        List<String> colors = Arrays.asList(colorsFilter.split(""));
        colors.forEach(c -> {
            cards.addAll(repository.findNotCommanderByColor(c));
        });
        return cards;
    }

    public List<Card> getCardLimitDeckByCommander(Card commander) {
        String colors = String.join("", commander.getColors());
        return getCardsByCommandColor(colors);
    }

    @Transactional
    @Async
    public void populetedDatabase() {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 1; i <= totalPage; i++) {
            final int page = i;
            CompletableFuture.runAsync(() -> {
                List<CardAPI> result = fetchPageData("https://api.magicthegathering.io/v1/cards?page=" + page);
                repository.saveAll(result.stream().map(CardConverter::fromCardApiToCard).toList());
            }, executor);
        }
        executor.shutdown();
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


    private List<CardAPI> fetchPageData(String url) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return Arrays.asList(objectMapper.convertValue(((LinkedHashMap<String, Object>) response.getBody())
                .get("cards"), CardAPI[].class));
    }
}
