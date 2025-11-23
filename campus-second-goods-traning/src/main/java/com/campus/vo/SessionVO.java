package com.campus.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 会话列表VO，用于展示聊天会话信息
 */
@Data
public class SessionVO {
    
    // 会话ID
    private String sessionId;
    
    // 对方用户ID
    private Long otherUserId;
    
    // 对方用户名
    private String otherUserName;
    
    // 对方头像
    private String otherUserAvatar;
    
    // 商品ID
    private Long productId;
    
    // 商品标题
    private String productTitle;
    
    // 商品图片
    private String productImage;
    
    // 最后一条消息内容
    private String lastMessageContent;
    
    // 最后一条消息类型
    private String lastMessageType;
    
    // 最后一条消息时间
    private LocalDateTime lastMessageTime;
    
    // 未读消息数量
    private Long unreadCount;
    
    // 最后一条消息的发送者ID
    private Long lastSenderId;
}
