package com.desafio.profissional.magic.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

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

    private String color_identity;
    private String colors;
    private String produced_mana;

    public List<String> getColor_identity() {
        if(Objects.isNull(color_identity)) {
            return new ArrayList<>();
        }
        return Arrays.asList(color_identity.split(","));
    }

    public List<String> getColors() {
        if(Objects.isNull(colors)) {
            return new ArrayList<>();
        }
        return Arrays.asList(colors.split(","));
    }

    public List<String> getProduced_mana() {
        if(Objects.isNull(produced_mana)) {
            return new ArrayList<>();
        }
        return Arrays.asList(produced_mana.split(","));
    }

    public void setColor_identity(List<String> color_identity) {
        if(Objects.isNull(color_identity)) {
            return;
        }
        this.color_identity = String.join(",", color_identity);
    }

    public void setColors(List<String> colors) {
        if(Objects.isNull(colors)) {
            return;
        }
        this.colors = String.join(",", colors);
    }

    public void setProduced_mana(List<String> produced_mana) {
        if(Objects.isNull(produced_mana)) {
            return;
        }
        this.produced_mana = String.join(",", produced_mana);
    }
}
