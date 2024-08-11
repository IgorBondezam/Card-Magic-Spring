package com.desafio.profissional.magic.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    @Id
    private UUID id;
    private String name;
    private LocalDate released_at;
    private Double cmc;
    private String type_line;
    private String mana_cost;
    private String oracle_text;

    @ElementCollection
    private List<String> color_identity = new ArrayList();
    @ElementCollection
    private List<String> colors = new ArrayList();
    @ElementCollection
    private List<String> produced_mana = new ArrayList();


}
