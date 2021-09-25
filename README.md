# Flight-Reservation-System
This assignment combines topics learned in an introductory course to Java Programming. It utilizes the Java Collection Framework and Object-Oriented Programming principles such as encapsulation, polymorphism, inheritance, and various data structures. The program simulates a Flight Reservation system and allows users to interact through the command line to see available flights, reserve and cancel a seat.

## Commands
* LIST
    * Lists all departing flights. Includes the name of the airline, flight number, destination, departure time, duration, and status.
    * This command must be called prior to booking any reservations. 
     
      ![list](/demo_img/list.jpg)
  
* SEATS
    * Displays the seat arrangment of the specified flight. 
    * Gets updated as reservations are made or canceled. 
    * Occupied seats are displayed as "XX".
    * This command must be called prior to booking any reservations. 
    
      ![seats](/demo_img/seats.jpg)
  
* RES
    * Reserves an economy or long haul flight.
    
      ![res](/demo_img/res.jpg)
  
* CANCEL
    * Cancels a reservation for the flightnum if it exists in the system.
    
      ![cancel](/demo_img/cancel.jpg)
  
* MYRES
    * Prints all reservations in the system. 
    
      ![myres](/demo_img/myres.jpg)
  
* PASMAN
    * Displays all passengers for a specified flight and their seat/passport number.
    
      ![pasman](/demo_img/pasman.jpg)
  
* QUIT
    * Stops the program from running in the command-line. 


Command       | Command-line Input
------------- | ----------------------------------------------------------------------
LIST          | >list
SEATS         | >seats (flight_number)
RES           | >res (flight_number) (passenger_name) (passport_number) (seat_number)
CANCEL        | >cancel (flight_number) (passenger_name) (passport_number)
MYRES         | >myres
PASMAN        | >pasman (flight_number)
QUIT          | >q

## Exceptions
Custom exceptions are thrown in FlightManager.java and caught in FlightReservationSystem.java.
* FlightNotFoundException
    * Occurs when a user attempts to make, cancel, or display reservations, or display seats with an invalid flight number.   
* DuplicatePassengerException
    * Occurs when a user attempts to make two reservations for the same passenger and passport number on the same flight. 
* PassengerNotInManifestException
    * Occurs when a user attemps to cancel a reservation that does not exist. 
* SeatOccupiedException
    * Occurs when a user attempts to reserve an occupied seat. 
* SeatNotFoundException
    * Occurs when a user attempts to make a reservation with an invalid seat number.


