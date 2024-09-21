package com.desafio.profissional.magic.domain.record;

import java.util.List;

public record UserRecordRes(Long id, String email, List<DeckRecordRes> deck) {
}
