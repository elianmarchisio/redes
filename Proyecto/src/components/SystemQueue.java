package components;

import java.util.ArrayList;

/**
 * System Queue Class
 * Implements Synchronized blocking queue
 */
public class SystemQueue {
    // Data
    private final ArrayList<Message> list;
    
    /**
     * Create Main System Queue
     */
    public SystemQueue() {
        list = new ArrayList<>();
    }
    
    /**
     * Inserts the specified element at the tail of this queue
     * @param message Message to enqueue
     */
    public synchronized void offer(Message message) {
        // Add element to queue
        list.add(message);
        // Notify new element available
        notifyAll();
    }
    
    /**
     * Retrieves and removes the head of this queue
     * @return String element
     * @throws InterruptedException 
     */
    public synchronized Message poll() throws InterruptedException {
        // Wait for a element in the queue
        while(list.isEmpty()) wait();
        // Get Element and Remove element
        Message result = list.remove(0);
        // Wakeup sleep threads
        notifyAll();
        // Return Element
        return result;
    }
    
    /**
     * Retrieves, but does not remove, the head of this queue
     * @return String element 
     * @throws InterruptedException 
     */
    public synchronized Message peek() throws InterruptedException {
        // Wait for a element in the queue
        while(list.isEmpty()) wait();
        // Get Element
        Message result = list.get(0);
        // Wakeup sleep threads
        notifyAll();
        // Return Element
        return result;
    }
    
    /**
     * Get Queue Size
     * @return integer size
     */
    public synchronized int size() {
        return list.size();
    }
}