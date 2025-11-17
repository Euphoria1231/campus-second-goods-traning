package com.campus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.entity.ChatMessage;
import com.campus.vo.SessionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    /**
     * 根据会话ID获取聊天记录，按时间正序排列
     */
    @Select("SELECT * FROM chat_message WHERE session_id = #{sessionId} ORDER BY send_time ASC")
    List<ChatMessage> selectBySessionId(@Param("sessionId") String sessionId);

    /**
     * 查询用户的未读消息数量
     */
    @Select("SELECT COUNT(*) FROM chat_message WHERE receiver_id = #{userId} AND is_read = false")
    Long countUnreadMessages(@Param("userId") Long userId);

    /**
     * 查询与特定用户的未读消息
     */
    @Select("SELECT * FROM chat_message WHERE receiver_id = #{receiverId} AND sender_id = #{senderId} AND is_read = false ORDER BY send_time ASC")
    List<ChatMessage> selectUnreadMessages(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

    /**
     * 标记会话消息为已读
     */
    @Update("UPDATE chat_message SET is_read = true WHERE session_id = #{sessionId} AND receiver_id = #{userId} AND is_read = false")
    void updateMessagesAsRead(@Param("sessionId") String sessionId, @Param("userId") Long userId);

    /**
     * 获取用户会话列表（每个会话的最新消息）
     */
    @Select("SELECT cm1.* FROM chat_message cm1 " +
            "INNER JOIN (SELECT session_id, MAX(send_time) as max_time FROM chat_message " +
            "WHERE #{userId} IN (sender_id, receiver_id) GROUP BY session_id) cm2 " +
            "ON cm1.session_id = cm2.session_id AND cm1.send_time = cm2.max_time " +
            "ORDER BY cm1.send_time DESC")
    List<ChatMessage> selectSessionList(@Param("userId") Long userId);

    /**
     * 获取与特定用户的未读消息数量
     */
    @Select("SELECT COUNT(*) FROM chat_message WHERE session_id = #{sessionId} AND receiver_id = #{userId} AND is_read = false")
    Long countUnreadBySession(@Param("sessionId") String sessionId, @Param("userId") Long userId);

    List<SessionVO> selectSessionsByUserId(Long userId);

    void markAsRead(String sessionId, Long userId);
}