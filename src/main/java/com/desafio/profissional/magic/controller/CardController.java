package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.domain.enums.ColorCard;
import com.desafio.profissional.magic.service.CardService;
import com.desafio.profissional.magic.service.returnService.CardReturnService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/card")
@SecurityRequirement(name = "bearer-key")
public class CardController {

    @Autowired
    private CardService service;

    @Autowired
    private CardReturnService returnService;

    @GetMapping("commander/{name}")
    public ResponseEntity getCommanderByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(returnService.getCommanderByName(name));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("color")
    public ResponseEntity getByCommanderColor(@RequestParam("colors") List<ColorCard> colors) {
        try {
            return ResponseEntity.ok(returnService.getCardsByCommandColor(colors.stream().map(ColorCard::getAcronym)
                    .collect(Collectors.joining(""))));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("seed")
    public ResponseEntity populetedAllDataBase() {
        try {
            service.populetedDatabase();
            return ResponseEntity.ok().body("All cards is loading on back door");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity findAllCards() {
        try {
            return ResponseEntity.ok(returnService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/json/{deckId}")
    public ResponseEntity createJsonCardsByDeckId(@PathVariable("deckId") Long deckId) {
        try {
            service.createJsonCardsByDeckId(deckId);
            return ResponseEntity.ok().body("File Json is created");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
