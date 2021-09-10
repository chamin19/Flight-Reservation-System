import java.util.TreeMap;

//class implements Comparable Interface
public class Aircraft implements Comparable<Aircraft>
{
	int numEconomySeats;
	int numFirstClassSeats;
	String model;
	String[][] seatLayout;
	String[][] initialSeatLayout;
	
    // constructor for Aircraft object with economy seats
	// initializes instance variables for aircraft
	public Aircraft(int seats, String model)
	{
		this.numEconomySeats = seats;
		this.numFirstClassSeats = 0;
		this.model = model;
	}
	
	// constructor for Aircraft object with economy and first class seats
	// initializes instance variables for aircraft 
	public Aircraft(int economy, int firstClass, String model)
	{
		this.numEconomySeats = economy;
		this.numFirstClassSeats = firstClass;
		this.model = model;
	}
  
	// returns number of economy seats on aircraft 
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	// returns total number of seats on aircraft
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	// returns number of first class seats on aircraft
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	// getter and setter methods for aircraft model
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	
	//method prints flight information
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

	// compareTo method that implements the Comparable interface
	// compares two Aircraft objects based on number of economy or first class seats
	public int compareTo(Aircraft other)
	{
		if (this.getNumSeats() == other.getNumSeats()) 
		{
			if (this.getNumFirstClassSeats() > other.getNumFirstClassSeats()) {return 1;}
			if (this.getNumFirstClassSeats() < other.getNumFirstClassSeats()) {return -1;}
			return 0;
		}
		else if (this.getNumSeats() > other.getNumSeats()) {return 1;}
		return -1;
	}

	// initializes array based on number of first and economy seats
	public void initialSeatLayout()
	{
		int numRows = getTotalSeats() / 4;
		int numEconRows = getNumSeats() / 4;
		int numFirstClassRows = getNumFirstClassSeats() / 4;
		seatLayout = new String[5][numRows];
 
		for (int i = 0 ; i < numFirstClassRows; i++)
		{
			seatLayout[0][i] = i+1 + "A+"; 
			seatLayout[1][i] = i+1 + "B+"; 
			seatLayout[2][i] = "  "; 
			seatLayout[3][i] = i+1 + "C+"; 
			seatLayout[4][i] = i+1 + "D+"; 
		}

		for (int j = numFirstClassRows ; j < numEconRows + numFirstClassRows; j++)
		{
			seatLayout[0][j] = j+1 + "A"; 
			seatLayout[1][j] = j+1 + "B"; 
			seatLayout[2][j] = "  "; 
			seatLayout[3][j] = j+1 + "C"; 
			seatLayout[4][j] = j+1 + "D";
			
		}
		initialSeatLayout = seatLayout.clone();
	}

	// updates the seat layout to appear as occupied when printed 
	// depending on length of seat string
	public void fillSeat(String seat)
	{
		for (int i = 0; i < 5; i++ ) {
			for ( int j = 0; j < initialSeatLayout[i].length; j++ ) {
				if (initialSeatLayout[i][j].equals(seat)) {
					if (seat.length() == 2) {seatLayout[i][j] = "XX";}
					if (seat.length() == 3) {seatLayout[i][j] = "XXX";}
					if (seat.length() == 4) {seatLayout[i][j] = "XXXX";}
					break;
				} 
			}
		}
	}

	// calls initialSeatLayout() and iterates over each reservation in map
	// fills seat as necessary
	public String[][] getSeatLayout(TreeMap<String,Passenger> map)
	{
		initialSeatLayout();
		for (Passenger p : map.values())
		{
			fillSeat(p.getSeat());
		}
		return seatLayout;
	}
}