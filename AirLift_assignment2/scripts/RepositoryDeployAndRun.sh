echo "Transfering data to the Repository node."
sshpass -f password ssh sd305@l040101-ws07.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd305@l040101-ws07.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirAirLift.zip sd305@l040101-ws07.ua.pt:test/AirLift
echo "Decompressing data sent to the Repository node."
sshpass -f password ssh sd305@l040101-ws07.ua.pt 'cd test/AirLift ; unzip -uq dirAirLift.zip'
echo "Executing program at the Repository node."
sshpass -f password ssh sd305@l040101-ws07.ua.pt 'cd test/AirLift/src/ ; java main.RepositoryMain'