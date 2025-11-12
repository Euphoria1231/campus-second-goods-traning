package com.campus.handler;


import com.campus.entity.ChatMessage;
import com.campus.service.ChatMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> onlineSessions = new ConcurrentHashMap<>();

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        onlineSessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = getUserIdFromSession(session);
        String payload = message.getPayload();

        // 解析消息
        Map<String, Object> msgMap = objectMapper.readValue(payload, Map.class);
        Long toUserId = Long.valueOf(msgMap.get("toUserId").toString());
        String content = (String) msgMap.get("content");

        if (toUserId != null && content != null) {
            // 保存到数据库
            chatMessageService.saveMessage(Long.valueOf(userId), toUserId, content);

            // 实时推送
            WebSocketSession targetSession = onlineSessions.get(toUserId.toString());
            if (targetSession != null && targetSession.isOpen()) {
                Map<String, Object> response = Map.of(
                        "fromUserId", userId,
                        "content", content,
                        "sendTime", System.currentTimeMillis()
                );
                String responseJson = objectMapper.writeValueAsString(response);
                targetSession.sendMessage(new TextMessage(responseJson));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserIdFromSession(session);
        onlineSessions.remove(userId);
    }

    private String getUserIdFromSession(WebSocketSession session) {
        return (String) session.getAttributes().get("userId");
    }
}