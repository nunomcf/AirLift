echo "Transfering data to the hostess node."
sshpass -f password ssh sd305@l040101-ws02.ua.pt 'mkdir -p test/AirLift'
sshpass -f password ssh sd305@l040101-ws02.ua.pt 'rm -rf test/AirLift/*'
sshpass -f password scp dirAirLift.zip sd305@l040101-ws02.ua.pt:test/AirLift
echo "Decompressing data sent to the hostess node."
sshpass -f password ssh sd305@l040101-ws02.ua.pt 'cd test/AirLift ; unzip -uq dirAirLift.zip'
echo "Executing program at the hostess node."
sshpass -f password ssh sd305@l040101-ws02.ua.pt 'cd test/AirLift/src/ ; java main.HostessMain'
