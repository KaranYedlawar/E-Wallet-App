package com.ewallet.service;

import com.ewallet.dto.CreateUserDTO;
import com.ewallet.entity.User;
import com.ewallet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserService {

    private static final String USER_CREATED_TOPIC = "user-created";
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    public User create(CreateUserDTO createUserDTO){
        User user = this.userRepository.save(createUserDTO.toUser());

        try {
            String data = objectMapper.writeValueAsString(user);

            logger.info("Data to be published - {}", data);

            this.kafkaTemplate.send(USER_CREATED_TOPIC, data);
        }catch (JacksonException e){
             e.printStackTrace();
        }

        return user;
    }
}
