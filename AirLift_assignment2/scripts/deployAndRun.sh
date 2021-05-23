cd $(dirname $0)/../src
javac -d . -encoding UTF-8 */*.java
cd ..
rm -f  dirAirLift.zip
zip -rq dirAirLift.zip src
mv dirAirLift.zip scripts
rm -f dirAirLift.zip
cd scripts
xterm  -T "General Repository" -hold -e "./RepositoryDeployAndRun.sh" &
xterm  -T "Departure Airport" -hold -e "./DepartureAirportDeployAndRun.sh" &
xterm  -T "Destination Airport" -hold -e "./DestinationAirportDeployAndRun.sh" &
xterm  -T "Plane" -hold -e "./PlaneDeployAndRun.sh" &
sleep 1
xterm  -T "Pilot" -hold -e "./PilotDeployAndRun.sh" &
xterm  -T "Hostess" -hold -e "./HostessDeployAndRun.sh" &
xterm  -T "Passengers" -hold -e "./PassengersDeployAndRun.sh" &
