/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import layout.ApplicationFrame;
import org.json.JSONObject;

/**
 *
 * @author mariusz
 */
public class ServerHandler {
    
    private static void chooseRole(String sessionID, ApplicationFrame fr, TCPClient client){
        String role = ""+sessionID.charAt(32);
        switch(role){
            case "0":
                fr.showPanel("StudentPanel");
                fr.setRole( new Student(fr.getLogin(), sessionID, client) );
                break;
            case "1": 
                fr.showPanel("TeacherPanel");
                fr.setRole( new Teacher(fr.getLogin(), sessionID, client) );
                break;
            case "2":
                fr.showPanel("chooseRolePanel");
                fr.setRole( new Administrator(fr.getLogin(), sessionID, client) );
        }
    }
    
    public static String parseServerResponse(String str, ApplicationFrame frame, TCPClient client){
        
        JSONObject msg = new JSONObject(str);
        String key = msg.keys().next();
        
        switch(key){
            case "logged":
                chooseRole(msg.getString("logged"), frame, client);
                return "ok";
            case "error":
                return msg.getString("error");
            default:
                return "blad";
                
        }
    }
}