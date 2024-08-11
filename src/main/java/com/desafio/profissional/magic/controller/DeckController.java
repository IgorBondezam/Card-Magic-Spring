package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/deck")
public class DeckController {

    @Autowired
    private DeckService service;

    @PostMapping("choose/commander/{userId}/{name}")
    public ResponseEntity<String> chooseCommanderByName(@PathVariable("userId") Long userId,
                                                        @PathVariable("name") String name) throws IOException, MagicValidatorException {
        service.setCommanderOnDeckByName(userId, name);
        return ResponseEntity.ok("Commander setted in your deck.");
    }

    @PostMapping("choose/random/99/{userId}")
    public ResponseEntity<String> chooseRandom99Cards(@PathVariable("userId") Long userId) throws IOException, MagicValidatorException {
        service.set99Cards(userId);
        return ResponseEntity.ok("Cards setted in your deck.");
    }
}
