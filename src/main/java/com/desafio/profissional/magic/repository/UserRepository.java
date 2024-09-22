package com.desafio.profissional.magic.repository;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserInfoRecordRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("select new com.desafio.profissional.magic.domain.record.UserInfoRecordRes(" +
            "u.id," +
            " u.email, " +
            "u.password," +
            "u.role) " +
            "from User u")
    List<UserInfoRecordRes> findAllRes();
}
