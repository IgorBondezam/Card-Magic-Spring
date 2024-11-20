package com.desafio.profissional.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deck_sequence")
    @SequenceGenerator(allocationSize = 1, name = "deck_sequence", sequenceName ="deck_sequence" )
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "commander")
    private Card commander;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "deck_card",
            joinColumns = @JoinColumn(name = "decK_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"deck"})
    private User user;
}
