package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.converter.UserConverter;
import com.desafio.profissional.magic.domain.record.UserRecordReq;
import com.desafio.profissional.magic.domain.record.UserRecordRes;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRecordReq record) throws UserException {
        return ResponseEntity.ok(service.save(UserConverter.fromReqToUser(record)));
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserRecordReq record) throws UserException {
        return ResponseEntity.ok(service.save(UserConverter.fromReqToUser(record)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRecordRes> findById(@PathVariable("id") Long id) throws UserException {
        return ResponseEntity.ok(UserConverter.toResFromUser(service.findById(id)));
    }



}
