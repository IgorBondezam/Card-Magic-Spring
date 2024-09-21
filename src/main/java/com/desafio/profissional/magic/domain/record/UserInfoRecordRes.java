package com.desafio.profissional.magic.domain.record;

import com.desafio.profissional.magic.domain.enums.UserRole;

import java.io.Serializable;

public record UserInfoRecordRes(Long id, String email, String password, UserRole role) implements Serializable {
}
