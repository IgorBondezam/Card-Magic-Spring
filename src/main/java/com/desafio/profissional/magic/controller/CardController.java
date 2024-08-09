package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.desafio.profissional.magic.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping("/from/api")
    public ResponseEntity<List<CardAPI>> getCards() {
        return ResponseEntity.ok(service.getMagicApiReturn());
    }
}
