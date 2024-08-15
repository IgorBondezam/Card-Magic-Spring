package com.desafio.profissional.magic.converter;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserRecordReq;
import com.desafio.profissional.magic.domain.record.UserRecordRes;

import java.util.Objects;

public class UserConverter {

    public static User fromReqToUser(UserRecordReq record){
        return User.builder()
                .id(record.id())
                .email(record.email())
                .password(record.password())
                .build();
    }

    public static UserRecordRes toResFromUser(User user){
        return new UserRecordRes(
                user.getId(),
                user.getEmail(),
                Objects.nonNull(user.getDeck()) ? DeckConverter.toResFromDeck(user.getDeck()) : null
        );
    }
}
