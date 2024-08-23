// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websocket;

import com.google.gson.Gson;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

/**
 *
 * @author End User
 */
public class MessageEncoder implements Encoder.Text<Message>{
    
    @Override
    public String encode(Message msg) throws EncodeException {
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        return json;
    }

}
