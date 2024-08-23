// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler.authen;

import entity.Account;
import entity.Chat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.ChatContext;

/**
 *
 * @author End User
 */
public abstract class BasedAuthentication extends HttpServlet{
    protected void processRequest(boolean doPost, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        Account account = (Account) request.getSession().getAttribute("account");
        ResourceBundle lang = (ResourceBundle) request.getSession().getAttribute("lang");
        if (lang == null) {
            lang = ResourceBundle.getBundle("lang", new Locale("en"));
            request.getSession().setAttribute("lang", lang);
        }
        if (account != null) {
            ArrayList<Chat> chats = new ChatContext().getChats(account.getId());
            if (chats.isEmpty()) {
                request.getSession().setAttribute("chats", chats);
            }
            if (doPost) {
                doPost(account ,request, response);
            } else {
                doGet(account ,request, response);
            }
        } else {
            response.sendRedirect("http://localhost:8080/PRJ301/failed.jsp");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(false, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BasedAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BasedAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(true, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BasedAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BasedAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected abstract void doGet(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    protected abstract void doPost(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
