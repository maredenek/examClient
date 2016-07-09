/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import static examclient.Administrator.getTab;
import static examclient.Person.frame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import layout.ApplicationFrame;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *  Klasa dostarczająca metod dla roli Egzaminatora.
 * 
 *  @author mariusz
 */
public class Teacher extends Person{
    
    private static JSONArray egzam = new JSONArray();
    
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
     *  Akcja wykonywana po kliknieciu button "Wyslij" na panelu dodawania egzaminu: 
     *  obsluguje funkcjonalnosc dodawania egzaminu.
     * 
     */
    public static void addExam(){
        
        JSONObject req = new JSONObject();
        JSONObject node = new JSONObject();
        
        if( frame.TeacherNazwaEgzaminu.getText().equals("") ){
            JOptionPane.showMessageDialog(frame, "Nazwa egzaminu nie moze byc pusta!");
            return;
        }
        
        if( !frame.TeacherTrescPytania.getText().equals("") )
            addNextQuestion();

        node.put(frame.TeacherNazwaEgzaminu.getText(), egzam);
        req.put("add_exam", node);
        //req.put("add_exam", session_id);
        
        String response = ServerHandler.parseServerResponse(
                klient.sendToServer(req.toString())
        );
        
        if( !response.equals("ok") ){
            JOptionPane.showMessageDialog(frame, response);
            frame.showPanel("TeacherPanel");
        }else{
            JOptionPane.showMessageDialog(frame, "Dodano egzamin!");
            frame.showPanel("TeacherPanel");
        }
        
        egzam = new JSONArray();
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
    
    /**
     * Akcja wykonywana po kliknieciu przycisku "Dodaj egzamin" na panelu Egzaminatora: 
     * przygotowuje panel dodawania egzaminu przez nauczyciela: spina radio buttony 
     * w grupe i czysci wszystkie pola formularza.
     */
    public static void prepareAddingExamPanel(){
        ButtonGroup group = new ButtonGroup();
        
        group.add(frame.TeacherRadio1);
        group.add(frame.TeacherRadio2);
        group.add(frame.TeacherRadio3);
        group.add(frame.TeacherRadio4);
        group.add(frame.TeacherRadio5);
        
        frame.TeacherNazwaEgzaminu.setText("");
        frame.TeacherTrescPytania.setText("");
        frame.TeacherOdpowiedz1.setText("");
        frame.TeacherOdpowiedz2.setText("");
        frame.TeacherOdpowiedz3.setText("");
        frame.TeacherOdpowiedz4.setText("");
        frame.TeacherOdpowiedz5.setText("");
    }
    
    /**
     * Akcja wykonywana po kliknieciu button "Dodaj nastepne" na panelu dodawnia egzaminu: 
     * dodaje kolejne pytanie z formularza do egzaminu.
     */
    public static void addNextQuestion(){
        
        ArrayList<JTextField> odpowiedzi = new ArrayList<>();
        odpowiedzi.add(frame.TeacherOdpowiedz1);
        odpowiedzi.add(frame.TeacherOdpowiedz2);
        odpowiedzi.add(frame.TeacherOdpowiedz3);
        odpowiedzi.add(frame.TeacherOdpowiedz4);
        odpowiedzi.add(frame.TeacherOdpowiedz5);
        
        ArrayList<JRadioButton> buttons = new ArrayList<>();
        buttons.add(frame.TeacherRadio1);
        buttons.add(frame.TeacherRadio2);
        buttons.add(frame.TeacherRadio3);
        buttons.add(frame.TeacherRadio4);
        buttons.add(frame.TeacherRadio5);
        
        if ( frame.TeacherTrescPytania.getText().equals("") ){
            JOptionPane.showMessageDialog(frame, "Treść pytania nie może być pusta!");
            return;
        }
        
        if ( frame.TeacherOdpowiedz1.getText().equals("") || frame.TeacherOdpowiedz2.getText().equals("") ){
            JOptionPane.showMessageDialog(frame, "Conajmniej 2 odpowiedzi musza zostac podane!");
            return;
        }
        
        JSONArray answears = new JSONArray();
        
        for (int i=1; i<6; i++){
            JSONObject obj = new JSONObject();
            int selected = buttons.get(i-1).isSelected() == true ? 1 : 0;
            
            obj.put(Integer.toString(i), odpowiedzi.get(i-1).getText() );
            obj.put("true", Integer.toString(selected));
            
            answears.put(obj);
        }
        
        JSONObject question = new JSONObject();
        
        question.put("tresc", frame.TeacherTrescPytania.getText());
        question.put("odpowiedz", answears);
        
        egzam.put(question);
        
        String name = frame.TeacherNazwaEgzaminu.getText();
        prepareAddingExamPanel();
        frame.TeacherNazwaEgzaminu.setText(name);
    }
    
    /**
     * Metoda wykonywana po kliknieciu przycisku "wróć" na panelu dodawania egzaminu:
     * czysci obiekt JSON reprezentujacy egzamin.
     */
    public static void clearExam(){
        egzam = new JSONArray();
    }
    
}
