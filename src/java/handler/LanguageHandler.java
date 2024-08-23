// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author End User
 */
public class LanguageHandler extends HttpServlet {
    private final String baseName = "lang";
    private final Locale en = new Locale("en");
    private final Locale vi = new Locale("vi", "vietnam");
    private ResourceBundle lang;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectURL = req.getParameter("redirectURL");
        String locale = req.getParameter("locale");
        if ("en".equalsIgnoreCase(locale)) {
            lang = ResourceBundle.getBundle(baseName, en);
        } else if ("vi".equalsIgnoreCase(locale)) {
            lang = ResourceBundle.getBundle(baseName, vi);
        }
        req.getSession().setAttribute("lang", lang);
        resp.sendRedirect(redirectURL);
    }
    
}
