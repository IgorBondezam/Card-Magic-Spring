package com.desafio.profissional.magic.domain.record.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardAPI(UUID id,
                      String name,
                      String imageUrl,
                      Double cmc,
                      String manaCost,
                      String text,
                      List<String> supertypes,
                      List<String> types,
                      List<String> subtypes,
                      List<String> colorIdentity,
                      List<String> colors
                      ) {
}
