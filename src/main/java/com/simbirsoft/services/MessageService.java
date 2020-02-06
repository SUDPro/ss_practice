package com.simbirsoft.services;

import com.simbirsoft.forms.MessageForm;
import com.simbirsoft.models.Message;

import java.util.List;

public interface MessageService {

    void save(MessageForm form);

    List<Message> findAll();
}