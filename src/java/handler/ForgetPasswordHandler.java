// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import helper.Mail;
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
import db.DBContext;

/**
 *
 * @author End User
 */
@WebServlet("/forget")
public class ForgetPasswordHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String authCode = req.getParameter("authoCode");
        if (username != null && authCode != null) {
            try {
                if (new AccountContext().verifyAuthCode(username, authCode)) {
                    req.getRequestDispatcher("change-password.jsp").forward(req, resp);
                } else req.getRequestDispatcher("failed.jsp").forward(req, resp);
            } catch (ParseException ex) {
                Logger.getLogger(ForgetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ForgetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ForgetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else req.getRequestDispatcher("forget.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String mail = req.getParameter("mail");
            String username = new DBContext().getUsername(mail);
            if (username != null) {
                String authCode = new AccountContext().createAuthCode(username);
                String subject = "Reset your account password";
                String content = "<p>Dear " + username+ "<br/> Did your forget your password ?<br/> Then please click on this link to reset your password</p><a style=\"border-radius: 5px; background-color: whitesmoke; padding: 8px;\" href=\"http://localhost:8080/Assignment/forget?user=" + username + "&authoCode=" + authCode + "\">Reset Password</a>";
                new Mail().send(mail, subject, content);
            }
            req.getRequestDispatcher("sent.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ForgetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPasswordHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
