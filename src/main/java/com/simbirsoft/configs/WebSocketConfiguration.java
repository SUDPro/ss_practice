package com.simbirsoft.configs;

import com.simbirsoft.handlers.AuthHandshakeHandler;
import com.simbirsoft.handlers.HandshakeInterceptorImpl;
import com.simbirsoft.handlers.MessagesWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private MessagesWebSocketHandler messagesWebSocketHandler;

    @Autowired
    private AuthHandshakeHandler authHandshakeHandler;

    @Autowired
    HandshakeInterceptorImpl handshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messagesWebSocketHandler, "/chat")
                .setHandshakeHandler(authHandshakeHandler).setAllowedOrigins("*");
    }
}
