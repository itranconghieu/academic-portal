// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler.authen;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author End User
 */
public abstract class GoogleAuthentication extends HttpServlet{
    private final String clientId = "444175066040-vfraie2ohc68jei5v58b1il2kppqm0o4.apps.googleusercontent.com";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NetHttpTransport transport = new NetHttpTransport();
            JacksonFactory jsonFactory = new JacksonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId))
                    .build();
            // (Receive idTokenString by HTTPS POST)
            String idTokenString = req.getParameter("credential");
            GoogleIdToken idToken = verifier.verify(idTokenString);
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();

            this.doPost(userId, req, resp);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(GoogleAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract void doPost(String userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;  
}
