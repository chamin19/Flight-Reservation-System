
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

// class models a flight with economy seats
public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	String flightNum;
	String airline;
	String origin, dest;
	String departureTime;
	Status status; 
	FlightType flightType;
	int flightDuration;
	Aircraft aircraft;
	protected int passengers; 
	protected ArrayList<Passenger> manifest = new ArrayList<Passenger>();
	protected TreeMap<String, Passenger> seatMap = new TreeMap<String, Passenger>();
	
  
	// initialize instance variables to default value
	public Flight()
	{
		flightNum = "";
		airline = "";
		origin = "";
		dest = "";
		departureTime = "";
		status = null;
		flightDuration = 0;
		aircraft = null;
		passengers = 0;
	}
	
	// constructor for Flight object
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		passengers = 0;
		status = Status.ONTIME;
		flightType = FlightType.MEDIUMHAUL;
	}

	// getter and setter methods for each variable
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	public int getPassengers()
	{
		return passengers;
	}
	public void setPassengers(int passengers)
	{
		this.passengers = passengers;
	}
	public FlightType getFlightType()
	{
		return flightType;
	}
	public void setFlightType(FlightType flightType)
	{
		this.flightType = flightType;
	}
	
	// Method returns true if seat is available
	// compares values in seatMap to seat
	public boolean seatAvailable(String seat)
	{
		for (Passenger p : seatMap.values())
		{
			if (p.getSeat().equals(seat)) {return false;}
		}
		return true;
	}
	
	// Method returns true if the seat exists in the flight
	// checks for match in initialiSeatLayout array in class Aircraft
	public boolean seatExists(String seat)
	{
		aircraft.initialSeatLayout();		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < aircraft.initialSeatLayout[i].length; j++) {
				if (aircraft.initialSeatLayout[i][j].equals(seat)) return true;
			}
		}
		return false;
	}

	// returns true if seat is economy
	public boolean isEconomySeat(String seat)
	{
		char c = seat.charAt(seat.length()-1);
		if (c == '+') {return false;}
		return true;
	}

	// method cancels seat by reducing passenger count if current number of 
	// passengers is greater than 0
	// removes the passenger from manifest and the seatMap
	// iterates over the original seatLayout array to revert "XX" to the original seat
	public void cancelSeat(Passenger p)
	{
		if (this.passengers > 0)
		{
			String seat = p.getSeat();
			this.passengers -= 1;
			manifest.remove(p);
			seatMap.remove(seat,p);
			//aircraft.emptySeat(p);
		}
	}
	
	// reserves a seat on a flight by increasing passenger count by 1 if seats are available
	// adds the passenger to manifest and the seatMap
	public void reserveSeat(Passenger p, String seat)
	{
		if (seatAvailable(seat))
		{
			if (this.passengers == 0) {aircraft.initialSeatLayout();}
			this.passengers += 1; 			
			manifest.add(p);
			seatMap.put(seat,p);
		}
	}
	
	//method returns true if two passenger objects are equal
	public Boolean passDup(Passenger p)
	{
		for (Passenger pass : manifest)
		{
			if (pass.equals(p)) {return true;}
		}
		return false;
	}

	// prints the modified 2D array seatLayout 
	// calls methos initialiSeatLayout() to initialize seat layout if passenger count is 0
	// iterates over seats in updated seat layout
	public void printSeats()
	{
		//iterate over map to print all passengers 
		if (this.passengers == 0) {aircraft.initialSeatLayout();}
		for (String[] x : aircraft.getSeatLayout(seatMap))
        {
            for (String y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
	}

	// prints all passengers in manifest 
	public void printPassengerManifest()
	{
		for (Passenger p : manifest) {p.print();}
	}

	//randomizes enum flight status
	public Status randomStatus()  {
		int ran = new Random().nextInt(Status.values().length);
		return Status.values()[ran];
    }

	//to String method returns flight info
	public String toString()
	{
		return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}
}

// customized exceptions
// thrown in FlightManager and caught in FlightReservationSystem
class DuplicatePassengerException extends RuntimeException
{
	public DuplicatePassengerException(){};
	public DuplicatePassengerException(String err) {super(err);}
}

class PassengerNotInManifestException extends RuntimeException
{
	public PassengerNotInManifestException(){};
	public PassengerNotInManifestException(String err) {super(err);}
}

class SeatOccupiedException extends RuntimeException
{
	public SeatOccupiedException(){};
	public SeatOccupiedException(String err) {super(err);}
}

class FlightNotFoundException extends RuntimeException
{
	public FlightNotFoundException(){};
	public FlightNotFoundException(String err) {super(err);}
}

class SeatNotFoundException extends RuntimeException
{
	public SeatNotFoundException(){};
	public SeatNotFoundException(String err) {super(err);}
}