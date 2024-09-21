package com.desafio.profissional.magic.converter;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserRecordReq;
import com.desafio.profissional.magic.domain.record.UserRecordRes;

public class UserConverter {

    public static User fromReqToUser(UserRecordReq record){
        return User.builder()
                .email(record.email())
                .password(record.password())
                .role(record.role())
                .build();
    }

    public static UserRecordRes toResFromUser(User user){
        return new UserRecordRes(
                user.getId(),
                user.getEmail(),
                !user.getDeck().isEmpty() ? user.getDeck().stream().map(DeckConverter::toResFromDeck).toList() : null
        );
    }
}
