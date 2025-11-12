package com.campus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("chat_message")
public class ChatMessage {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("session_id")
    private String sessionId;

    @TableField("sender_id")
    private Long senderId;

    @TableField("receiver_id")
    private Long receiverId;

    @TableField("content")
    private String content;

    @TableField("message_type")
    private Integer messageType = 1; // 1-文本消息

    @TableField("is_read")
    private Boolean isRead = false; // 默认未读

    @TableField("send_time")
    private LocalDateTime sendTime;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    // 非数据库字段，用于前端显示
    @TableField(exist = false)
    private String senderName;

    @TableField(exist = false)
    private String receiverName;


    // 生成会话ID的静态方法
    public static String generateSessionId(Long userId1, Long userId2) {
        return userId1 < userId2 ?
                userId1 + "_" + userId2 :
                userId2 + "_" + userId1;
    }

}