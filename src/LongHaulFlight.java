
// LongHaulFlight is a subclass of the Flight superclass 
public class LongHaulFlight extends Flight
{
	int numFirstClassPassengers;  
	
	//constructor for LongHaulFlight object
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
	 	this.numFirstClassPassengers = 0;
	}

	// method initiliazies instance variables
	public LongHaulFlight()
	{
		flightNum = "";
		airline = "";
		dest = "";
		departureTime = "";   
		flightDuration = 0;
		aircraft = null;    
		numFirstClassPassengers = 0;
	}
	
	public FlightType getFlightType()
	{
		return FlightType.LONGHAUL;
	}

	//Reserves a seat by increasing the number of first class or economy passengers
	//Adds passenger to manifest ArrayList and seatLayout array
	//For economy reservation, calls the inherited method reserveSeat() from Class Flight
	//For first class, calls method for Class Aircraft to compare the capacity to the current number of passengers 
	public void reserveSeat(Passenger p, String seat)
	{
		if (super.seatAvailable(seat) && super.isEconomySeat(seat) == false)
		{
			numFirstClassPassengers += 1;
			if (super.getPassengers() == 0) {aircraft.initialSeatLayout();} 			
			manifest.add(p);
			seatMap.put(seat,p);			
		}

		else if (super.isEconomySeat(seat))
		{
			super.reserveSeat(p, seat);
		}
		
	}
	
	// cancels a seat by decreasing the num of first class or economy seat passengers
	// removes the passenger from manifest ArrayList and the seatMap
	// for economy cancelation, uses the inherited get and set passenger methods from class Flight
	public void cancelSeat(Passenger p)
	{
		String seat = p.getSeat();
		if (super.isEconomySeat(seat) == false && numFirstClassPassengers > 0)
		{
			numFirstClassPassengers -= 1;
			manifest.remove(p);
			seatMap.remove(seat,p);
		}
		else if (super.isEconomySeat(seat) && super.getPassengers() > 0)
		{
			super.cancelSeat(p);
		}
	}
	
	// returns total passenger count of economy and first class passengers
	// overrides inherited method that returns economy passenger count
	public int getPassengerCount() 
	{
		return numFirstClassPassengers + super.getPassengers();
	}

	// toString() method that overriddes the inherited method inherited
	public String toString()  
	{
		return super.toString() + " Long Haul";
	}
}