package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.converter.DeckConverter;
import com.desafio.profissional.magic.domain.record.DeckRecordReq;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/deck")
public class DeckController {

    @Autowired
    private DeckService service;

    @PatchMapping("choose/commander/{userId}/{name}")
    public ResponseEntity<String> chooseCommanderByName(@PathVariable("userId") Long userId,
                                                        @PathVariable("name") String name) throws IOException, MagicValidatorException {
        service.setCommanderOnDeckByName(userId, name);
        return ResponseEntity.ok("Commander setted in your deck.");
    }

    @PatchMapping("choose/random/99/{userId}")
    public ResponseEntity<String> chooseRandom99Cards(@PathVariable("userId") Long userId) throws IOException, MagicValidatorException {
        service.set99Cards(userId);
        return ResponseEntity.ok("Cards setted in your deck.");
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> createDeck(@PathVariable("userId") Long userId, @RequestBody DeckRecordReq req) throws MagicValidatorException, UserException {
        service.createDeck(DeckConverter.fromReqToDeck(req), userId);
        return ResponseEntity.ok("Deck has been created");
    }
}
