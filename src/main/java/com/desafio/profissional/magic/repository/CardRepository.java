package com.desafio.profissional.magic.repository;

import com.desafio.profissional.magic.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    @Query("select c " +
            " from Deck d " +
            " join d.cards c " +
            " where d.id = :deckId")
    List<Card> findCardsByDeckId(@Param("deckId") Long deckId);
    @Query("select d.commander " +
            " from Deck d" +
            " where d.id = :deckId")
    Card findCommanderByDeckId(@Param("deckId") Long deckId);

    @Query("select c " +
            " from Card c " +
            " where c.superTypes ilike ('%legendary%') and c.name ilike CONCAT('%',UPPER(:name),'%')")
    List<Card> findCommanderByName(@Param("name") String name);

    @Query("select c " +
            " from Card c " +
            " where c.superTypes not ilike ('%legendary%') and c.colors ilike CONCAT('%',UPPER(:color),'%')")
    List<Card> findNotCommanderByColor(@Param("color") String color);

    @Query("select c from Card c where c.id in (:ids)")
    List<Card> findCardsInId(@Param("ids") List<UUID> ids);

}
