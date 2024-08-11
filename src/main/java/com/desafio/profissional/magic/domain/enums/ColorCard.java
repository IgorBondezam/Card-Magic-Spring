package com.desafio.profissional.magic.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorCard {

    WHITE("White", "W"),
    BLUE("Blue", "U"),
    BLACK("Black", "B"),
    RED("Red", "R"),
    GREEN("Green", "G");

    private String descrition;
    private String acronym;
}
