package com.desafio.profissional.magic.repository;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserRecordReq;
import com.desafio.profissional.magic.domain.record.UserRecordRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("select new com.desafio.profissional.magic.domain.record.UserRecordReq(" +
            "u.id," +
            " u.email, " +
            "u.password) " +
            "from User u")
    List<UserRecordReq> findAllRes();

    @Query("select count(u.id) > 0 from User u where u.id = :id and u.deck is not null  ")
    Boolean hasUserWithoutDeck(@Param("id") Long id);
}
