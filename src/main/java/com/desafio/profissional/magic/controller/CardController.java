package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.domain.enums.ColorCard;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
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
    public ResponseEntity<CardAPI> getCommanderByName(@PathVariable("name") String name) throws IOException, MagicValidatorException {
        return ResponseEntity.ok(service.getCommanderByName(name));
    }

    @GetMapping("color")
    public ResponseEntity<List<CardAPI>> populetedByCommanderColor(@RequestParam("colors") List<ColorCard> colors) throws IOException {
        return ResponseEntity.ok(service.populetedByCommanderColor(colors.stream().map(ColorCard::getAcronym).collect(Collectors.joining(""))));
    }
}
