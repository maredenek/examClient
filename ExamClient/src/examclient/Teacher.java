/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import static examclient.Administrator.getTab;
import static examclient.Person.frame;
import javax.swing.JOptionPane;
import layout.ApplicationFrame;
import org.json.JSONObject;

/**
 *  Klasa dostarczająca metod dla roli Egzaminatora.
 * 
 *  @author mariusz
 */
public class Teacher extends Person{
    
    /**
     * Konstruktor.
     * 
     * @param login         String - login użytkownika.
     * @param session_id    String - id sesji.
     * @param kl            Obiekt TCPClient do obslugi polaczenia z serwerem.
     */
    public Teacher(String login, String session_id, TCPClient kl, ApplicationFrame fr) {
        super(login, session_id, kl, fr);
    }
    
    public static String addExam(){
        return "";
    }
    
    public static void prepareAssignmentPanel(){
        String jsonExams = Person.sendRequest("get_exams");
        String jsonGroups = Person.sendRequest("get_groups");
        String statusExams = ServerHandler.parseServerResponse(jsonExams);
        String statusGroups = ServerHandler.parseServerResponse(jsonGroups);
        
        if( !statusExams.contains("ok") ){
            JOptionPane.showMessageDialog(frame, statusExams);
            frame.showPanel("TeacherPanel");
            return;
        }
        
        if( !statusGroups.contains("ok") ){
            JOptionPane.showMessageDialog(frame, statusGroups);
            frame.showPanel("TeacherPanel");
            return;
        }
        
        frame.listaEgzaminow.removeAllItems();
        for (String s : getTab(jsonExams))
            frame.listaEgzaminow.addItem(s);
        
        frame.listaGrup1.removeAllItems();
        for (String s : getTab(jsonGroups))
            frame.listaGrup1.addItem(s);
    }
    
    public static void assignExamToGroup(){
        JSONObject request = new JSONObject();
        JSONObject assignment = new JSONObject();
        assignment.put("exam", (String)frame.listaEgzaminow.getSelectedItem());
        assignment.put("group", (String)frame.listaGrup1.getSelectedItem());
        
        request.put("assign_exam_to_group", session_id);
        request.put("assignment", assignment);
        
        String response = ServerHandler.parseServerResponse(
                klient.sendToServer(request.toString())
        );
        
        if( !response.equals("ok") ){
            JOptionPane.showMessageDialog(frame, response);
            frame.showPanel("AdminPanel");
        }else{
            JOptionPane.showMessageDialog(frame, "Przypisano egzamin grupie");
            frame.showPanel("AdminPanel");
        }
    }
    
    public static String checkAnswears(){
        return "";
    }
    
    public static String viewAnswears(){
        return "";
    }
    
}
