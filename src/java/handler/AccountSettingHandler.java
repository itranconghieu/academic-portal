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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.AccountContext;

/**
 *
 * @author End User
 */
public class AccountSettingHandler extends BasedAuthentication {

    @Override
    protected void doGet(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("remove-google-account-connection") != null) {
            try {
                new AccountContext().updateGoogleConnection(account.getId(), null);
                account.setGoogleId(null);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AccountSettingHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AccountSettingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        if (currentPassword.equals(account.getPassword())) {
            try {
                new AccountContext().updatePassword(account.getId(), newPassword);
                account.setPassword(newPassword);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AccountSettingHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AccountSettingHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.setAttribute("changed", true);
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        } else {
            req.setAttribute("uncorrected", true);
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }
    
}
