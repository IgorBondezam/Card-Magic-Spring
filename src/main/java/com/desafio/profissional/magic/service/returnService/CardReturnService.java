package com.desafio.profissional.magic.service.returnService;

import com.desafio.profissional.magic.converter.CardConverter;
import com.desafio.profissional.magic.domain.record.CardRecordRes;
import com.desafio.profissional.magic.exception.MagicValidatorException;
import com.desafio.profissional.magic.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardReturnService {

    @Autowired
    private CardService service;

    @Cacheable(cacheNames = "cardCache", key = "#root.method.name")
    public List<CardRecordRes> findAll() {
        return service.findAll().stream().map(CardConverter::toResFromCard).toList();
    }

    @Cacheable(cacheNames = "cardCache", key = "#root.method.name + #name")
    public CardRecordRes getCommanderByName(String name) throws MagicValidatorException {
        return CardConverter.toResFromCard(service.getCommanderByName(name));
    }

    @Cacheable(cacheNames = "cardCache", key = "#root.method.name")
    public List<CardRecordRes> getCardsByCommandColor(String colorsFilter) {
        return service.getCardsByCommandColor(colorsFilter).stream().map(CardConverter::toResFromCard).toList();
    }
}
