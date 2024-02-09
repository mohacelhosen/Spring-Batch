package com.store.batch.user.service;

import com.store.batch.user.model.User;
import com.store.batch.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){

        if (user.getId() !=null){
            Optional<User> userOptional = userRepository.findById(user.getId());
            if (userOptional.isPresent()){
                throw new IllegalArgumentException("This user already registered:: "+user.getId());
            }
            return userRepository.save(user);
        }
        return userRepository.save(user);
    }
}
