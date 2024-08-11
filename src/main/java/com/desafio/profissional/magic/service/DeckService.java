package com.desafio.profissional.magic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckService {

    @Autowired
    private CardService cardService;

    public void setCommanderOnDeckByName(String name) {

        cardService.getCommanderByName(name);
    }
}
