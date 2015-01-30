package vehicle;

/**
 * Class Vehicle
 * Implements 
 */
public class Vehicle {
    
    private int seats = 50;
    private int reserved;
	
    public Vehicle(){
        reserved = 0;
    }
    
    /**
     * Returns the number of available seats in the vehicle
     */
    public int available(){
	return seats - reserved;
    }
    
    /**
     * Returns true iff could reserve n seats in the vehicle.
     * Otherwise returns false
     */
    public boolean reserve(int n){
	if (n <= available()){
            reserved += n;
            return true;
	}
	return false;
    }

    /*
    * Returns true iff could cancel n reservations in the vehicle.
    * Otherwise returns false
    */
    public boolean cancel(int n){
        if (n <= reserved){
            reserved = reserved - n;
            return true;
	}
	return false;
    }
}
