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
import java.net.Socket;

/**
 *  Klasa dostarczająca metod do komunikacji z serwerem.
 * 
 *  @author mariusz
 */
public class TCPClient {
    
    private final String serverIP;
    private final Integer serverPort;

    public TCPClient(String srvIP, String port) {
        this.serverIP = srvIP;
        this.serverPort = Integer.parseInt(port);

    }
    
    public String sendToServer(String text) throws IOException{
        String sentence;   

        Socket clientSocket = new Socket(serverIP, serverPort);   
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
 
        outToServer.writeBytes(text + '\n');
        sentence = inFromServer.readLine();   
  
        outToServer.close();
        inFromServer.close();
        clientSocket.close();
        
        return sentence;
    }
}
