package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.repository.CardRepository;
import com.desafio.profissional.magic.repository.DeckRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DeckRabbitService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    @RabbitListener(queues = {"magic-queue"})
    @Transactional
    public void addCardById(@Payload Message message) {
        List<String> payload = Arrays.stream(((String) message.getPayload()).split(";")).toList();
        Deck deck = deckRepository.findById(Long.parseLong(payload.get(0))).get();
        if (deck.getCards().stream().anyMatch(c -> c.getId().equals(UUID.fromString(payload.get(1))))) {
           return;
        }
        deck.getCards().add(cardRepository.findById(UUID.fromString(payload.get(1))).get());
        deckRepository.save(deck);
    }
}
