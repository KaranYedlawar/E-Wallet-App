package com.ewallet.dto;

import com.ewallet.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {

    private String name;

    private String email;

    private Integer age;

    private String phone;

    public User toUser(){

        User user = new User();
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        user.setAge(this.getAge());
        user.setPhone(this.getPhone());

        return user;
    }

}
