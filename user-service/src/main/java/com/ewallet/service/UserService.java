package com.ewallet.service;

import com.ewallet.dto.CreateUserDTO;
import com.ewallet.entity.User;
import com.ewallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserService {

    private static final String USER_CREATED_TOPIC = "user-created";

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    public User create(CreateUserDTO createUserDTO){
        User user = this.userRepository.save(createUserDTO.toUser());

        try {
            this.kafkaTemplate.send(USER_CREATED_TOPIC, objectMapper.writeValueAsString(user));
        }catch (JacksonException e){
             e.printStackTrace();
        }

        return user;
    }
}
