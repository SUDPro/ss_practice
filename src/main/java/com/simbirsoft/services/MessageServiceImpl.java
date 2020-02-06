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
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void save(MessageForm form) {
        Message message = new Message();
        message.setDate(new Date());
        message.setText(form.getText());
        message.setSender(usersRepository.findOneByLogin(form.getFrom()).get());
        messagesRepository.save(message);
    }

    @Override
    public List<Message> findAll() {
        return messagesRepository.findAll();
    }
}