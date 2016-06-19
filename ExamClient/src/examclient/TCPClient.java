/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *  Klasa dostarczająca metod do komunikacji z serwerem.
 * 
 *  @author mariusz
 */
public class TCPClient {
    
    private final String serverIP;
    private final Integer serverPort;

    /**
     * Konstruktor.
     * 
     * @param srvIP String: adres IP serwera.
     * @param port  String: port, na którym serwer pracuje.
     */
    public TCPClient(String srvIP, Integer port) {
        this.serverIP = srvIP;
        this.serverPort = port;

    }
    
    /**
     * Metoda do wysyłania i odbierania komunikatów do/z serwera.
     * 
     * @param text  String: text do wysłania.
     * @return      String: text otrzymany od serwera.
     */
    public String sendToServer(String text){ 
        String response = null;   
        
        try(Socket clientSocket = new Socket(serverIP, serverPort)) {   
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   

            outToServer.writeBytes(text + '\n');
            response = inFromServer.readLine();   

            outToServer.close();
            inFromServer.close();
        }
        catch(UnknownHostException ex){ return "Błąd: proszę podać poprawny adress serwera."; }
        catch(ConnectException ex) { return "Błąd: nie można nawiązać połączenia z serwerem na wskazanym porcie."; }
        catch(SocketException ex) {return "Błąd: nie można polaczyc sie ze wskazana siecia. Sprawdz podane dane.";}
        catch(IOException ex){ ex.printStackTrace(); return "Błąd";}
        
        return response;
    }
}
