/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

/**
 *  Klasa dostarczająca metod dla roli Egzaminatora.
 * 
 *  @author mariusz
 */
public class Teacher extends Person{
    
    /**
     * Konstruktor.
     * 
     * @param ip            String - ip serwera.
     * @param login         String - login użytkownika.
     * @param session_id    String - id sesji.
     */
    public Teacher(String ip, String login, String session_id) {
        super(ip, login, session_id);
    }
    
    public void addExam(){
        
    }
    
    public void assignExamToGroup(){
        
    }
    
    public void checkAnswears(){
        
    }
    
    public void viewAnswears(){
        
    }
    
}
