// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import entity.Role;
import handler.authen.autho.PermissionAuthorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.DBContext;

/**
 *
 * @author End User
 */
public class SendRequestHandler extends PermissionAuthorization{

    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String enrollId = req.getParameter("enroll");
            String toClass = req.getParameter("classAfter");
            String reason = req.getParameter("reason");
            new DBContext().insertRequest(Integer.parseInt(enrollId), toClass, reason);
            resp.sendRedirect("http://localhost:8080/Assignment/move-out-class");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SendRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SendRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
