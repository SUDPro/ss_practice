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
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void save(UserForm form) {
        usersRepository.save(new User(form.getLogin(), encoder.encode(form.getPassword()), form.getName(), UserType.SIMPLE));
    }

    public boolean isUserExist(String login) {
        return usersRepository.existsByLogin(login);
    }

    public User getUserByLogin(String login){
        return usersRepository.findOneByLogin(login).get();
    }

    public User getUserByRoomId(Long userId, Long roomId){
        return usersRepository.getUserByRoomId(userId, roomId);
    }

    public boolean isUserExistInChat(Long userId, Long roomId){
        return usersRepository.getUserByRoomId(userId, roomId) != null;
    }

    public void save(User user) {
        usersRepository.save(user);
    }
}