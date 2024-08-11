package com.desafio.profissional.magic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deck {

    @Id
    private Long id;

    @ManyToOne
    private Card commander;

    @ManyToMany
    private List<Card> cards;

    @OneToOne
    private User user;
}
