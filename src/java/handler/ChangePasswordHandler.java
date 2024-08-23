// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
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
@WebServlet("/change")
public class ChangePasswordHandler extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String authCode = req.getParameter("authCode");
            String newPassword = req.getParameter("newPassword");
            new AccountContext().updatePassword(username, newPassword);
            new AccountContext().removeAuthCode(username, authCode);
            Account account = new AccountContext().verifyAccount(username, newPassword);
            req.getSession().setAttribute("account", account);
            resp.sendRedirect("http://localhost:8000/PRJ301_war_exploded/timetable/week");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ChangePasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
