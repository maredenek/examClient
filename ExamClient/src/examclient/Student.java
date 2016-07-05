/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import static examclient.Person.frame;
import java.util.Vector;
import javax.swing.JOptionPane;
import layout.ApplicationFrame;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *  Klasa dostarczająca metod dla roli Studenta.
 * 
 *  @author mariusz
 */
public class Student extends Person{
    
    /**
     * Konstruktor.
     * 
     * @param login         String - login użytkownika.
     * @param session_id    String - id sesji.
     * @param kl            Obiekt TCPClient do obslugi polaczenia z serwerem.
     */
    public Student(String login, String session_id, TCPClient kl, ApplicationFrame fr){
        super(login, session_id, kl, fr);
    }
    
    /**
     * 
     * @return 
     */
    public static String doExam(){
        return "";
    }
    
    /**
     * 
     */
    public static void checkResults(){
        String results = sendRequest("get_answears");
        String parsed = ServerHandler.parseServerResponse(results);
        String[] data = new String[50];
        
        if ( !parsed.equals("ok") ){
            JOptionPane.showMessageDialog(frame, parsed);
            frame.showPanel("StudentPanel");
            return;
        }
        
        JSONArray tab = new JSONObject(results).getJSONArray("results");
        
        for(int i = 0; i<tab.length(); i++){
            JSONObject egzam = tab.getJSONObject(i);
            String egzaminName = egzam.getString("egzamin");
            double scored = egzam.getDouble("score");
            double max = egzam.getDouble("max");
            
            data[i] = egzaminName + " : " + (int)((scored/max)*100) + " %";
        }
        
        frame.ListaWynikiEgzaminow.setListData(data);
    }
    
}
