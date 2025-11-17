package com.campus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chat_message")
public class ChatMessage {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 商品ID
    private Long productId;

    // 发送者ID
    private Long senderId;

    // 接收者ID
    private Long receiverId;

    // 消息内容
    private String content;

    // 消息类型: TEXT, IMAGE, SYSTEM
    private String messageType;

    // 是否已读
    private Boolean isRead;

    // 发送时间
    private LocalDateTime sendTime;

    // 会话ID
    private String sessionId;

    // 逻辑删除字段（如果数据库表中有此字段，可以取消注释下面的注解）
    // @TableLogic
    // private Integer deleted;

    public static String generateSessionId(Long fromUserId, Long toUserId) {
        if (fromUserId == null || toUserId == null) {
            throw new IllegalArgumentException("用户ID不能为null");
        }

        // 保证小的ID在前，大的ID在后
        Long smaller = Math.min(fromUserId, toUserId);
        Long larger = Math.max(fromUserId, toUserId);

        return smaller + "_" + larger;
    }

}
