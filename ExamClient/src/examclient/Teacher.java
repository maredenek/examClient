/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import static examclient.Administrator.getTab;
import static examclient.Person.frame;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import layout.ApplicationFrame;
import org.json.JSONArray;
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
     * @param fr
     */
    public Teacher(String login, String session_id, TCPClient kl, ApplicationFrame fr) {
        super(login, session_id, kl, fr);
    }
    
    /**
     *  Metoda obslugujaca funkcjonalnosc dodawania egzaminu - do implementacji w przyszlosci.
     * 
     *  @return     String: do ustalenia podczas implementacji.
     */
    public static String addExam(){
        return "";
    }
    
    /**
     * Akca wykonywana po kliknieciu buttona "Przypisz egzamin grupie" na panelu Egzaminatora: 
     * wysylane sa zadania do serwera i na podstawie zwroconych danych
     * uzupelniane sa selecty na kolejnym panelu.
     */
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
    
    /**
     * Akcja wykonywana po kliknieciu buttona "Przypisz" na panelu przypisywania
     * egzaminu do grupy: wysyla do serwera zadanie przypisania wybranego egzaminu 
     * do wskazanej grupy.
     */
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
            frame.showPanel("TeacherPanel");
        }else{
            JOptionPane.showMessageDialog(frame, "Przypisano egzamin grupie");
            frame.showPanel("TeacherPanel");
        }
    }
    
    /**
     * Metoda obslugujaca funkcjonalnosc do zlecania sprawdzania wynikow odpowiedzi
     * uczniow przeslanych do serwera - zostanie zaimplementowana w przyszlosci.
     * 
     * @return  String: tresc do ustalenia podczas implementacji.
     */
    public static String checkAnswears(){
        return "";
    }
    
    /**
     * Metoda wysyla zadanie do serwera o wyniki egzaminow uczniow i prezentuje
     * je w polu textowym na panelu.
     */
    public static void viewAnswears(){
        String jsonResults = Person.sendRequest("view_results");
        String statusResults = ServerHandler.parseServerResponse(jsonResults);
        Vector<String> data = new Vector<>();
        
        if( !statusResults.contains("ok") ){
            JOptionPane.showMessageDialog(frame, statusResults);
            frame.showPanel("TeacherPanel");
            return;
        }
        
        JSONObject obj = new JSONObject(jsonResults).getJSONObject("students_answears");
        
        Iterator iter = obj.keys();
        for (int i=0; i<obj.length(); i++){
            String student = (String)iter.next();
            data.add(student + " :");
            
            JSONArray arr = obj.getJSONArray(student);
            for (int j=0; j<arr.length(); j++){
                JSONObject egzam = arr.getJSONObject(j);
                String egzaminName = egzam.getString("egzamin");
                double scored = egzam.getDouble("score");
                double max = egzam.getDouble("max");

                data.add("      " + egzaminName + " : " + (int)((scored/max)*100) + " %");
            }
            data.add(" ");
        }
        
        frame.ListaWynikiEgzaminow1.setListData(data);
        
    }
    
}
