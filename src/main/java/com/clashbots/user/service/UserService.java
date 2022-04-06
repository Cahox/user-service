package com.clashbots.user.service;

import com.clashbots.user.VO.Contract;
import com.clashbots.user.VO.ResponseTemplateVO;
import com.clashbots.user.entity.User;
import com.clashbots.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("inside save user method of UserService");
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        log.info("inside find user by id method of UserService");
        return userRepository.findByUserId(userId);
    }

    public ResponseTemplateVO getUserWithContract(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        Contract contract = restTemplate.getForObject("http://CONTRACT-SERVICE/contracts/" + user.getContractId(), Contract.class);

        vo.setUser(user);
        vo.setContract(contract);
        return vo;
    }
}
