echo "Transfering data to the Passenger node."
sshpass -f password ssh sd305@l040101-ws03.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd305@l040101-ws03.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirAirLift.zip sd305@l040101-ws03.ua.pt:test/AirLift
echo "Decompressing data sent to the Passenger node."
sshpass -f password ssh sd305@l040101-ws03.ua.pt 'cd test/AirLift ; unzip -uq dirAirLift.zip'
echo "Executing program at the Passenger node."
sshpass -f password ssh sd305@l040101-ws03.ua.pt 'cd test/AirLift/src/ ; java main.PassengerMain'