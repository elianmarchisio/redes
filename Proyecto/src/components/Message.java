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
    private final String[] message;
    private final type ctype;
    
    /**
     * Create new Message
     * @param nMessage Message String
     * @param nType Message Type
     */
    public Message(String nMessage, type nType) {
        ctype = nType;
        message = nMessage.split("\\s+");
    }
    
    /**
     * Get Message String array (splitted input String)
     * @return string array
     */
    public String[] getMessage() {
        return message;
    }
    
    /**
     * Checks if current Message is from a client
     * @return true (Client), false (Peer)
     */
    public boolean isClient() {
        return ctype == type.client;
    }
    
    /**
     * Get Message Array Size
     * @return integer lenght
     */
    public int messageSize() {
        return message.length;
    }
}