// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websocket;

import com.google.gson.Gson;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

/**
 *
 * @author End User
 */
public class MessageDecoder implements Decoder.Text<Message>{

    @Override
    public Message decode(String json) throws DecodeException {
        Gson gson = new Gson();
        Message msg = gson.fromJson(json, Message.class);
        return msg;
    }

    @Override
    public boolean willDecode(String string) {
        boolean canDecode = true;
        return canDecode;
    }
    
    
}
