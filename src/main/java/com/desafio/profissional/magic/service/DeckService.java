package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.repository.DeckRepository;
import com.desafio.profissional.magic.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DeckService {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckRepository repository;

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

    @CacheEvict(cacheNames = "deckCache", allEntries = true)
    public Deck createDeckByFile(Long userId, String deckName, String json) throws IOException, MagicValidatorException {
        Deck deck = new Deck();
        deck.setName(deckName);
        List<CardAPI> cards = readCardFile(json);
        List<CardAPI> commander = getCommanderInCards(cards);
        List<String> commanderColor = commander.get(0).colors();
        cardService.validCommander(commander);
        deck.setCommander(CardConverter.fromCardApiToCard(commander.get(0)));
        cards.remove(commander.get(0));
        validCardsNoCommander(cards, commanderColor);
        deck.setCards(cards.stream().map(CardConverter::fromCardApiToCard).toList());
        return createDeck(deck, userId);
    }

    private void validCardsNoCommander(List<CardAPI> cards, List<String> commanderColor) throws MagicValidatorException {
        List<CardAPI> invalidCards = cards.stream()
                .filter(c -> c.colors().stream().noneMatch(commanderColor::contains)).toList();
        if(!invalidCards.isEmpty()) {
            throw new MagicValidatorException("There are cards those are out of the commander colors rule");
        }
    }

    private List<CardAPI> getCommanderInCards(List<CardAPI> cards) {
        return cards.stream().filter(c -> c.supertypes().contains("Legendary")).toList();
    }

    private List<CardAPI> readCardFile(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CardAPI> cards = new ArrayList<>(Arrays.stream(objectMapper.readValue(json, CardAPI[].class)).toList());
        return cards;
    }

    private void isDeckFromUser(Long deckId, Long userId) throws MagicValidatorException {
        if(!repository.isDeckFromUser(deckId, userId)){
            throw new MagicValidatorException("This deck is not from this user");
        }
    }
}
