package com.desafio.profissional.magic.service;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserInfoRecordRes;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private DeckService deckService;

    @CacheEvict(cacheNames = "Usuarios", allEntries = true)
    public User save(User user) throws UserException {
        user.setPassword(passwordEncoder(user.getPassword()));
        try{
            if(isCreatedUserAndDeckIsNull(user)) {
                user.getDeck().addAll(deckService.findDeckByUserId(user.getId()));
            }
            return repository.save(user);
        } catch (Exception e){
            throw new UserException("Error to save the user. Try again later");
        }
    }
    @CacheEvict(cacheNames = "Usuarios", allEntries = true)
    public User update(Long id, User newUser) throws UserException {
        User user = findById(id);
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        return save(user);
    }

    @Cacheable(cacheNames = "userCache", key = "#root.method.name + #id")
    public User findById(Long id) throws UserException {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserException("Error to find the user. This player doens't exists!");
        }
        return user.get();
    }

    @CacheEvict(cacheNames = "Usuarios", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Cacheable(cacheNames = "Usuarios", key = "#root.method.name")
    public List<UserInfoRecordRes> findAll() {
        return repository.findAllRes();
    }

    private String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean isCreatedUserAndDeckIsNull(User user) {
        return Objects.nonNull(user.getId()) && Objects.isNull(user.getDeck());
    }
}
