package components;

/**
 * Message Class
 * Implements messages from clients and peers
 * split input string by spaces
 */
public class Message {
    // Message Types
    public enum type {client, peer}
    // Current Message Data
    private String message;
    private final type ctype;
    private final int port;
    private final String ip;
    
    /**
     * Create new Message (Client Message)
     * @param nMessage Message String
     * @param ntype Message Type
     * @param nIP Message Source IP
     * @param nPort Message Source Port
     */
    public Message(String nMessage, type ntype, String nIP, int nPort) {
        ctype = ntype;
        message = nMessage;
        ip = nIP;
        port = nPort;
    }
       
    /**
     * Get Message String
     * @return message string
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Set Message String
     * @param nMessage new message
     */
    public void setMessage(String nMessage) {
        message = nMessage;
    }
    
    public String getIP() {
        return ip;
    }
    
    public int getPort() {
        return port;
    }
    
    /**
     * Checks if current Message is from a client
     * @return true (Client), false (Peer)
     */
    public boolean isClient() {
        return ctype == type.client;
    }
    
    /**
     * Checks if current Message is from a peer
     * @return true (Peer), false (Client)
     */
    public boolean isPeer() {
        return ctype == type.peer;
    }
}