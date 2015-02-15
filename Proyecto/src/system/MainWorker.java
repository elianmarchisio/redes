package system;

import components.Message;
import components.SystemQueue;
import components.Vehicle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.TCPServer;

/**
 * MainWorker Class, Implements System worker
 * Process request from SystemQueue
 * Send response to TCP Clients and UDP Peers
 */
public class MainWorker extends Thread {
    // Data    
    private final SystemQueue queue;
    private final Vehicle vehicle;
    // Network
    private final TCPServer tcpServer;
    
    /**
     * Main Worker Constructor
     * @param mainVehicle System Vehicle
     * @param mainQueue Message queue to process
     * @param tcpSrv Main tcpServer
     */
    public MainWorker(Vehicle mainVehicle, SystemQueue mainQueue, TCPServer tcpSrv) {
        vehicle = mainVehicle;
        queue = mainQueue;
        tcpServer = tcpSrv;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                // Get queue head message
                Message message = queue.poll();
                // Process message as a client or peer message
                if(message.isClient())
                    processClientMessage(message);
                else
                    processPeerMessage(message);
            } catch (InterruptedException | IOException ex) {
                System.err.println("MainWorker network error or interruped exception");
                Logger.getLogger(MainWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void processClientMessage(Message message) throws IOException {
        String command = message.getMessage()[0];
        String response = null;
        // New Client Connected
        if(command.equals("CONNECTED"))
            response = "      Welcome!!\n"
                     + ":: Usage infomation ::\n"
                     + "Available: display number of available seats\n"
                     + "Reserve n: reserve N seats\n"
                     + "Cancel n: cancel N reserved seats\n"
                     + "End: finalize session\n"
                     + "Commands aren't case sensitive\n";
        // Request End, finalize client message processing and close connection
        if(command.equals("END")){
            tcpServer.sendData(">> Session ends, Closing connection\n");
            tcpServer.closeConnection();
            return;
        }
        // Request Available Seats
        if(command.equals("AVAILABLE"))
            response = ">> Number of Available Seats: " + vehicle.available() + '\n';
        // Request Reserver N Seats
        if(command.equals("RESERVE")){
            // Check valid command reserve and number of seats
            if(message.messageSize() < 2)
                response = ">> Invalid number of seats to reserve\n";
            else
                try {
                    // Get integer argument
                    int seats = Integer.parseInt(message.getMessage()[1]);
                    // Try to reserve seats
                    if(vehicle.reserve(seats))
                        response = ">> Reserved " + seats + " seats\n";
                    else
                        response = ">> Can't reserv " + seats + " seats\n";
                } catch (NumberFormatException nfe) {
                    response = ">> Insert Numeric Value :: ie. reserve 5\n";
                }
        }
        // Request Cancel N Seats
        if(command.equals("CANCEL")){
            // Check valid command cancel and number of seats
            if(message.messageSize() < 2)
                response = ">> Invalid number of seats to cancel\n";
            else
                try {
                    // Get integer argument
                    int seats = Integer.parseInt(message.getMessage()[1]);
                    // Try to reserve seats
                    if(vehicle.cancel(seats))
                        response = ">> Canceled " + seats + " seats\n";
                    else
                        response = ">> Can't cancel " + seats + " seats\n";
                } catch (NumberFormatException nfe) {
                    response = ">> Insert Numeric Value :: ie. cancel 10\n";
                } 
        }        
        // Invalid Client Request
        if(response == null)
            response = ">> Invalid Command/Request\n";
        // Send Response
        tcpServer.sendData(response);
    }

    private void processPeerMessage(Message message) throws IOException {
        
        tcpServer.sendData(message.getMessage()[0].toUpperCase() + '\n');
    }

}
