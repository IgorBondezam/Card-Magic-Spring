package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.converter.UserConverter;
import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserRecordReq;
import com.desafio.profissional.magic.domain.record.UserRecordRes;
import com.desafio.profissional.magic.exception.UserException;
import com.desafio.profissional.magic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserRecordReq record) {
        try {
            User saved = service.save(UserConverter.fromReqToUser(record));
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/user")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body("Your User has been saved.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserRecordReq record) {
        try {
            service.update(id, UserConverter.fromReqToUser(record));
            return ResponseEntity.ok("Your User has been updated.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(UserConverter.toResFromUser(service.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity findByAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
