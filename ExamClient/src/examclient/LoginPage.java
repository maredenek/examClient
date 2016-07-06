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
    
    private static ApplicationFrame appFrame;
    private static TCPClient kl;
    
    /**
     * Metoda sprawdza czy pola formularza zostały uzupełnione. 
     * 
     * @param fr    ApplicationForm: aktywne GUI aplikacji.
     * @return      True: jeżeli przynajmniej jedno pole jest puste, false: w p.p.
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
     * Akcja po kliknięciu przycisku "Zaloguj" na formularzu logowania.
     * 
     * @param frame     ApplicationForm: aktywne GUI aplikacji.
     */
    public static void logIn(ApplicationFrame frame){
        appFrame = frame;
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
        kl = new TCPClient(frame.getServerAddr(), port);
        
        JSONObject tab = new JSONObject();
        tab.put( "login", loginName );
        tab.put( "haslo", haslo );
        
        JSONObject msg = new JSONObject();
        msg.put("log_in", tab);

        srvResponse = kl.sendToServer( msg.toString() );

        if ( srvResponse.contains("Błąd")){
            JOptionPane.showMessageDialog(frame, srvResponse);
            return;
        }
        
        status = ServerHandler.parseServerResponse(srvResponse);
        
        if ( !status.equals("ok") )
            JOptionPane.showMessageDialog(frame, status);
        
    }
    
    /**
     * Metoda do sprawdzania, ktory formularz ma zostac zaprezentowany uzytkownikowi
     * na podstawie id sesji nadanej przez serwer.
     * 
     * @param sessionID
     */
    public static void chooseRole(String sessionID){
        String role = ""+sessionID.charAt(32);
        switch(role){
            case "0":
                appFrame.showPanel("StudentPanel");
                appFrame.setRole( new Student(appFrame.getLogin(), sessionID, kl, appFrame) );
                break;
            case "1": 
                appFrame.showPanel("TeacherPanel");
                appFrame.setRole( new Teacher(appFrame.getLogin(), sessionID, kl, appFrame) );
                break;
            case "2":
                appFrame.showPanel("chooseRolePanel");
                appFrame.setRole( new Administrator(appFrame.getLogin(), sessionID, kl, appFrame) );
        }
    }
    
}
