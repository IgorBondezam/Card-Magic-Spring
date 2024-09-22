package com.desafio.profissional.magic.IntTest;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
public class UserReturnServiceIntTest extends ConfigIntTest {

    @Autowired
    private UserService service;

    @Test
    public void testandoIntegrat() throws UserException {
        service.save(User.builder().email("igorbondezam@test.com").password("12345").build());
        User user = service.findById(1L);
        assertEquals(1L, user.getId());
        assertEquals("igorbondezam@test.com", user.getEmail());
        assertNotEquals("12345", user.getPassword());
    }

}
