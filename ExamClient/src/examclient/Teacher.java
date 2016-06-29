/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import layout.ApplicationFrame;

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
    
    public static String assignExamToGroup(){
        return "";
    }
    
    public static String checkAnswears(){
        return "";
    }
    
    public static String viewAnswears(){
        return "";
    }
    
}
