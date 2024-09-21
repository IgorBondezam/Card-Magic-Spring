package com.desafio.profissional.magic.repository;

import com.desafio.profissional.magic.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findDeckByUserId(Long id);

    @Query("select count(d.id) > 0 from Deck d join d.user u where d.id = :deckId and u.id = :userId")
    Boolean isDeckFromUser(@Param("deckId") Long deckId, @Param("userId") Long userId );
}
