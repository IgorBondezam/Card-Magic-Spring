package com.desafio.profissional.magic.domain.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRecordReq(Long id, String email, String password) {
}
