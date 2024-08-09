package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.domain.record.API.CardAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CardService {

    public List<CardAPI> getMagicApiReturn(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String uri = "https://api.scryfall.com/cards/search?q=colors=GB";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity(uri,Object.class);
        return Arrays.asList(objectMapper.convertValue(((LinkedHashMap<String, Object>)response.getBody()).get("data"), CardAPI[].class));

    }
}
