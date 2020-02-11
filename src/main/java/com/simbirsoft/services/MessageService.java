package com.simbirsoft.services;

import com.simbirsoft.forms.MessageForm;
import com.simbirsoft.models.Message;
import com.simbirsoft.repositories.MessagesRepository;
import com.simbirsoft.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    UsersRepository usersRepository;

    public void save(MessageForm form) {
        Message message = new Message();
        message.setDate(new Date());
        message.setText(form.getText());
        message.setSender(usersRepository.findOneByLogin(form.getFrom()).get());
        messagesRepository.save(message);
    }

    public List<Message> findAll() {
        return messagesRepository.findAll();
    }

    public List<Message> getMessagesByRoomId(Long id) {
        return messagesRepository.findAllByRoomId(id);
    }
}