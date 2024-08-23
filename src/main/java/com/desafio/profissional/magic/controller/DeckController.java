package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.converter.DeckConverter;
import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.record.DeckRecordReq;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.DeckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/deck")
@SecurityRequirement(name = "bearer-key")
public class DeckController {

    @Autowired
    private DeckService service;

    @PatchMapping("choose/commander/{userId}/{name}")
    public ResponseEntity<String> chooseCommanderByName(@PathVariable("userId") Long userId,
                                                        @PathVariable("name") String name) throws IOException, MagicValidatorException {
        try {
            service.setCommanderOnDeckByName(userId, name);
            return ResponseEntity.ok("Commander setted in your deck.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("choose/random/99/{userId}")
    public ResponseEntity<String> chooseRandom99Cards(@PathVariable("userId") Long userId) throws IOException, MagicValidatorException {
        try {
            service.set99Cards(userId);
            return ResponseEntity.ok("Cards setted in your deck.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> createDeck(@PathVariable("userId") Long userId, @RequestBody DeckRecordReq req) throws MagicValidatorException, UserException {
        try {
            Deck saved = service.createDeck(DeckConverter.fromReqToDeck(req), userId);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/user/{userId}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body("Deck has been created");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
