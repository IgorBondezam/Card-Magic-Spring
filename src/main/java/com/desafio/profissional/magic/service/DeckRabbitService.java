package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.domain.record.ImportDeckMessage;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.repository.DeckRepository;
import com.desafio.profissional.magic.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DeckRabbitService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private ObjectMapper om;

    @RabbitListener(queues = {"add-cards-queue"})
    @Transactional
    public void addCardById(@Payload Message message) {
        List<String> payload = Arrays.stream(((String) message.getPayload()).split(";")).toList();
        Deck deck = deckRepository.findById(Long.parseLong(payload.get(0))).get();
        if (deck.getCards().stream().anyMatch(c -> c.getId().equals(UUID.fromString(payload.get(1))))) {
           return;
        }
        deck.getCards().add(cardService.findById(UUID.fromString(payload.get(1))).get());
        deckRepository.save(deck);
    }

    @RabbitListener(queues = {"deck_import_queue"})
    @Transactional
    public void importDeck(@Payload Message message) {
        String messageReturn = "";
        try {
            ImportDeckMessage deckMessage = om.readValue((String) message.getPayload(), ImportDeckMessage.class);
            Deck deck = new Deck();
            List<CardAPI> cards = readCardFile(deckMessage.cards());
            List<CardAPI> commander = getCommanderInCards(cards);
            List<String> commanderColor = commander.get(0).colors();
            deck.setName(deckMessage.deckName());
            cardService.validCommander(commander);
            deck.setCommander(CardConverter.fromCardApiToCard(commander.get(0)));
            cards.remove(commander.get(0));
            validCardsNoCommander(cards, commanderColor);
            deck.getCards().addAll(cardService.getAllCardsInId(cards.stream().map(CardAPI::id).toList()));
            createDeck(deck, deckMessage.userId());
        } catch (Exception e) {
            messageReturn = e.getMessage();
        }
        if(messageReturn.isEmpty()) {
            messageReturn = "Deck was success imported\n";
        }
        System.out.printf(messageReturn);
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

    public void createDeck(Deck deck, Long userId) throws MagicValidatorException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new MagicValidatorException("There isn't any player with this id"));
        deck.setUser(user);
        deckRepository.save(deck);
    }
}
