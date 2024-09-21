package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.repository.DeckRepository;
import com.desafio.profissional.magic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Deck createDeck(Deck deck, Long userId) throws MagicValidatorException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new MagicValidatorException("There isn't any player with this id"));
        deck.setUser(user);
        Deck saved = repository.save(deck);
        user.getDeck().add(saved);
        userRepository.save(user);
        return saved;
    }

    private void isDeckFromUser(Long deckId, Long userId) throws MagicValidatorException {
        if(!repository.isDeckFromUser(deckId, userId)){
            throw new MagicValidatorException("This deck is not from this user");
        }
    }
}
