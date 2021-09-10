
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		try 
		{
			FlightManager manager;
			manager = new FlightManager();

			ArrayList<Reservation> myReservations = new ArrayList<Reservation>();
			Scanner scanner = new Scanner(System.in);
			System.out.print(">");

			while (scanner.hasNextLine())
			{
				String inputLine = scanner.nextLine();
				if (inputLine == null || inputLine.equals("")) continue;

				Scanner commandLine = new Scanner(inputLine);
				
				String action = commandLine.next();

				if (action == null || action.equals("")) continue;

				if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				// Lists all departing flights
				else if (action.equalsIgnoreCase("LIST"))
				{
					manager.printAllFlights(); 
				}
				
				// Reserves an economy flight given flight number 
				// Calls reserveSeatOnFlight method from Flight Manager
				// Passess in passenger object and flightNum
				// Adds reservation to arrayList myReservations if method is not null
				// Otherwise prints appropriate exception
				else if (action.equalsIgnoreCase("RES"))
				{
					String flightNum = null;
					String name = null;
					String passportNum = null;
					String seat = null;

					if (commandLine.hasNext())
					{
						flightNum = commandLine.next();
						if (commandLine.hasNext())
						{
							name = commandLine.next();	
							if (commandLine.hasNext())
							{
								passportNum = commandLine.next();
								if (commandLine.hasNext())
								{
									seat = commandLine.next();
									Passenger p = new Passenger(name, passportNum, seat);								
									try 
									{
										Reservation res = manager.reserveSeatOnFlight(flightNum, p);
										myReservations.add(res);
										res.print();
									}
									catch (FlightNotFoundException | SeatOccupiedException | DuplicatePassengerException | SeatNotFoundException e)
									{
										System.out.println(e.getMessage());
									}
								}
							}
						}
					}
				}
				
				// Displays all passengers for a specified flight and their seat/passport number 
				// Calls printPassengers method from Flight Manager, passing in flight number
				// Otherwise catches exception thrown in Flight Manager
				else if (action.equalsIgnoreCase("PASMAN"))
				{
					String flightNum = null;
					if (commandLine.hasNext())
					{
						flightNum = commandLine.next();
						try {manager.printPassengers(flightNum);}
						catch (FlightNotFoundException e)
						{
							System.out.println(e.getMessage());
						}
					}
				}
				
				// Checks flight to see if seats are available
				// If not, calls catches exception thrown in Flight Manager
				else if (action.equalsIgnoreCase("SEATS"))
				{
					String flightNum = null;
					if (commandLine.hasNext())
					{
						flightNum = commandLine.next();
						try {manager.printSeatLayout(flightNum);}
						catch (FlightNotFoundException e)
						{
							System.out.println(e.getMessage());
						}
					}	
				}
				
				// Cancels a reservation for the flightnum if it exists in myReservations
				// Calls cancelReservation from Flight Manager and removes it from the ArrayList
				// If not, calls catches exception thrown in Flight Manager
				else if (action.equalsIgnoreCase("CANCEL"))
				{
					String flightNum = null;
					String name = null;
					String passportNum = null;
					String seat = null;
					if (commandLine.hasNext())
					{
						flightNum = commandLine.next();
						if (commandLine.hasNext())
						{
							name = commandLine.next();	
							if (commandLine.hasNext())
							{
								passportNum = commandLine.next();
								try
								{
									Reservation res = new Reservation(flightNum, "-", name, passportNum, "-");
									for (Reservation res2 : myReservations) {
										if (res.equals(res2)) 	{
											seat = res2.getSeat();
											break;
										}
									}
									res.setSeat(seat);
									Passenger p = new Passenger(name, passportNum, seat);
									manager.cancelReservation(res,p);
									myReservations.remove(res);
								}
								catch (FlightNotFoundException | PassengerNotInManifestException e)
								{
									System.out.println(e.getMessage());
								}
							}
						}
					}
				}

				// Print all the reservations in ArrayList myReservations
				else if (action.equalsIgnoreCase("MYRES"))
				{
					for (int i = 0; i < myReservations.size(); i++)
					{
						myReservations.get(i).print();
					}
				}
				System.out.print("\n>");
			}
		} 
		//exception thrown in Flight Manager are caught here
		catch (FileNotFoundException e) {System.out.println(e.getMessage());} 
		catch (IOException e) {System.out.println(e.getMessage());}
		catch (NumberFormatException e) {System.out.println(e.getMessage());}
	}
}