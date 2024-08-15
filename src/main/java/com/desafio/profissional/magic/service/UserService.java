package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.converter.UserConverter;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private DeckService deckService;

    public String save(User user) throws UserException {
        try{
            if(Objects.nonNull(user.getId()) && Objects.isNull(user.getDeck())) {
                user.setDeck(deckService.findDeckByUserId(user.getId()));
            }
            repository.save(user);
        } catch (Exception e){
            throw new UserException("Error to save the user. Try again later");
        }
        return "Your User has been saved.";
    }

    public User findById(Long id) throws UserException {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserException("Error to find the user. This player doens't exists!");
        }
        return user.get();
    }

    public Boolean hasUserWithoutDeck(Long id) {
        return repository.hasUserWithoutDeck(id);
    }
}
