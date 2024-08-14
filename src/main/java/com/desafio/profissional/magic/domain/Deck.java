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
    @JoinColumn(name = "commander")
    private Card commander;

    @ManyToMany
    @JoinTable(
            name = "deck_card",
            joinColumns = @JoinColumn(name = "decK_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
