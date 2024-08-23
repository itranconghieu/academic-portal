// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import entity.Semester;
import entity.Student;
import entity.Role;
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
import transact.Attend;
import transact.CourseEnroll;

/**
 *
 * @author End User
 */
public class AttendanceReportHandler extends PermissionAuthorization{
    private Student student;

    protected void processRequest(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClassNotFoundException, SQLException, ParseException {
        Date now = new Date();
        req.setAttribute("now", now);
        if (role.have("/search")) {
            String studentId = req.getParameter("student");
            student = new DBContext().getStudent(studentId);
            if ((studentId != null || studentId != "") && student.getId() != null) {
                new CookieHandler().addRecents(req, resp, studentId);
            } else resp.getWriter().print("Not found");
        } else {
            try {
                student = (Student) account;
            } catch (Exception e) {
                resp.getWriter().print("Not yet");
            }
        }
        String is = req.getParameter("semester");
        String ic = req.getParameter("course");
        ArrayList<Semester> semesterList = new DBContext().getSemesters(student.getId());
        req.setAttribute("semesterList", semesterList);
        Semester selectedSemester = is == null ? semesterList.get(semesterList.size() - 1)
                : semesterList.get(Integer.parseInt(is));
        req.setAttribute("selectedSemester", selectedSemester);
        HashMap<Integer, ArrayList<CourseEnroll>> semesterMap = new DBContext().getCourseEnrolls(student.getId());
        ArrayList<CourseEnroll> courseEnrollList = semesterMap.get(selectedSemester.getId());
        req.setAttribute("enrollList", courseEnrollList);
        CourseEnroll selectedEnroll = ic == null ? courseEnrollList.get(courseEnrollList.size() - 1)
                : courseEnrollList.get(Integer.parseInt(ic));
        req.setAttribute("selectedEnroll", selectedEnroll);
        ArrayList<Attend> attendList = new DBContext().getAttends(selectedEnroll.getId());
        req.setAttribute("attendList", attendList);
        int absentSession = 0;
        for (Attend attend : attendList) {
            if (attend.getSession().getStartDate().before(now) && (attend.isPresent() == null || !attend.isPresent())) {
                absentSession++;
            }
        }
        req.setAttribute("absentSession", ((float)absentSession / attendList.size()) * 100);
        req.getRequestDispatcher("attendance-report.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, role, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AttendanceReportHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceReportHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AttendanceReportHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, role, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AttendanceReportHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceReportHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AttendanceReportHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
