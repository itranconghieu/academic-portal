// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import handler.authen.GoogleAuthentication;
import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.AccountContext;

/**
 *
 * @author End User
 */
public class GoogleConnectionHandler extends GoogleAuthentication {

    @Override
    public void doPost(String userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Account account = (Account) req.getSession().getAttribute("account");
            if (new AccountContext().updateGoogleConnection(account.getId(), userId)) {
                account.setGoogleId(userId);
                resp.sendRedirect("http://localhost:8080/Assignment/account");
            } else {
                req.setAttribute("connectFailed", true);
                resp.getWriter().print("Connect failed");
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GoogleConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GoogleConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
