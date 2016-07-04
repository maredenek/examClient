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
    
    /**
     * 
     */
    public static void prepareAssignmentPanel(){
        
        String jsonUsers = Person.sendRequest("get_students");
        String jsonGroups = Person.sendRequest("get_groups");
        String statusUsers = ServerHandler.parseServerResponse(jsonUsers);
        String statusGroups = ServerHandler.parseServerResponse(jsonGroups);
        
        if( !statusUsers.contains("ok") ){
            JOptionPane.showMessageDialog(frame, statusUsers);
            frame.showPanel("AdminPanel");
            return;
        }
        
        if( !statusGroups.contains("ok") ){
            JOptionPane.showMessageDialog(frame, statusGroups);
            frame.showPanel("AdminPanel");
            return;
        }
        
        frame.listaStudentow.removeAllItems();
        for (String s : getTab(jsonUsers))
            frame.listaStudentow.addItem(s);
        
        frame.listaGrup.removeAllItems();
        for (String s : getTab(jsonGroups))
            frame.listaGrup.addItem(s);
        
    }
    
    /**
     * 
     */
    public static void assignStudentToGroup(){
        
        JSONObject request = new JSONObject();
        JSONObject assignment = new JSONObject();
        assignment.put("user", (String)frame.listaStudentow.getSelectedItem());
        assignment.put("group", (String)frame.listaGrup.getSelectedItem());
        
        request.put("assign_to_group", session_id);
        request.put("assignment", assignment);
        
        String response = ServerHandler.parseServerResponse(
                klient.sendToServer(request.toString())
        );
        
        if( !response.equals("ok") ){
            JOptionPane.showMessageDialog(frame, response);
            frame.showPanel("AdminPanel");
        }else{
            JOptionPane.showMessageDialog(frame, "Przypisano studenta do grupy");
            frame.showPanel("AdminPanel");
        }
        
    }
    
    /**
     * 
     * @param json
     * @return 
     */
    public static String[] getTab(String json){
        
        JSONObject jsonObj = new JSONObject(json);
        String key = jsonObj.keys().next();
        JSONObject list = jsonObj.getJSONObject(key);
        String[] tab = new String[list.length()];
        
        for (int i=0; i<list.length(); i++){
            tab[i] = list.getString(Integer.toString(i));
        }
        
        return tab;
    }
    
}
