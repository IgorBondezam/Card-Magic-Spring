package com.desafio.profissional.magic.domain.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserLogin(@NotBlank @Email String email, String password) implements Serializable {
}
