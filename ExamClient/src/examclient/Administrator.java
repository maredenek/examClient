/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import javax.swing.JOptionPane;
import layout.ApplicationFrame;
import org.json.JSONObject;

/**
 *  Klasa dostarczająca metod dla roli Administratora.
 * 
 *  @author mariusz
 */
public class Administrator extends Teacher{
    
    /**
     * Konstruktor.
     * 
     * @param login         String - login użytkownika.
     * @param session_id    String - id sesji.
     * @param kl            Obiekt TCPClient do obslugi polaczenia z serwerem.
     * @param fr
     */
    public Administrator(String login, String session_id, TCPClient kl, ApplicationFrame fr) {
        super(login, session_id, kl, fr);
    }
    
    public static void addGroup(){
        
        JSONObject msg = new JSONObject();
        JSONObject obj = new JSONObject();
        String nazwaGrupy = frame.getNewGroupName();
        
        //sprawdzenie czy pole z nazwa grupy nie jest puste
        if( nazwaGrupy.equals("") ){
            JOptionPane.showMessageDialog(frame, "Proszę podać nazwę grupy!");
            return;
        }
        
        obj.put("name", nazwaGrupy);
        obj.put("session_id", session_id);
        
        msg.put("add_group", obj);
        
        String message = klient.sendToServer(msg.toString());
        
        //sprawdzenie czy nie nastąpiło zerwanie połączenia z serwerem
        if( message.contains("Błąd") ){
            JOptionPane.showMessageDialog(frame, message);
            return;
        }
        
        //sprawdzenie odpowiedzi serwera
        JSONObject response = new JSONObject(message);
        
        if( response.has("error") )
            JOptionPane.showMessageDialog(frame, response.get("error"));
        else{
            JOptionPane.showMessageDialog(frame, response.get("message"));
            frame.showPanel("AdminPanel");
        }
        
    }
    
    public static String assignStudentToGroup(){
        
        //String[] users = Person.sendRequest("get_users");
        //String[] groups = Person.sendRequest("get_groups");
        
        return "";
    }
    
}
