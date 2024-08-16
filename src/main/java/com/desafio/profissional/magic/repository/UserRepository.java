package com.desafio.profissional.magic.repository;

import com.desafio.profissional.magic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("select count(u.id) > 0 from User u where u.id = :id and u.deck is not null  ")
    Boolean hasUserWithoutDeck(@Param("id") Long id);
}
