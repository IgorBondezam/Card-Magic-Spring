package com.desafio.profissional.magic.domain.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLogin(@NotBlank @Email String email, String password) {
}
