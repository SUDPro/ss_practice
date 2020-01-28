package com.simbirsoft.services;

import com.simbirsoft.forms.MessageForm;
import com.simbirsoft.models.Message;
import com.simbirsoft.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    UserService userService;

    @Autowired
    MessageRepository repository;

    public void saveMessage(MessageForm form) {
        Message message = new Message();
        message.setSender(userService.getUserByLogin(form.getFrom()));
        message.setDate(new Date());
        message.setText(form.getText());
        repository.save(message);
    }

}
