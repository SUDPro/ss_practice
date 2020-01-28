package com.simbirsoft.services;

import com.simbirsoft.models.User;
import com.simbirsoft.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User getUserByLogin(String login) {
        return repository.getUserByLogin(login);
    }
}
