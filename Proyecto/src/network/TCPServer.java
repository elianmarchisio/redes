package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class TCPServer extends Thread {
    String clientSentence;
    String capitalizedSentence;
    //
    ServerSocket serverSocket;
    Socket connectionSocket;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    //
    private final int port;

    public TCPServer(int serverPort){
        port = serverPort;
    }
    
    int i = 0;
    @Override
    public void run(){
        try {
            // Create Socket
            serverSocket = new ServerSocket(port);
            
            while(true){
                connectionSocket = serverSocket.accept();
                //
                while(true){
                    i++;
                    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    clientSentence = inFromClient.readLine();
                    System.out.println("Received: " + clientSentence);
                    capitalizedSentence = clientSentence.toUpperCase() + '\n';
                    outToClient.writeBytes(capitalizedSentence);
                    if(i == 4) break;
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
