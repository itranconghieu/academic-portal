// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import handler.authen.BasedAuthentication;
import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author End User
 */
public class LogoutHandler extends BasedAuthentication{
    protected void processRequest(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("account");
        req.getSession().removeAttribute("chats");
        resp.sendRedirect("http://localhost:8080/Assignment/sign-in");
    }
    
    @Override
    protected void doGet(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(account, req, resp);
    }

    @Override
    protected void doPost(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(account, req, resp);
    }
    
}
