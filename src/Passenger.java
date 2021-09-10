
public class Passenger 
{
    private String name;
    private String passportNum;
    private String seat;

    // constructor for Passenger object
    public Passenger(String name, String passportNum, String seat)
    {
        this.name = name;
        this.passportNum = passportNum;
        this.seat = seat;
    }

    //getter and setter methods for each variable
    public String getName()
	{
		return name;
	}
    public void setName(String name)
	{
		this.name = name;
	}
    public String getPassportNum()
    {
        return passportNum;
    }
    public void setPassportNum(String passportNum)
    {
        this.passportNum = passportNum; 
    }
    public String getSeat()
    {
        return seat;
    }
    public void setSeat(String seat)
    {
        this.seat = seat;
    }

    // method that compares two passenger objects by name and passport number
    // return true of they are the same and false otherwise
    // inherited from superclass Object
    public boolean equals(Object object)  
    {
        Passenger otherPass = (Passenger) object;
        if (this.getName().equals(otherPass.getName()) && this.getPassportNum().equals(otherPass.getPassportNum()))
        {
            return true;
        }
        return false;
    }
    
    //method prints passenger information
	public void print()
	{
		System.out.println("Name: " + name + "\t Passport Number: " + passportNum + "\t Seat Number: " + seat);
	}
}