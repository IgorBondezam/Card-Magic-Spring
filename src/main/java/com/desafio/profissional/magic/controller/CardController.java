package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.domain.enums.ColorCard;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.domain.record.CardRecordRes;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping("commander/{name}")
    public ResponseEntity getCommanderByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(service.getCommanderByName(name));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("color")
    public ResponseEntity populetedByCommanderColor(@RequestParam("colors") List<ColorCard> colors) {
        try {
            return ResponseEntity.ok(service.populetedByCommanderColor(colors.stream().map(ColorCard::getAcronym).collect(Collectors.joining(""))));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity findAllCards() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/json/{userId}")
    public ResponseEntity createJsonCardsByUserId(@PathVariable("userId") Long userId) {
        try {
            service.createJsonCardsByUserId(userId);
            return ResponseEntity.ok().body("File Json is created");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
