package com.simbirsoft.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simbirsoft.forms.MessageForm;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagesWebSocketHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String messageAsString = (String) message.getPayload();
        MessageForm messageDto = objectMapper.readValue(messageAsString, MessageForm.class);

        if (messageDto.getText().equals("")) {
            sessions.put(messageDto.getFrom(), session);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (WebSocketSession currentSession : sessions.values()) {
            currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
        }
    }
}
