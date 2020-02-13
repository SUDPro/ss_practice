package com.simbirsoft.services;

import com.simbirsoft.forms.MessageForm;
import com.simbirsoft.models.Message;
import com.simbirsoft.repositories.MessagesRepository;
import com.simbirsoft.repositories.RoomsRepository;
import com.simbirsoft.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    public Message save(MessageForm form) {
        Message message = new Message();
        message.setDate(new Date());
        message.setText(form.getText());
        message.setRoom(roomsRepository.findById(form.getRoomId()).get());
        message.setSender(usersRepository.findOneByLogin(form.getFrom()).get());
        return messagesRepository.save(message);
    }

    public List<Message> findAll() {
        return messagesRepository.findAll();
    }

    public List<Message> getMessagesByRoomId(Long id) {
        return messagesRepository.findAllByRoomId(id);
    }
}