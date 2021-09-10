
public class Reservation
{
	String flightNum;
	String flightInfo;
	boolean firstClass;
	String passengerName;
	String passengerPassport;
	String seat;
	
	// constructor method for Reservation object
    // initializes instance variables
	public Reservation(String flightNum, String info, String name, String passport, String seat)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.firstClass = false;
		this.passengerName = name;
		this.passengerPassport = passport;
		this.seat = seat;
	}
	
	// returns true if reservation is for a first class seat
	public boolean isFirstClass()
	{
		return firstClass;
	}

	//getter and setter methods for each variable
	public void setFirstClass()
	{
		this.firstClass = true;
	}
	public String getFlightNum()
	{
		return flightNum;
	}
	public String getFlightInfo()
	{
		return flightInfo;
	}
	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}
	public String getSeat()
	{
		return seat;
	}
	public void setSeat(String seat)
	{
		this.seat = seat;
	}
	public String getName()
	{
		return passengerName;
	}
	public String getPassport()
	{
		return passengerPassport;
	}
	//method prints flight information
	public void print()
	{
		System.out.println(flightInfo + "\t" + passengerName + " " + passengerPassport + " " + seat);
	}

	public boolean equals(Object other)
	{
		Reservation otherPass = (Reservation) other;
        if (this.getFlightNum().equals(otherPass.getFlightNum()) && this.passengerName.equals(otherPass.passengerName)  && this.passengerPassport.equals(otherPass.passengerPassport))
        {
            return true;
        }
        return false;
	}
}