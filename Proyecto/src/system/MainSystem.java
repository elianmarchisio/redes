package system;

import components.SystemQueue;
import components.Vehicle;
import network.TCPServer;


public class MainSystem {
    static int MAX_SEATS = 50;
    static int TCP_PORT = 4401;
    
    /**
     * 
     * @param argv 
     */
    public static void main(String argv[]){
        // Clients Request and peers messaje queue
        SystemQueue mainQueue = new SystemQueue();
        // Main Vehicle data
        Vehicle mainVehicle = new Vehicle(MAX_SEATS);
        // Client TCP Server
        TCPServer server = new TCPServer(TCP_PORT, mainQueue);
        // Main System Worker
        MainWorker worker = new MainWorker(mainVehicle, mainQueue, server);
        
        // Start Threads
        worker.start();
        server.start();
    }
}
