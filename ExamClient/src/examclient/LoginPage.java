/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import javax.swing.JOptionPane;
import layout.ApplicationFrame;
import org.json.JSONObject;

/**
 *  Klasa dostarcza metod do obslugi panelu logowania.
 * 
 * @author mariusz
 */
public class LoginPage{
    
    /**
     * Metoda sprawdza czy pola formularza zostały uzupełnione. 
     * 
     * @param fr
     * @return  True: jeżeli przynajmniej jedno pole jest puste, false: w p.p.
     */
    public static boolean areFieldsEmpty(ApplicationFrame fr){
        
        if( fr.getServerAddr().equals("") )
            return true;
        else if( fr.getServerPort().equals("") )
            return true;
        else if ( fr.getLogin().equals("") )
            return true;
        else if ( fr.getPassword().equals("") )
            return true;
        
        return false;
    }
    
    /**
     * Event po kliknięciu przycisku "Zaloguj" na formularzu logowania.
     * 
     * @param frame     Frame aplikacji.
     */
    public static void logIn(ApplicationFrame frame){
        //sprawdzanie czy login i haslo zostaly podane
        if ( areFieldsEmpty(frame) ) {
            JOptionPane.showMessageDialog(frame, "Żadne pole formularza nie może być puste!");
            return;
        }
        
        String loginName = frame.getLogin();
        String haslo = MD5Converter.toMD5(frame.getPassword());
        Integer port = null;
        String srvResponse = null;
        String status = null;
        
        try{
            port = Integer.parseInt(frame.getServerPort());
            //sprawdzanie czy port jest liczba naturalna wieksza od 2
            if ( port < 3 ) {throw new IllegalStateException();}
        }
        catch (NumberFormatException | IllegalStateException ex){
            JOptionPane.showMessageDialog(frame, "Proszę podać port serwera jako liczbę naturalną większą od 2!");
            return;
        }
        
        //tworzenie obiektu do komunikacji z serwerem
        TCPClient klient = new TCPClient(frame.getServerAddr(), port);
        
        JSONObject tab = new JSONObject();
        tab.put( "login", loginName );
        tab.put( "haslo", haslo );
        
        JSONObject msg = new JSONObject();
        msg.put("log_in", tab);

        srvResponse = klient.sendToServer( msg.toString() );
        
        if ( srvResponse.contains("Błąd")){
            JOptionPane.showMessageDialog(frame, srvResponse);
            return;
        }
        
        status = ServerHandler.parseServerResponse(srvResponse, frame, klient);
        
        if ( !status.equals("ok") )
            JOptionPane.showMessageDialog(frame, status);
        
    }
    
}
