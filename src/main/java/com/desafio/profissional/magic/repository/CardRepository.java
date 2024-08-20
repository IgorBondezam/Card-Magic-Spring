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
            " from Deck d" +
            " join d.user u" +
            " join d.cards c " +
            " where u.id = :userId")
    List<Card> findCardByUserId(@Param("userId") Long userId);
    @Query("select d.commander " +
            " from Deck d" +
            " join d.user u" +
            " join d.cards c " +
            " where u.id = :userId")
    Card findCommanderByUserId(@Param("userId") Long userId);

}
