// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import entity.Course;
import entity.CourseGrade;
import entity.Curriculum;
import entity.GradeItem;
import entity.Major;
import entity.Student;
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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.DBContext;
import transact.CourseEnroll;
import transact.Grade;

/**
 *
 * @author End User
 */
public class TranscriptHandler extends PermissionAuthorization {
    private Student student;

    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (role.have("/search")) {
                String studentId = req.getParameter("student");
                student = new DBContext().getStudent(studentId);
                if ((studentId != null || studentId != "") && student.getId() != null)  {
                    new CookieHandler().addRecents(req, resp, studentId);
                } else resp.getWriter().print("Not found");
            } else {
                try {
                    student = (Student) account;
                } catch (Exception e) {
                    resp.getWriter().print("Not yet");
                }
            }
            req.setAttribute("student", student);
            Major major = student.getMajor();
            Curriculum curriculum = new DBContext().getCurriculum(major);
            req.setAttribute("curriculum", curriculum);
            ArrayList<Course> courseList = curriculum.getCourseList();
            HashMap<String, CourseEnroll> courseMap = new HashMap<>();
            DBContext dbContext = new DBContext();
            for (Course course : courseList) {
                CourseEnroll courseEnroll = dbContext.getCourseEnroll(student, course);
                courseMap.put(course.getId(), courseEnroll);
            }
            req.setAttribute("courseMap", courseMap);
            String courseId = req.getParameter("course");
            String semesterId = req.getParameter("semester");
            if (courseId != null && semesterId != null) {
                Semester semester = new DBContext().getSemester(Integer.parseInt(semesterId));
                HashMap<Integer, ArrayList<CourseEnroll>> semesterMap = new DBContext().getCourseEnrolls(student.getId());
                ArrayList<CourseEnroll> courseEnrollList = semesterMap.get(semester.getId());
                req.setAttribute("courseEnrollList", courseEnrollList);
                Course course = new DBContext().getCourse(courseId);
                CourseGrade courseGrade = new DBContext().getCourseGrade(course);
                ArrayList<GradeItem> gradeItems = (ArrayList<GradeItem>) courseGrade.getGradeItems();
                HashMap<String, ArrayList<GradeItem>> itemMap = new HashMap<>();
                for (GradeItem item : gradeItems) {
                    String category = item.getCategory().getName();
                    if (itemMap.get(category) == null) {
                        ArrayList<GradeItem> items = new ArrayList();
                        itemMap.put(category, items);
                    }
                    itemMap.get(category).add(item);
                }
                req.setAttribute("itemMap", itemMap);
                CourseEnroll courseEnroll = courseMap.get(courseId);
                req.setAttribute("courseEnroll", courseEnroll);
                HashMap<String, Grade> gradeMap = new HashMap<>();
                for (GradeItem item : gradeItems) {
                    gradeMap.put(courseEnroll.getCourse().getId() + "/" + item.getId(),
                            new DBContext().getGrade(courseEnroll.getId(), item));
                }
                req.setAttribute("gradeMap", gradeMap);
                req.setAttribute("courseGrade", courseGrade);
            }
            req.getRequestDispatcher("transcript.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TranscriptHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TranscriptHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TranscriptHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account,  Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }

    
}
