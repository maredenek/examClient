/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import org.json.JSONObject;

/**
 *  Klasa do sprawdzania komunikatow zwracanych przez serwer.
 * 
 * @author mariusz
 */
public class ServerHandler {
    
    /**
     * Statyczna metoda do sprawdzania tresci komunikatow przeslanych przez serwer.
     * W przypadku nierozpoznania komunikatu nie jest wykonywana zadna akcja ze strony
     * klienta.
     * 
     * @param str   String: tresc komunikatu od serwera.
     * @return      OK: jezeli komunikat zostal rozpoznany i odpowiedni komunikat
     *              w przeciwnym przypadku.
     */
    public static String parseServerResponse(String str){
        
        JSONObject msg = new JSONObject(str);
        String key = msg.keys().next();
        
        switch(key){
            case "logged":
                LoginPage.chooseRole(msg.getString("logged") );
                return "ok";
            case "error":
                return msg.getString("error");
            case "groupsList":
                return "ok";
            case "studentsList":
                return "ok";
            case "student_assigned":
                return "ok";
            case "examsList":
                return "ok";
            case "exam_assigned":
                return "ok";
            case "results":
                return "ok";
            case "students_answears":
                return "ok";
            case "exam_added":
                return "ok";
            default:
                return ("Nie rozpoznano odpowiedzi serwera: " + str);
                
        }
    }
}