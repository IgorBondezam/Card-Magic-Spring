package com.desafio.profissional.magic.repository;

import com.desafio.profissional.magic.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {

    Deck findDeckByUserId(Long id);
}
