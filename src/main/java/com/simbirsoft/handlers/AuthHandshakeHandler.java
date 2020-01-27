package com.simbirsoft.handlers;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

@Component
public class AuthHandshakeHandler implements HandshakeHandler {

    private DefaultHandshakeHandler handshakeHandler = new DefaultHandshakeHandler();

    @Override
    public boolean doHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler,
                               Map<String, Object> map) throws HandshakeFailureException {
        return handshakeHandler.doHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
    }

}
