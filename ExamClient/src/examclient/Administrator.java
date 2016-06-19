/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

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
     */
    public Administrator(String login, String session_id, TCPClient kl) {
        super(login, session_id, kl);
    }
    
    public static String addGroup(){
        return "";
    }
    
    public static String assignStudentToGroup(){
        return "";
    }
    
}
