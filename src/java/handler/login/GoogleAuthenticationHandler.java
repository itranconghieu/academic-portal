// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler.login;

import entity.Account;
import handler.authen.GoogleAuthentication;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.AccountContext;

/**
 *
 * @author End User
 */
public class GoogleAuthenticationHandler extends GoogleAuthentication {

    @Override
    public void doPost(String userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Account account = new AccountContext().verifyGoogleAccountConnection(userId);
            if (account.getId() != null) {
                req.getSession().setAttribute("account", account);
                resp.sendRedirect("http://localhost:8000/PRJ301_war_exploded/timetable/week");
            } else {
                req.setAttribute("noMatch", true);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GoogleAuthenticationHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GoogleAuthenticationHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(GoogleAuthenticationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
