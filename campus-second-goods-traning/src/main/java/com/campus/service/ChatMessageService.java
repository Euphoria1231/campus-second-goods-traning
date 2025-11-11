package com.campus.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.entity.ChatMessage;
import com.campus.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageService extends ServiceImpl<ChatMessageMapper, ChatMessage> {

    /**
     * 保存聊天消息
     */
    public boolean saveMessage(Long fromUserId, Long toUserId, String content) {
        String sessionId = ChatMessage.generateSessionId(fromUserId, toUserId);
        ChatMessage message = new ChatMessage();
        message.setSessionId(sessionId);
        message.setSenderId(fromUserId);
        message.setReceiverId(toUserId);
        message.setContent(content);
        return this.save(message);
    }

    /**
     * 获取聊天历史
     */
    public List<ChatMessage> getChatHistory(Long userId1, Long userId2) {
        String sessionId = ChatMessage.generateSessionId(userId1, userId2);
        return this.baseMapper.selectBySessionId(sessionId);
    }

    /**
     * 获取用户总未读消息数
     */
    public Long getUnreadCount(Long userId) {
        return this.baseMapper.countUnreadMessages(userId);
    }

    /**
     * 获取与特定用户的未读消息数
     */
    public Long getUnreadCountWithUser(Long currentUserId, Long targetUserId) {
        String sessionId = ChatMessage.generateSessionId(currentUserId, targetUserId);
        return this.baseMapper.countUnreadBySession(sessionId, currentUserId);
    }

    /**
     * 标记消息为已读
     */
    public void markAsRead(String sessionId, Long userId) {
        this.baseMapper.updateMessagesAsRead(sessionId, userId);
    }

    /**
     * 获取会话列表
     */
    public List<ChatMessage> getSessionList(Long userId) {
        return this.baseMapper.selectSessionList(userId);
    }

    /**
     * 获取未读消息
     */
    public List<ChatMessage> getUnreadMessages(Long receiverId, Long senderId) {
        return this.baseMapper.selectUnreadMessages(receiverId, senderId);
    }
}