// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import entity.Account;
import entity.Lecturer;
import entity.Role;
import handler.authen.autho.PermissionAuthorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.DBContext;
import transact.Attend;

/**
 *
 * @author End User
 */
public class TakeAttendanceHandler extends PermissionAuthorization{
    private transact.Session session;
    private ArrayList<Attend> attendList;
    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestEdit = req.getParameter("edit");
            String courseId = req.getParameter("course");
            String groupId = req.getParameter("group");
            String sessionNo = req.getParameter("session");
            session = new DBContext().getSession(groupId, courseId, Integer.parseInt(sessionNo));
            req.setAttribute("session", session);
            attendList = new DBContext().getAttends(session);
            req.setAttribute("attendList", attendList);
            boolean timeOut = new Date().after(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(new SimpleDateFormat("yyyy-MM-dd").format(session.getStartDate().getTime() + 86400000)
                            + " " + session.getSlot().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            boolean timeOn = !new Date().before(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(new SimpleDateFormat("yyyy-MM-dd").format(session.getStartDate())
                            + " " + session.getSlot().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            req.setAttribute("timeOut", timeOut);
            if (!timeOn) {
                resp.sendRedirect("http://localhost:8080/Assignment/timetable/week");
            } else {
                if ((!timeOut && (requestEdit != null || session.isTaked() == null || !session.isTaked()))
                        && (session.getLecturer().getId().equals(account.getId()))) {
                    req.getRequestDispatcher("attendance/take.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("attendance/view.jsp").forward(req, resp);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeAttendanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TakeAttendanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TakeAttendanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] attendStatus = req.getParameterValues("attendStatus");
            String[] comments = req.getParameterValues("comment");
            ArrayList<Attend> editedList = new ArrayList<>();
            String taker = account.getId();
            for (int i = 0; i < attendList.size(); i++) {
                Attend attend = attendList.get(i);
                boolean status = Boolean.parseBoolean(attendStatus[i]);
                if (attend.isPresent() == null || status != attend.isPresent()
                        || !comments[i].equalsIgnoreCase(attend.getComment())
                        || !taker.equals(attend.getModifier().getId())) {
                    attend.setAttendStatus(status);
                    attend.setComment(comments[i]);
                    Lecturer modifier = new Lecturer();
                    modifier.setId(taker);
                    attend.setModifier(modifier);
                    editedList.add(attend);
                }
            }
            new DBContext().updateAttends(editedList);     
            new DBContext().updateSession(session);
            req.setAttribute("session", session);
            req.setAttribute("attendList", attendList);
            resp.sendRedirect("http://localhost:8080/Assignment/attendance?course=" + session.getSession().getCourse().getId() +"&group=" + session.getGroupId() +"&session=" + session.getSession().getNo());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeAttendanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TakeAttendanceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
