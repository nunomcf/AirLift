#!/bin/bash


#mappingf=$(dirname $0)/../mapping
#publicc=$(grep Public $mappingf | sed s/Public\s*//g)
#rmiflags="-ea 
#         -Djava.rmi.server.codebase=\"http://$publicc.ua.pt/sd0103/classes/\" \
#         -Djava.rmi.server.useCodebaseOnly=true \
#         -Djava.security.policy=java.policy"


cd $(dirname $0)/..

for i in $(seq 1 30); do
	java $rmiflags sd.p1g3.assignment3.mainProgram.CustomerMain $i &
done

sleep 1

for i in $(seq 0 1); do
	java $rmiflags sd.p1g3.assignment3.mainProgram.MechanicMain $i &
done

java $rmiflags sd.p1g3.assignment3.mainProgram.ManagerMain &
