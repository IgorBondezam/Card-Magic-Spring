package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.domain.enums.ColorCard;
import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping("commander/{name}")
    public ResponseEntity<CardAPI> getCards(@PathVariable("name") String name) throws IOException, MagicValidatorException {
        return ResponseEntity.ok(service.getCommanderByName(name));
    }

    @GetMapping("color")
    public ResponseEntity<List<CardAPI>> getCardsByCommanderColor(@RequestParam("colors") List<ColorCard> colors) throws IOException {
        return ResponseEntity.ok(service.getCardByCommanderColor(colors.stream().map(ColorCard::getAcronym).collect(Collectors.joining(""))));
    }
}
