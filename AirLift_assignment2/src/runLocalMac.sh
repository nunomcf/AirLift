#!/bin/bash

echo "Compiling..............."
javac -d . -encoding UTF-8 */*.java

echo "Executing all the shared regions and entities..............."
ttab 'java main.RepositoryMain' 
ttab 'java main.DepartureAirportMain'
ttab 'java main.DestinationAirportMain' 
ttab 'java main.PlaneMain'


echo "Launching hostess..."
ttab 'java main.HostessMain'
echo "Launching pilot..."
ttab 'java main.PilotMain'
echo "Launching passengers..."
ttab 'java main.PassengerMain 0'
ttab 'java main.PassengerMain 1'
ttab 'java main.PassengerMain 2' 
ttab 'java main.PassengerMain 3'
ttab 'java main.PassengerMain 4'
ttab 'java main.PassengerMain 5' 
ttab 'java main.PassengerMain 6'
ttab 'java main.PassengerMain 7'
ttab 'java main.PassengerMain 8'
ttab 'java main.PassengerMain 9' 
ttab 'java main.PassengerMain 10'
ttab 'java main.PassengerMain 11'
ttab 'java main.PassengerMain 12'
ttab 'java main.PassengerMain 13'
ttab 'java main.PassengerMain 14'
ttab 'java main.PassengerMain 15' 
ttab 'java main.PassengerMain 16' 
ttab 'java main.PassengerMain 17' 
ttab 'java main.PassengerMain 18' 
ttab 'java main.PassengerMain 19' 
ttab 'java main.PassengerMain 20' 

