package com.simbirsoft.services;

import com.simbirsoft.enumTypes.UserType;
import com.simbirsoft.forms.UserForm;
import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import com.simbirsoft.repositories.BanInfosRepository;
import com.simbirsoft.repositories.RoomsRepository;
import com.simbirsoft.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BanInfosRepository banInfosRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void save(UserForm form) {
       User user =  usersRepository.save(new User(form.getLogin(), encoder.encode(form.getPassword()), form.getName(), UserType.SIMPLE));
       Room room = roomsRepository.findByName("room1");
       banInfosRepository.save(new BanInfo(room, user));
    }

    public boolean isUserExist(String login) {
        return usersRepository.existsByLogin(login);
    }

    public User getUserByLogin(String login) {
        return usersRepository.findOneByLogin(login).get();
    }

    public boolean isUserExistInChat(Long userId, Long roomId) {
        return usersRepository.getUserByIdAndRoomId(userId, roomId) != null;
    }

    public void save(User user) {
        usersRepository.save(user);
    }
}