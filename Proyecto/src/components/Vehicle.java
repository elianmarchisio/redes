package components;

/**
 * Class Vehicle
 * Implements bus seats magnament
 */
public class Vehicle {
    // Data
    private final int seats;
    private int reserved;
	
    /**
     * Vehicle constructor
     * @param nSeats number of seats in the vehicle
     */
    public Vehicle(int nSeats) {
        reserved = 0;
        seats = nSeats;
    }
    
    /**
     * Returns the number of available seats in the vehicle
     * @return integer
     */
    public int available() {
	return seats - reserved;
    }
    
    /**
     * Returns true iff could reserve n seats in the vehicle.
     * Otherwise returns false
     * @param n number of seat to reserve
     * @return boolean true succes of false failure
     */
    public boolean reserve(int n){
	if (n <= available() && n > 0){
            reserved += n;
            return true;
	}
	return false;
    }

    /**
     * Returns true iff could cancel n reservations in the vehicle.
     * Otherwise returns false
     * @param n number of seat to cancel reserve
     * @return boolean true succes of false failure
     */
    public boolean cancel(int n){
        if (n <= reserved && n > 0){
            reserved = reserved - n;
            return true;
	}
	return false;
    }
}