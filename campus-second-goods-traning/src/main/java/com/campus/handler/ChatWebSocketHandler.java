package com.campus.handler;

import com.campus.entity.ChatMessage;
import com.campus.service.ChatMessageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
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
        Map<String, Object> msgMap = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
        });
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
                        "sendTime", System.currentTimeMillis());
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

    /**
     * 向指定用户发送WebSocket消息
     * 
     * @param chatMessage 聊天消息对象
     * @return 是否成功发送（用户在线且连接正常）
     */
    public boolean sendMessageToUser(ChatMessage chatMessage) {
        if (chatMessage == null || chatMessage.getReceiverId() == null) {
            log.warn("消息或接收者ID为空，无法发送");
            return false;
        }

        String receiverId = chatMessage.getReceiverId().toString();
        WebSocketSession targetSession = onlineSessions.get(receiverId);

        if (targetSession != null && targetSession.isOpen()) {
            try {
                // 构建完整的消息响应对象
                Map<String, Object> response = new HashMap<>();
                response.put("id", chatMessage.getId());
                response.put("senderId", chatMessage.getSenderId());
                response.put("receiverId", chatMessage.getReceiverId());
                response.put("content", chatMessage.getContent());
                response.put("messageType",
                        chatMessage.getMessageType() != null ? chatMessage.getMessageType() : "TEXT");
                response.put("isRead", chatMessage.getIsRead() != null ? chatMessage.getIsRead() : false);
                response.put("sendTime",
                        chatMessage.getSendTime() != null ? chatMessage.getSendTime().toString() : null);
                response.put("sessionId", chatMessage.getSessionId());
                if (chatMessage.getProductId() != null) {
                    response.put("productId", chatMessage.getProductId());
                }

                String responseJson = objectMapper.writeValueAsString(response);
                targetSession.sendMessage(new TextMessage(responseJson));
                log.info("成功通过WebSocket发送消息给用户: {}", receiverId);
                return true;
            } catch (Exception e) {
                log.error("发送WebSocket消息失败，用户ID: {}", receiverId, e);
                return false;
            }
        } else {
            log.debug("用户 {} 不在线或连接已关闭，消息已保存到数据库", receiverId);
            return false;
        }
    }

    /**
     * 检查用户是否在线
     * 
     * @param userId 用户ID
     * @return 是否在线
     */
    public boolean isUserOnline(Long userId) {
        if (userId == null) {
            return false;
        }
        WebSocketSession session = onlineSessions.get(userId.toString());
        return session != null && session.isOpen();
    }
}