package com.clashbots.user.controller;

import com.clashbots.user.RabbitmqSender;
import com.clashbots.user.entity.User;
import com.clashbots.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user){
        log.info("inside save user method of UserController");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long userId){
        log.info("inside find user by id method of UserController");
        return userService.findUserById(userId);
    }

    private RabbitmqSender rabbitMqSender;
    @Autowired
    public UserController(RabbitmqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @GetMapping("/rabbitmq/contract/{id}")
    public String testRabbitmqContract(@PathVariable("id") Long userId) {
        log.info("inside find user by id with contract method of UserController");
        rabbitMqSender.send(userId);
        return "Message has been sent Successfully: " + userId;
    }
}
