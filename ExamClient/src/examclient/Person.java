/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

/**
 *  Klasa abstrakcyjna przechowujaca adres ip serwera, login zalogowanego uzytkownika
 *  oraz id sesji.
 * 
 *  @author mariusz
 */
public abstract class Person {
    
    /**
     * Adres IP serwera.
     */
    protected String ip;
    
    /**
     * Login zalogowanego uzytkownika.
     */
    protected String login;
    
    /**
     * Id sesji nadany przez serwer.
     */
    protected String session_id;
    
    /**
     * Konstruktor.
     * 
     * @param ip            IP serwera.
     * @param login         Login zalogowanego uzytkownika.
     * @param session_id    ID sesji nadany przez serwer.
     */
    public Person(String ip, String login, String session_id){
        this.ip = ip;
        this.login = login;
        this.session_id = session_id;
    }

    /**
     * Pobierz adress ip serwera.
     * 
     * @return  String z adresem ip serwera.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Pobierz login zalogowanego użytkownika.
     * 
     * @return  String z loginem uzytkownika.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Pobierz id sesji.
     * 
     * @return  String z id sesji uzytkownika.
     */
    public String getSession_id() {
        return session_id;
    }
    
    public void logOut(){
        
    }
    
}
