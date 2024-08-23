// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import entity.Chat;
import java.sql.*;
import java.util.ArrayList;
import websocket.Message;

/**
 *
 * @author End User
 */
public class ChatContext extends Open{
    public ChatContext() throws ClassNotFoundException, SQLException {
    
    }

    public ArrayList<Chat> getChats(String us) throws SQLException, ClassNotFoundException {
        String sql = "SELECT [ChatEnroll].[chatId], groupId, courseId, readonly\n"
                + "      ,[username]\n"
                + "      ,[administrator]\n"
                + "  FROM [dbo].[ChatEnroll]\n"
                + "  JOIN Chats\n"
                + "  ON ChatEnroll.chatId = Chats.chatId\n"
                + "WHERE username = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, us);
        ArrayList<Chat> cs = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Chat c = new Chat();
            c.setId(rs.getInt("chatId"));
            c.setGroupId(rs.getString("groupId"));
            c.setCourse(new DBContext().getCourse(rs.getString("courseId")));
            c.setReadOnly(rs.getBoolean("readonly"));
            cs.add(c);
        }
        ps.close();
        return cs;

    }
    
    public ArrayList<Message> loadMessages(String chatId) throws SQLException {
        String sql = "SELECT [messageId]\n"
                + "      ,[chatId]\n"
                + "      ,[sender]\n"
                + "      ,[content]\n"
                + "      ,[type]\n"
                + "      ,[sentTime]\n"
                + "  FROM [dbo].[Messages]\n"
                + "WHERE chatId = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(chatId));
        ArrayList<Message> messages = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Message msg = new Message();
            msg.setSender(rs.getString("sender"));
            msg.setContent(rs.getString("content"));
            msg.setSentTime(rs.getString("sentTime"));
            messages.add(msg);
        }
        return messages;
    }

    public void insertMessage(String chatId, Message msg, String sendTime) throws SQLException {
        String sql = "INSERT INTO [dbo].[Messages]\n"
                + "           ([chatId]\n"
                + "           ,[sender]\n"
                + "           ,[content]\n"
                + "           ,[sentTime])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(chatId));
        ps.setString(2, msg.getSender());
        ps.setString(3, msg.getContent());
        ps.setString(4, sendTime);
        ps.executeUpdate();
    }
}
