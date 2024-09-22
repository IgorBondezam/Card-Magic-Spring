package com.desafio.profissional.magic.service.returnService;

import com.desafio.profissional.magic.converter.DeckConverter;
import com.desafio.profissional.magic.domain.record.DeckRecordRes;
import com.desafio.profissional.magic.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckReturnService {

    @Autowired
    private DeckService service;

    @Cacheable(cacheNames = "deckCache", key = "#root.method.name")
    public List<DeckRecordRes> findAll() {
        return service.findAll().stream().map(DeckConverter::toResFromDeck).toList();
    }

    @Cacheable(cacheNames = "deckCache", key = "#root.method.name + #userId")
    public List<DeckRecordRes> findDeckByUserId(Long userId) {
        return service.findDeckByUserId(userId).stream().map(DeckConverter::toResFromDeck).toList();
    }
}
