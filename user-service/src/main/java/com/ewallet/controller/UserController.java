package com.ewallet.controller;

import com.ewallet.dto.CreateUserDTO;
import com.ewallet.entity.User;
import com.ewallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public User createUser(@RequestBody CreateUserDTO createUserDTO) {
        return this.userService.create(createUserDTO);
    }
}
