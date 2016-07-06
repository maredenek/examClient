/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import layout.ApplicationFrame;
import org.json.JSONObject;

/**
 *  Klasa abstrakcyjna przechowujaca adres ip serwera, login zalogowanego uzytkownika,
 *  id sesji i obiekt reprezentujacy aktywne GUI aplikacji.
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
     * Okno aplikacji.
     */
    protected static ApplicationFrame frame;
    
    /**
     * Konstruktor.
     * 
     * @param login         Login zalogowanego uzytkownika.
     * @param session_id    ID sesji nadany przez serwer.
     * @param kl            Obiekt TCPClient do obslugi komunikacji z serwerem.
     * @param fr            Obiekt ApplicationFrame: aktywne okno aplikacji. 
     */
    public Person(String login, String session_id, TCPClient kl, ApplicationFrame fr){
        this.login = login;
        Person.session_id = session_id;
        Person.klient = kl;
        Person.frame = fr;
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
     *  Przeslanie do serwera komunikatu log_out oraz wyczyszczenie pol tej klasy.
     * 
     * @return 
     */
    public String logOut(){
        JSONObject msg = new JSONObject();
        msg.put("log_out", session_id);
        
        login = null;
        session_id = null;
        
        return klient.sendToServer(msg.toString());
    }
    
    /**
     * Wyslanie do serwera zadania o trzesci przekazanej w parametrze i id serwera
     * pobranym z pola klasy.
     *  
     * @param req   String: tresc zadania do serwera.
     * @return      String: odpowiedz serwera.
     */
    public static String sendRequest(String req){
        JSONObject msg = new JSONObject();
        msg.put(req, session_id);
        
        return klient.sendToServer(msg.toString());
    }
    
}
