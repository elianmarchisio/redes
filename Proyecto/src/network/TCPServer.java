package network;

import components.Message;
import components.SystemQueue;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Process TCP Server Connections
 * Offer Client Request to main app queue
 */
public class TCPServer extends Thread {
    // Server
    ServerSocket serverSocket;
    Socket connectionSocket;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    // Data
    private final int port;
    private final SystemQueue queue;

    /**
     * Create New TCP Server
     * @param serverPort TCP Server port
     * @param mainQueue  Queue from incoming messages
     */
    public TCPServer(int serverPort, SystemQueue mainQueue) {
        port = serverPort;
        queue = mainQueue;
    }
    
    /**
     * TCPServer thread
     */
    @Override
    public void run() {
        try {
            createSocket();
            while(true){
                acceptConnection();
                processData();
            }
        } catch(IOException ex) {
            System.err.println("TCP Server Conection Error :: " + ex);
        }
    }
    
    /**
     * Create TCP server socket
     * @throws IOException 
     */
    private void createSocket() throws IOException {
        serverSocket = new ServerSocket(port);
    }
    
    /**
     * Accept new client connection
     * @throws IOException 
     */
    private void acceptConnection() throws IOException {
        connectionSocket = serverSocket.accept();
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    }
    
    /**
     * Close current client connection
     * @throws IOException 
     */
    public void closeConnection() throws IOException {
        connectionSocket.close();
        inFromClient.close();
        outToClient.close();
    }
    
    /**
     * Get message from connected client
     * @return String message
     * @throws IOException 
     */
    private String getData() throws IOException {
        return inFromClient.readLine();
    }
    
    /**
     * Send message to the connected client
     * @param data String to send
     * @throws IOException 
     */
    public void sendData(String data) throws IOException {
        outToClient.writeBytes(data);
    }
    
    /**
     * Process Client message
     * @throws IOException 
     */
    private void processData() throws IOException {
        try {
            // New client connected
            queue.offer(new Message("CONNECTED",Message.type.client));
            // Print connected client info
            System.out.println("Client Connected " + connectionSocket.toString());
            // Read Client inputs
            while(true){
                String clientSentence = getData().toUpperCase();
                System.out.println("Received: " + clientSentence);
                // Enqueue client request
                queue.offer(new Message(clientSentence,Message.type.client));
            }
        } catch(Exception ex) {
            System.out.println("Client Closed Connection " + connectionSocket.toString());
        }
        closeConnection();
    }
}