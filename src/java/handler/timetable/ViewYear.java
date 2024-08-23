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
import handler.authen.BasedAuthentication;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ViewYear extends BasedAuthentication{
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    protected void processRequest(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClassNotFoundException, SQLException, ParseException {
        Date now = new Date();
        String iy = req.getParameter("selected-year");
        int startYear = account.getRecognizeDate().getYear();
        int endYear = now.getYear();
        ArrayList<Integer> years = this.getYearList(startYear, endYear);
        req.setAttribute("years", years);
        int selectedYear = iy == null ? now.getYear()
                : years.get(Integer.parseInt(iy));
        req.setAttribute("selectedYear", selectedYear);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectedYear + 1900);
        req.setAttribute("calendar", calendar);
        HashMap<String, ArrayList<Attend>> sessionMap = new HashMap<>();
        for (int month = 0; month < 12; month++) {
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            int numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= numDays; i++) {
                  calendar.set(Calendar.DAY_OF_MONTH, i);
                Date date = calendar.getTime();
                String key = df.format(date);
                ArrayList<Attend> attendList = new ArrayList<>();
                try {
                    attendList = new DBContext().getAttends((Student) account, date);
                } catch (Exception e) {
                    ArrayList<transact.Session> sessionList = new DBContext().getSession((Lecturer) account, date);
                    for (transact.Session s : sessionList) {
                        Attend t = new Attend();
                        t.setSession(s);
                        attendList.add(t);
                    }
                }
                sessionMap.put(key, attendList);
            }
        }
        req.setAttribute("sessionMap", sessionMap);
        req.getRequestDispatcher("view-year.jsp").forward(req, resp);
    }
    
    @Override
    protected void doGet(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewYear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewYear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ViewYear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(Account account, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(account, req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewYear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ViewYear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ViewYear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Integer> getYearList(int startYear, int endYear) {
        int localVar = startYear;
        ArrayList<Integer> yearList = new ArrayList<>();
        int length = (endYear - localVar) + 1;
        while (yearList.size() < length) {
            yearList.add(localVar ++);
        }
        return  yearList;
    }
}
