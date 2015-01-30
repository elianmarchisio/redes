package system;

import network.TCPServer;


public class main {
    private static TCPServer server;
    
    public static void main(String argv[]){
         server = new TCPServer(4401);
         server.run();
    }
}
