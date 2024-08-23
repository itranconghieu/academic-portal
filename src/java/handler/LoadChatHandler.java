// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import handler.authen.BasedAuthentication;
import entity.Account;
import entity.Chat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.ChatContext;
import websocket.Message;

/**
 *
 * @author End User
 */
public class LoadChatHandler extends BasedAuthentication {
    ArrayList<Chat> chats;
    protected void processRequest(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClassNotFoundException, SQLException  {
        chats = (ArrayList) req.getSession().getAttribute("chats");
        String chatId = req.getParameter("id");
        if (chatId != null) {
            if (verifyChat(chatId)) {
                for (Chat chat : chats) {
                    if (chat.getId() == Integer.parseInt(chatId));
                    req.setAttribute("chat", chat);
                }
                ArrayList<Message> messages = new ChatContext().loadMessages(chatId);
                req.setAttribute("messages", messages);
                req.getRequestDispatcher("chat.jsp").forward(req, resp);
            } else {
                resp.getWriter().print("Load failed");
            }
        } else {
            req.getRequestDispatcher("chat.jsp").forward(req, resp);
        }
        
        
    }
    
    @Override
    protected void doGet(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean verifyChat(String id) {
        for (Chat c : chats) {
            if (c.getId() == Integer.parseInt(id)) {
                return true;
            }
        }
        return false;
    }
}
