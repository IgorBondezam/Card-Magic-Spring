package com.desafio.profissional.magic.domain.record.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CardAPI(UUID id,
                      String name,
                      LocalDate released_at,
                      Double cmc,
                      String type_line,
                      String mana_cost,
                      String oracle_text,
                      List<String> color_identity,
                      List<String> colors,
                      List<String> produced_mana) {
}
