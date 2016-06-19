/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import org.json.JSONObject;

/**
 *  Klasa abstrakcyjna przechowujaca adres ip serwera, login zalogowanego uzytkownika
 *  oraz id sesji.
 * 
 *  @author mariusz
 */
public abstract class Person {
    
    /**
     * Login zalogowanego uzytkownika.
     */
    protected String login;
    
    /**
     * Id sesji nadany przez serwer.
     */
    protected static String session_id;
    
    /**
     * Obiekt do obslugi polaczenia z serwerem.
     */
    protected static TCPClient klient;
    
    /**
     * Konstruktor.
     * 
     * @param login         Login zalogowanego uzytkownika.
     * @param session_id    ID sesji nadany przez serwer.
     */
    public Person(String login, String session_id, TCPClient kl){
        this.login = login;
        this.session_id = session_id;
        this.klient = kl;
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
    
    /**
     *  Przeslanie do serwera komunikatu log_out oraz wyczyszczenie pol obiektu.
     * @return 
     */
    public String logOut(){
        JSONObject msg = new JSONObject();
        msg.put("log_out", session_id);
        
        login = null;
        session_id = null;
        
        return klient.sendToServer(msg.toString());
    }
    
    public static String sendRequest(String req){
        JSONObject msg = new JSONObject();
        msg.put(req, session_id);
        
        return klient.sendToServer(msg.toString());
    }
    
}
