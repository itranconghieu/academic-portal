// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import transact.Request;
import entity.Role;
import entity.Semester;
import handler.authen.autho.PermissionAuthorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.DBContext;
import transact.CourseEnroll;

/**
 *
 * @author End User
 */
public class MoveOutClassHandler extends PermissionAuthorization{

    protected void processRequest(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String ic = req.getParameter("course");
            String moveToClass = req.getParameter("class");
            Date now = new Date();
            Semester currentSemester = new DBContext().getSemester(now);
            HashMap<Integer, ArrayList<CourseEnroll>> semesterMap = new DBContext().getCourseEnrolls(account.getId());
            ArrayList<CourseEnroll> courseEnrollList = semesterMap.get(currentSemester.getId());
            req.setAttribute("enrollList", courseEnrollList);
            CourseEnroll selectedEnroll = ic == null ? courseEnrollList.get(courseEnrollList.size() - 1)
                : courseEnrollList.get(Integer.parseInt(ic));
            req.setAttribute("selectedEnroll", selectedEnroll);
            ArrayList<String> classList = new DBContext().getClass(currentSemester, selectedEnroll.getCourse());
            req.setAttribute("classList", classList);
            if (moveToClass != null) {
                entity.Course enrolledCourse = selectedEnroll.getCourse();
                ArrayList<transact.Session> sessionList = new DBContext().getSessions(moveToClass, enrolledCourse.getId());
                req.setAttribute("sessionList", sessionList);
                req.setAttribute("classAfter", moveToClass);
            }
            ArrayList<Request> requestList = new DBContext().getRequests(account.getId());
            req.setAttribute("requestList", requestList);
            req.getRequestDispatcher("move-out.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MoveOutClassHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MoveOutClassHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MoveOutClassHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(account, role, req, resp);
    }

    @Override
    protected void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(account, role, req, resp);
    }
    
}
