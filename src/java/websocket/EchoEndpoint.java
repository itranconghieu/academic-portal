// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import db.ChatContext;

/**
 *
 * @author End User
 */

@ServerEndpoint(
        value = "/chat/{chat-id}",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
public class EchoEndpoint {
    private static final Dictionary<String ,Queue<Session>> map = new Hashtable<>();
    
    @OnOpen
    public void onOpen(Session sn, @PathParam("chat-id") String chatId) {
        Queue<Session> queue = map.get(chatId);
        if (queue == null) {
            queue = new ConcurrentLinkedQueue<>();
            queue.add(sn);
            map.put(chatId, queue);
        } else {
            queue.add(sn);
        }
    }
    
    @OnMessage
    public void onMessage(Session sn, Message msg, @PathParam("chat-id") String chatId) throws IOException, EncodeException, ClassNotFoundException, SQLException {
        Date now = new Date();
        msg.setSentTime(new SimpleDateFormat("HH:mm a").format(now));
        new ChatContext().insertMessage(chatId, msg, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        this.broadcast(sn, msg, chatId);
    } 
    
    @OnError
    public void onError(Session sn, Throwable error, @PathParam("chat-id") String chatId) {
        Queue<Session> queue = map.get(chatId);
        if (queue != null) {
            queue.remove(sn);    
        }
    }
    
    @OnClose
    public void onClose(Session sn, CloseReason reason, @PathParam("chat-id") String chatId) {
        Queue<Session> queue = map.get(chatId);
        if (queue != null) {
            queue.remove(sn);    
        }
        
    }
    
    public void broadcast(Session sender, Message msg, String chatId) throws IOException, EncodeException {
        Queue<Session> queue = map.get(chatId);
        if (queue != null) {
            for (Session sn : queue) {
                sn.getBasicRemote().sendObject(msg);
            }
        }
    }
}
