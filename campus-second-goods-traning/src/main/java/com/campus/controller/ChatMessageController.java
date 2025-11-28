package com.campus.controller;

import com.campus.entity.ChatMessage;
import com.campus.handler.ChatWebSocketHandler;
import com.campus.service.ChatMessageService;
import com.campus.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    /**
     * 获取聊天历史
     */
    @GetMapping("/history")
    public Result getChatHistory(@RequestParam Long userId1, @RequestParam Long userId2) {
        List<ChatMessage> history = chatMessageService.getChatHistory(userId1, userId2);
        return Result.success(history);
    }

    /**
     * 获取会话列表
     */
    @GetMapping("/sessions")
    public Result getSessionList(@RequestParam Long userId) {
        List<ChatMessage> sessions = chatMessageService.getSessionList(userId);
        return Result.success(sessions);
    }

    /**
     * 获取未读消息数
     */
    @GetMapping("/unread")
    public Result getUnreadCount(@RequestParam Long userId) {
        Long count = chatMessageService.getUnreadCount(userId);
        return Result.success(count);
    }

    /**
     * 获取与特定用户的未读消息数
     */
    @GetMapping("/unread/with")
    public Result getUnreadCountWithUser(@RequestParam Long currentUserId,
            @RequestParam Long targetUserId) {
        Long count = chatMessageService.getUnreadCountWithUser(currentUserId, targetUserId);
        return Result.success(count);
    }

    /**
     * 标记消息为已读
     */
    @PostMapping("/read")
    public Result markAsRead(@RequestParam String sessionId, @RequestParam Long userId) {
        chatMessageService.markAsRead(sessionId, userId);
        return Result.success("标记已读成功");
    }

    /**
     * 发送消息
     */
    @PostMapping("/send")
    public Result sendMessage(@RequestParam Long fromUserId,
            @RequestParam Long toUserId,
            @RequestParam String content,
            @RequestParam Long productId) {
        try {
            // 验证productId不能为空
            if (productId == null) {
                log.error("productId不能为空，fromUserId: {}, toUserId: {}", fromUserId, toUserId);
                return Result.fail("商品ID不能为空");
            }

            // 生成会话ID
            String sessionId = ChatMessage.generateSessionId(fromUserId, toUserId);

            // 创建消息对象
            ChatMessage message = new ChatMessage();
            message.setSessionId(sessionId);
            message.setSenderId(fromUserId);
            message.setReceiverId(toUserId);
            message.setContent(content);
            message.setMessageType("TEXT");
            message.setIsRead(false);
            message.setSendTime(LocalDateTime.now());
            message.setProductId(productId);

            // 保存到数据库
            boolean saved = chatMessageService.save(message);
            if (!saved) {
                log.error("保存消息失败，fromUserId: {}, toUserId: {}", fromUserId, toUserId);
                return Result.fail("发送消息失败");
            }

            // 通过WebSocket实时推送消息
            boolean pushed = chatWebSocketHandler.sendMessageToUser(message);
            if (pushed) {
                log.info("消息已发送并推送给用户，fromUserId: {}, toUserId: {}", fromUserId, toUserId);
            } else {
                log.info("消息已保存，但目标用户不在线，fromUserId: {}, toUserId: {}", fromUserId, toUserId);
            }

            return Result.success(message);
        } catch (Exception e) {
            log.error("发送消息异常，fromUserId: {}, toUserId: {}", fromUserId, toUserId, e);
            return Result.fail("发送消息失败: " + e.getMessage());
        }
    }
}