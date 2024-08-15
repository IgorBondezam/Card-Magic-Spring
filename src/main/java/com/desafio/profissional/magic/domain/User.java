package com.desafio.profissional.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "userplayer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userplayer_sequence")
    @SequenceGenerator(allocationSize = 1, name = "userplayer_sequence", sequenceName ="userplayer_sequence" )
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @OneToOne
    @JoinColumn(name = "deck_id")
    @JsonIgnoreProperties(value = {"user"})
    private Deck deck;
}
