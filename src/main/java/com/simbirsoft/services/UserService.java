package com.simbirsoft.services;

import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.forms.UserForm;
import com.simbirsoft.models.User;
import com.simbirsoft.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder encoder;

    public void save(UserForm form) {
        usersRepository.save(new User(form.getLogin(), encoder.encode(form.getPassword()), UserType.SIMPLE));
    }

    public boolean isUserExist(String login) {
        return usersRepository.existsByLogin(login);
    }
}