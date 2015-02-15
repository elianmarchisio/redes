package system;

import components.SystemQueue;
import components.Vehicle;
import network.TCPServer;
import network.UDPServer;


public class MainSystem {
    static int MAX_SEATS = 50;
    static int TCP_PORT = 4401;
    static int UDP_PORT = 5501;
    
    /**
     * 
     * @param argv 
     */
    public static void main(String argv[]){
        // Clients Request and peers messaje queue
        SystemQueue mainQueue = new SystemQueue();
        // Main Vehicle data
        Vehicle mainVehicle = new Vehicle(MAX_SEATS);
        // Main TCP Server
        TCPServer tcpServer = new TCPServer(TCP_PORT, mainQueue);
        // Main UDP Server
        UDPServer udpServer = new UDPServer(UDP_PORT, mainQueue);
        // Main System Worker
        MainWorker worker = new MainWorker(mainVehicle, mainQueue, tcpServer, udpServer);
        
        // Start Threads
        worker.start();
        tcpServer.start();
        udpServer.start();
    }
}
