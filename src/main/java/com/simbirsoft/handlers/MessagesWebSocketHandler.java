package com.simbirsoft.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simbirsoft.forms.MessageForm;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessagesWebSocketHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessionList = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageAsString = (String) message.getPayload();
        MessageForm messageForm = objectMapper.readValue(messageAsString, MessageForm.class);

        for (WebSocketSession currentSession : sessionList) {
            currentSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageForm)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        super.afterConnectionClosed(session, status);
    }
}
