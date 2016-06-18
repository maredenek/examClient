/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa dostarczająca metod do konwersji String na MD5. 
 *  
 * @author mariusz
 */
public class MD5Converter {
    
    /**
     *  Metoda konwertuje string przesłany w parametrze na hash md5.
     * 
     *  @param text  String - text do konwersji na MD5.
     *  @return      String - skonwertowany text do MD5.
     */
    public static String toMD5(String text){
        MessageDigest md;
        StringBuilder sb = new StringBuilder();
        
        try {
            md = MessageDigest.getInstance("MD5");
            
            byte[] arr = md.digest(text.getBytes());

            for (int i = 0; i < arr.length; ++i) {
                sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
        
    }
}
