package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.domain.record.ImportDeckMessage;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.repository.DeckRepository;
import com.desafio.profissional.magic.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class DeckService {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckRepository repository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public List<Deck> findAll() {
        return repository.findAll();
    }

    public List<Deck> findDeckByUserId(Long userId) {
        return repository.findDeckByUserId(userId);
    }

    @CacheEvict(cacheNames = "deckCache", allEntries = true)
    public void setCommanderOnDeckByName(Long userId, Long deckId, String name) throws MagicValidatorException {
        List<String> colors = new ArrayList<>();
        Deck actualDeck = repository.findById(deckId).orElseThrow(() ->
                new MagicValidatorException("Deck doesn't have been set yet.")
        );

        isDeckFromUser(deckId, userId);

        if(Objects.nonNull(actualDeck.getCommander())){
            colors = actualDeck.getCommander().getColors();
        }
        actualDeck.setCommander(cardService.getCommanderByName(name));
        if(Objects.nonNull(actualDeck.getCommander()) && new HashSet<>(actualDeck.getCommander().getColors()).containsAll(colors)){
            actualDeck.getCards().clear();
        }
        repository.save(actualDeck);
    }

    @CacheEvict(cacheNames = "deckCache", allEntries = true)
    public void set99Cards(Long userId, Long deckId) throws MagicValidatorException {
        Deck actualDeck = repository.findById(deckId).orElseThrow(() ->
                new MagicValidatorException("Deck doesn't have been set yet.")
        );
        isDeckFromUser(deckId, userId);

        if(Objects.isNull(actualDeck.getCommander())){
            throw new MagicValidatorException("Commander doesn't hava been set yet.");
        }
        actualDeck.getCards().clear();
        actualDeck.getCards().addAll(cardService.getCardLimitDeckByCommander(actualDeck.getCommander()).subList(0,99));
        repository.save(actualDeck);
    }

    @CacheEvict(cacheNames = "deckCache", allEntries = true)
    public Deck createDeck(Deck deck, Long userId) throws MagicValidatorException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new MagicValidatorException("There isn't any player with this id"));
        deck.setUser(user);
        return repository.save(deck);
    }

    @Async
    @CacheEvict(cacheNames = "deckCache", allEntries = true)
    public void createDeckByFile(Long userId, String deckName, String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        amqpTemplate.convertAndSend(
                "deck_import_queue",
                objectMapper.writeValueAsString(new ImportDeckMessage(userId, deckName, json))
        );
    }

    public String addCardById(Long deckId, String cardId) {
        amqpTemplate.convertAndSend(
                "add-cards-queue",
                deckId + ";" + cardId
        );
        return "The card will be add on your deck";
    }

    private void isDeckFromUser(Long deckId, Long userId) throws MagicValidatorException {
        if(!repository.isDeckFromUser(deckId, userId)){
            throw new MagicValidatorException("This deck is not from this user");
        }
    }
}
