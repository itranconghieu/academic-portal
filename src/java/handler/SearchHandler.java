// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import entity.Role;
import entity.Student;
import handler.authen.autho.PermissionAuthorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.DBContext;

/**
 *
 * @author End User
 */
public class SearchHandler extends PermissionAuthorization {

    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pattern = req.getParameter("pattern");
            ArrayList<Student> studentList = new DBContext().getStudents(pattern);
            resp.setContentType("text/html");
            if (studentList.isEmpty()) {
                resp.getWriter().print("<div class=\"text-center fw-semibold\">No result</div>");
            }
            for (Student student : studentList) {
                resp.getWriter().print("<button type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#" + student.getId() +"\" aria-expanded=\"false\" aria-controls=\"" + student.getId() +"\" class=\"dropdown-item\">" 
                        + "<span style=\"font-weight: 500\">" + student + " | " + student.getCode() + "</span><br/>"
                        + "<span>" + student.getMajor().getName() + "</span>"
                        + "</button>"
                        + "<div class=\"collapse\" style=\"font-size: small;\" id=\"" + student.getId() +"\">"
                        + "<a href=\"http://localhost:8080/Assignment/transcript?student=" + student.getId() +"\" class=\"dropdown-item\"><i class=\"fi fi-rs-arrow-up-right\" style=\"font-size: x-small;\"></i> Transcript</a>"
                        + "<a href=\"http://localhost:8080/Assignment/attendance-reports?student=" + student.getId() +"\" class=\"dropdown-item\"><i class=\"fi fi-rs-arrow-up-right\" style=\"font-size: x-small;\"></i> Attendance report</a>"
                        + "</div>");
            }
        } catch (ParseException ex) {
            Logger.getLogger(SearchHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    protected void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
