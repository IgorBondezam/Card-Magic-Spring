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
import java.util.Objects;

@Service
public class DeckService {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckRepository repository;

    public Deck findDeckByUserId(Long userId) {
        return repository.findDeckByUserId(userId);
    }

    public void setCommanderOnDeckByName(Long userId, String name) throws IOException, MagicValidatorException {
        Deck actualDeck = findDeckByUserId(userId);
        actualDeck.setCommander(CardConverter.fromCardApiToCard(cardService.getCommanderByName(name)));
        repository.save(actualDeck);
    }

    public void set99Cards(Long userId) throws MagicValidatorException, IOException {
        Deck actualDeck = findDeckByUserId(userId);
        if(Objects.isNull(actualDeck)){
            throw new MagicValidatorException("Deck doesn't have been set yet.");
        }
        if(Objects.isNull(actualDeck.getCommander())){
            throw new MagicValidatorException("Commander doesn't hava been set yet.");
        }
        actualDeck.setCards(cardService.getCardByCommanderColor(
                String.join("", actualDeck.getCommander().getColors()))
                .stream().map(CardConverter::fromCardApiToCard).toList().subList(0, 99));
    }

    public void createDeck(Deck deck, Long userId) throws MagicValidatorException, UserException {
        if(userRepository.hasUserWithoutDeck(userId)){
            throw new MagicValidatorException("This player already has a deck");
        }
        User user = userRepository.findById(userId).get();
        deck.setUser(user);
        Deck saved = repository.save(deck);
        user.setDeck(saved);
        userRepository.save(user);
    }
}
