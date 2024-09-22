package com.desafio.profissional.magic.service.returnService;

import com.desafio.profissional.magic.converter.UserConverter;
import com.desafio.profissional.magic.domain.record.UserInfoRecordRes;
import com.desafio.profissional.magic.domain.record.UserRecordRes;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReturnService {

    @Autowired
    private UserService service;

    @Cacheable(cacheNames = "userCache", key = "#root.method.name")
    public List<UserInfoRecordRes> findAll() {
        return service.findAll();
    }

    @Cacheable(cacheNames = "userCache", key = "#root.method.name + #id")
    public UserRecordRes findById(Long id) throws UserException {
        return UserConverter.toResFromUser(service.findById(id));
    }
}
