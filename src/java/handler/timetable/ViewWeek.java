// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler.timetable;

import entity.Account;
import entity.Lecturer;
import entity.Student;
import entity.TimeSlot;
import entity.Role;
import helper.Week;
import handler.authen.autho.PermissionAuthorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.DBContext;
import transact.Attend;

/**
 *
 * @author End User
 */
public class ViewWeek extends PermissionAuthorization{
    
    protected void processRequest(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClassNotFoundException, SQLException, ParseException {
        ArrayList<TimeSlot> timeSlots = new DBContext().getTimeSlots();
        req.setAttribute("timeSlots", timeSlots);
        Date now = new Date();
        String iw = req.getParameter("selected-week");
        String iy = req.getParameter("selected-year");
        int startYear = account.getRecognizeDate().getYear();
        int endYear = now.getYear();
        ArrayList<Integer> years = this.getYearList(startYear, endYear);
        req.setAttribute("years", years);
        int selectedYear = iy == null ? now.getYear()
                : years.get(Integer.parseInt(iy));
        req.setAttribute("selectedYear", selectedYear);
        ArrayList<Week> weeks = this.getWeekList(selectedYear);
        req.setAttribute("weeks", weeks);
        Week selectedWeek = iw == null ? this.getWeek(weeks, now)
                : weeks.get(Integer.parseInt(iw));
        req.setAttribute("selectedWeek", selectedWeek);
        HashMap<String, Attend> sessionMap = new HashMap<>();
        for (Date date = new Date(selectedWeek.getStartDate().getTime()); !date.after(selectedWeek.getEndDate()); date.setTime(date.getTime() + 86400000)) {
            for (TimeSlot slot : timeSlots) {
                String key = df.format(date) + " " + slot.getSlot();
                Attend t = new Attend();
                try {
                    t = new DBContext().getAttend((Student) account, date, slot);
                } catch (Exception e) {
                    transact.Session s = new DBContext().getSession((Lecturer) account, date, slot);
                    t.setSession(s);
                }
                if (t.getSession() != null || t != null) {
                     sessionMap.put(key, t);
                }
            }
        }
        req.setAttribute("sessionMap", sessionMap);
        req.getRequestDispatcher("view-week.jsp").forward(req, resp);
    }


    @Override
    protected void doGet(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewWeek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewWeek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ViewWeek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account, Role role, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewWeek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewWeek.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ViewWeek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public ArrayList<Integer> getYearList(int startYear, int endYear) {
        int localVar = startYear;
        ArrayList<Integer> yearList = new ArrayList<>();
        int length = (endYear - localVar) + 1;
        while (yearList.size() < length) {
            yearList.add(localVar ++);
        }
        return  yearList;
    }
            
    public ArrayList<Week> getWeekList(int year) throws ParseException {
        ArrayList<Week> weekList = new ArrayList<>();
        Date date = new Date();
        int day = 1;
        // Loop for searching the first monday;
        while (day <= 7) {
            date = df.parse((year + 1900) + "-01-" + day);
            if (date.getDay() == 1) {
               break;
            }
            day++;
        }
        while (weekList.size() < 52) {
            Week week = new Week();
            Date startDate = new Date(date.getTime());
            week.setStartDate(startDate);
            Date endDate = new Date(startDate.getTime() + 604799000);
            week.setEndDate(endDate);
            weekList.add(week);
            date.setTime(date.getTime() + 604800000);
        }
        return weekList;
    }
    
    public Week getWeek(ArrayList<Week> weekList, Date date) {
        Week week = new Week();
        for (Week i : weekList) {
            Date startDate = i.getStartDate();
            Date endDate = i.getEndDate();
            if ((date.getTime() >= startDate.getTime())
                    && (date.getTime() <= endDate.getTime())) {
                week = i;
                break;
            }
        }
        return week == null ? weekList.get(51) : week;
    } 

    
}
