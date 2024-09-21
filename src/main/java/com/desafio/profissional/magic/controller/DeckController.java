package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.converter.DeckConverter;
import com.desafio.profissional.magic.domain.Deck;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.DeckRecordReq;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.DeckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/deck")
@SecurityRequirement(name = "bearer-key")
@Log4j2
public class DeckController {

    @Autowired
    private DeckService service;

    @GetMapping()
    public ResponseEntity findAllDecks() {
        try {
            List<Deck> decks = service.findAll();
            return ResponseEntity.ok(decks.stream().map(DeckConverter::toResFromDeck));
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("user")
    public ResponseEntity findDeckLoggedInUser(Authentication auth) {
        try {
            Long userId = ((User) auth.getPrincipal()).getId();
            List<Deck> decks = service.findDeckByUserId(userId);
            return ResponseEntity.ok(decks.stream().map(DeckConverter::toResFromDeck));
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("choose/commander/{deckId}/{name}")
    public ResponseEntity<String> chooseCommanderByName(@PathVariable("deckId") Long deckId, @PathVariable("name") String name, Authentication auth) {
        try {
            Long userId = ((User) auth.getPrincipal()).getId();
            service.setCommanderOnDeckByName(userId, deckId, name);
            return ResponseEntity.ok("Commander setted in your deck.");
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("choose/random/99/{deckId}")
    public ResponseEntity<String> chooseRandom99Cards(@PathVariable("deckId") Long deckId, Authentication auth) {
        try {
            Long userId = ((User) auth.getPrincipal()).getId();
            service.set99Cards(userId, deckId);
            return ResponseEntity.ok("Cards setted in your deck.");
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> createDeck(@PathVariable("userId") Long userId, @RequestBody DeckRecordReq req) {
        try {
            Deck saved = service.createDeck(DeckConverter.fromReqToDeck(req), userId);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/user/{userId}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body("Deck has been created");
        } catch (Exception e) {
            log.error(e.getStackTrace());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
