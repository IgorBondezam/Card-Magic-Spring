package com.desafio.profissional.magic.domain.record;

import com.desafio.profissional.magic.domain.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRecordReq(String email, String password, UserRole role) implements Serializable {
}
