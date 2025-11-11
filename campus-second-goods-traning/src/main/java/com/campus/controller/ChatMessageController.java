package com.campus.controller;

import com.campus.entity.ChatMessage;
import com.campus.service.ChatMessageService;
import com.campus.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

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
}