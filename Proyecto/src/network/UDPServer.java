package network;

import components.Message;
import components.Message.type;
import components.SystemQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Process UDP Connections
 * Add (offers) udp message to main app queue
 */
public class UDPServer extends Thread {
    // UDP Server
    DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    DatagramPacket sendPacket;
    // client data
    private InetAddress source;
    private InetAddress destination;
    // Data
    private final int port;
    private final SystemQueue queue;
    private final int DATA_LENGHT = 1024;
    
    /**
     * Create new UDP Server
     * @param serverPort udp Server Port
     * @param mainQueue Queue for incoming messages
     */
    public UDPServer(int serverPort, SystemQueue mainQueue) {
        port = serverPort;
        queue = mainQueue;
    }
    
    /**
     * UDPServer Thread
     */
    @Override
    public void run(){
        try {
            // Create Server Socket
            serverSocket = new DatagramSocket(port);
            while(true){
                // Create Receive packet
                receivePacket = new DatagramPacket(new byte[DATA_LENGHT], DATA_LENGHT);
                // Wait for receive packet
                serverSocket.receive(receivePacket);
                // Get Client Data
                source = receivePacket.getAddress();
                // Get Message and enqueue
                String clientSentence = new String(receivePacket.getData());
                queue.offer(new Message(clientSentence, type.peer, source.getHostAddress(), receivePacket.getPort()));
            }
        } catch (IOException ex) {
            System.err.println("UDP Server Connection Error :: " + ex);
        }
    }
    
    /**
     * Send UDP Message
     * @param message Message to Send (port and ip stored in the message)
     * @throws IOException 
     */
    public void sendData(Message message) throws IOException {
        byte[] response = message.getMessage().getBytes();
        destination = InetAddress.getByName(message.getIP());
        sendPacket =  new DatagramPacket(response, response.length, destination, message.getPort());
        serverSocket.send(sendPacket);
    }
}