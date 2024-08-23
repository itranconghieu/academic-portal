// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler.authen.autho;

import handler.authen.BasedAuthentication;
import entity.Account;
import entity.Role;
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
public abstract class PermissionAuthorization extends BasedAuthentication{
    protected void processRequest(boolean doPost, Account account, HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException,  IOException, ServletException, ClassNotFoundException {
        Role role = new AccountContext().getAccountRole(account.getId());
        account.setRole(role);
        String targetURL = request.getServletPath();
        if (role.have(targetURL)) {
            if (doPost) {
                this.doPost(account, role, request, response);
                return;
            } else {
                this.doGet(account, role, request, response);
                return;
            }
        }
        response.sendRedirect("http://localhost:8000/PRJ301_war_exploded/failed.jsp");
    }
    
    @Override
    protected void doGet(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(false, account, req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(PermissionAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PermissionAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PermissionAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(true, account, req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(PermissionAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PermissionAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PermissionAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected abstract void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    protected abstract void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
