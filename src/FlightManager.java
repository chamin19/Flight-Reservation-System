
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FlightManager
{
  // consists of flights departing from Toronto
  TreeMap<String, Flight> flightMap = new TreeMap<String,Flight>();
  
  //Array containing flight destinations
  String[] cities = 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  int[] flightTimes = { 3, // Dallas
  											1, // New York
  											7, // London
  											8, // Paris
  											16 // Tokyo
  										};
  
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  

  Random random = new Random(); 
  
  public FlightManager() throws FileNotFoundException, IOException, NumberFormatException
  {
  	// Create some aircraft types with max seat capacities
  	airplanes.add(new Aircraft(84, "Boeing 737"));
  	airplanes.add(new Aircraft(36,"Airbus 320"));
  	airplanes.add(new Aircraft(24, "Dash-8 100"));
  	airplanes.add(new Aircraft(48, "Bombardier 5000"));
  	airplanes.add(new Aircraft(100, 16, "Boeing 747"));
    Collections.sort(airplanes);

    File flightInfo = new File("flights.txt");
    if (!flightInfo.exists()) {throw new FileNotFoundException("Could not locate file flights.txt");}
    if (flightInfo.length() == 0) {throw new IOException("File is empty");}
    Scanner in = new Scanner(flightInfo);
    Scanner inputLine;
    String airline;
    String flightNum;
    String dest;
    String dep;
    int passCap;
    Flight flight;
    int i = 1; //keeps track of line in flights.txt that has an error
    
    while (in.hasNextLine())
    {
      String line = in.nextLine();
      inputLine = new Scanner(line);

      String[] words = line.split("\\s+");
      // if flight in flights.txt is missing information, throw IOException
      if (words.length < 4) {throw new IOException("Incomplete flight information" + " (" + flightInfo + ":" + i + ")");}
      // if flight in flights.txt has extra information, throw IOException
      if (words.length > 4) {throw new IOException("Improper flight information format" + " (" + flightInfo + ":" + i + ")");}
      airline = inputLine.next();
      airline = airline.replace("_"," "); 
      flightNum = generateFlightNumber(airline);
      dest = inputLine.next().replace("_"," ");
      // throws exception if destination is not available 
      if (!inArray(dest)) {throw new IOException("Destination " + dest + " unavailable " + "(" + flightInfo + ":" + i + ")");}
      dep = inputLine.next();
      //throws exception if capacity in flights.txt is not an integer
      try {passCap = Integer.parseInt(inputLine.next());}
      catch (NumberFormatException e) {throw new NumberFormatException("Seat capacity must be an integer" + " (" + flightInfo + ":" + i + ")");}
      if (dest.equals("Tokyo")) {flight = new LongHaulFlight(flightNum, airline, dest, dep, flightTimes[destIndex(dest)], airplanes.get(airIndex(passCap)));}
      else {flight = new Flight(flightNum, airline, dest, dep, flightTimes[destIndex(dest)], airplanes.get(airIndex(passCap)));}
      flightMap.put(flightNum, flight);
      flight.setStatus(flight.randomStatus());
      i++; 
    } 
  }

  //determines if the city in flights.txt is in the cities array
  private Boolean inArray(String d)
  {
    for (String city : cities) {if (d.equals(city)) return true;}
    return false;
  }

  // private method that determines the index of the destination in flightTimes array 
  private int destIndex(String dest)
  {
    int index = 0;
    for (int i = 0 ; i < cities.length ; i++) {
      if (cities[i].equals(dest)) {
        index = i;
        break;
      }
    }
    return index;
  }

  // private method that determines the approriate aircraft given the required capacity from flights.txt
  // returns the index of the aircraft for the aircraft airplanes ArrayList
  private int airIndex(int capacity)
  {
    int index = -1;
    for (int i = 0; i < airplanes.size()-1 ; i++)
    {
      int craftACap = airplanes.get(i).getNumSeats();
      int craftBCap = airplanes.get(i+1).getNumSeats();
      if (craftACap >= capacity) 
      {
        index = i;
        break;
      }
      else if (craftACap < capacity && capacity <= craftBCap)
      {
        index = i+1;
        break;
      }
    }
    return index;
  }

  // private method that generates and returns random flight number between 101 and 300 given airline name parameter
  private String generateFlightNumber(String airline) 
  {
    String flightNumber = "";
    int randomNum = random.nextInt(301-101)+101;  
    String [] words = airline.split(" ", 2); 
    for (String word : words) 
    {
      char letter = word.charAt(0);
      flightNumber += letter; 
    }
    flightNumber += randomNum; 
  	return flightNumber; 
  }

  // Prints all flights in Map
  // iterates over keys in TreeMap 
  public void printAllFlights()
  {
  	for (String key : flightMap.keySet())
  	{
  		System.out.println(flightMap.get(key).toString());
  	}
  }
  
  // Given flight number and seat type, reserve a seat on a flight
  // For first class reservation: calls reserveSeat method from class LongHaulFlight
  // For economy class reservation: calls reserveSeat method from class Flight
  // Creates and returns a new Reservation object if flight found and seats available  
  // otherwise throws appropriate exception
  public Reservation reserveSeatOnFlight(String flightNum, Passenger p) throws FlightNotFoundException, SeatOccupiedException, DuplicatePassengerException, SeatNotFoundException
  {
    boolean flightFound = false;
    if (flightMap.containsKey(flightNum)) {flightFound = true;}
    Flight flight = flightMap.get(flightNum);
    if (flightFound) //if flight is found in flightMap
    {
      if (flight.passDup(p)==false) //no dup passenger
      {
        if (flight.seatExists(p.getSeat())) //seat is not on flight
        {
          if (flight.isEconomySeat(p.getSeat()) == false) 
          {
            LongHaulFlight lhflight = LongHaulFlight.class.cast(flight);
            if (lhflight.seatAvailable(p.getSeat())) 
            {
              lhflight.reserveSeat(p, p.getSeat());
              Reservation res = new Reservation(flightNum, flight.toString(), p.getName(), p.getPassportNum(), p.getSeat());
              String fcFlightInfo = res.getFlightInfo() + " FCL";
              res.setFlightInfo(fcFlightInfo);
              res.setFirstClass();   
              return res;
            }  

            else //seat occupied
            {
              throw new SeatOccupiedException("Seat " + p.getSeat() + " is occupied");
            }
          }
          else //must be economy
          {
            if (flight.seatAvailable(p.getSeat()))  
            {
              flight.reserveSeat(p, p.getSeat());
              Reservation res = new Reservation(flightNum, flight.toString(), p.getName(), p.getPassportNum(), p.getSeat()); 
              return res;
            }
            else //seat occupied
            {
              throw new SeatOccupiedException("Seat " + p.getSeat() + " is occupied");
            }
          }
        }
        else //seat not in flight
        {
          throw new SeatNotFoundException("Seat " + p.getSeat() + " does not exist on this flight");
        }
      }
      else //dup passenger
      {
        throw new DuplicatePassengerException("Duplicate passenger " + p.getName() + " " + p.getPassportNum());
      }
    }
    else //flight not found
    {
      throw new FlightNotFoundException("Flight " + flightNum + " not found");
    }
  }  
    
  // Given a Reservation object, cancel a first class or economy seat on the flight
  // For first class reservation: calls cancelSeat method from class LongHaulFlight
  // For economy class reservation: calls cancelSeat method from class Flight 
  // Calls passDup() and isEconomySeat() methods from Flight to find duplicate 
  // passengers/ determine if seat is first class and economy
  // Otherwise throws exception 
  public void cancelReservation(Reservation res, Passenger p) throws FlightNotFoundException, PassengerNotInManifestException
  {
    boolean flightFound = false;
    if (flightMap.containsKey(res.getFlightNum())) {flightFound = true;}
    Flight flight = flightMap.get(res.flightNum);
    boolean passFound = false;

    if (flightFound)
    {
      if (flight.passDup(p)) {passFound = true;}
      if (passFound)
      {
        if (!flight.isEconomySeat(res.getSeat())) 
        {
          LongHaulFlight lhflight = LongHaulFlight.class.cast(flight);
          lhflight.cancelSeat(p);
        }
        else //must be economy
        {
          flight.cancelSeat(p);
        } 
      }
      else //passenger not found in manifest
      {
        throw new PassengerNotInManifestException("Passenger not on flight " + res.getFlightNum());
      }
    }
    else //flight not found
    {
      throw new FlightNotFoundException("Flight " + res.getFlightNum() + " not found");
    }
  }

  //Print each passenger's name, passport number, and seat number in the flight given the flightNum
  public void printPassengers(String flightNum) throws FlightNotFoundException
  {
    boolean flightFound = false;
    if (flightMap.containsKey(flightNum)) {flightFound = true;}
    Flight flight = flightMap.get(flightNum);
    if (flightFound) {flight.printPassengerManifest();}
    else {throw new FlightNotFoundException("Flight " + flightNum + " not found");}
  }

  // print all seats in seatLayout 2D array from Class Flight
  // searches for matching ket from TreeMap and calls printSeats() method from class Flight
  // Otherwise throw FlightNotFoundException
  public void printSeatLayout(String flightNum) throws FlightNotFoundException
  {
    boolean flightFound = false;
    if (flightMap.containsKey(flightNum)) {flightFound = true;}
    Flight flight = flightMap.get(flightNum);
    
    if (flightFound) {flight.printSeats();}
    else {throw new FlightNotFoundException("Flight " + flightNum + " not found");}  
  }

}