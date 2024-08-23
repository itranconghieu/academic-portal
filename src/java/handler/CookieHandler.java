// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author End User
 */
public class CookieHandler {

    public String getValue(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c.getValue();
            }
        }
        return null;
    }

    public ArrayList<String> getRecentSearchResult(HttpServletRequest req) {
        ArrayList<String> recents = new ArrayList<>();
        if (this.getValue(req, "recents") != null) {
            recents.addAll(Arrays.asList(this.getValue(req, "recents").split("/")));
        }
        return recents;
    }

    public void addRecents(HttpServletRequest req, HttpServletResponse resp, String recent) {
        ArrayList<String> recentList = this.getRecentSearchResult(req);
        if (!(recentList.contains(recent.toLowerCase()))) {
            recentList.add(recent);
            StringBuilder recentString = new StringBuilder();
            for (String i : recentList) {
                recentString.append('/').append(i);
            }
            Cookie recents = new Cookie("recents", recentString.toString().replace("/", ""));
            recents.setMaxAge(-1);
            resp.addCookie(recents);
        }
    }
}
