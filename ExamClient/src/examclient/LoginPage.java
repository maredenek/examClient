/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import layout.ApplicationFrame;

/**
 *  Klasa dostarcza metod do obslugi panelu logowania.
 * 
 * @author mariusz
 */
public class LoginPage extends ApplicationFrame{
    
    /**
     * Metoda sprawdza czy pola formularza zostały uzupełnione. 
     * 
     * @return  True: jeżeli przynajmniej jedno pole jest puste, false: w p.p.
     */
    public static boolean areFieldsEmpty(){
        
        if( serverAddress.getText().equals("") )
            return true;
        else if( serverPort.getText().equals("") )
            return true;
        else if ( login.getText().equals("") )
            return true;
        else if ( password.getText().equals("") )
            return true;
        
        return false;
    }
    
}
