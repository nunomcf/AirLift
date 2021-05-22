#!/bin/bash

srcf=$(dirname $0)/..

mappingf=$srcf/mapping
targetsr=$(grep $(hostname) $mappingf | sed s/$(hostname).*//g)
targetport=$(grep $(hostname) $mappingf | sed s/.*$(hostname)//g)

publicc=$(grep Public $mappingf | sed s/[[:space:]]*Public[[:space:]]*//g)

rmiflags="-ea -Djava.rmi.server.codebase=http://$publicc/sd0103/classes/ -Djava.rmi.server.useCodebaseOnly=true -Djava.security.policy=java.policy"


cd $srcf

if [ -z $targetsr ] || [ $targetsr == "Public" ]; then
	echo Nothing to do.
elif [ $targetsr == "Repository" ]; then
	sleep 3
	java -ea $rmiflags sd.p1g3.assignment3.mainProgram.RepositoryMain 
elif [ $targetsr == "Entities" ]; then
	sleep 8
	$(dirname $0)/run_entities.sh
elif [ $targetsr == "Registry" ]; then
	killall -u $(whoami) rmiregistry
	sleep 1
	rmiregistry -J-Djava.rmi.server.codebase=http://$publicc/sd0103/classes/ \
            	-J-Djava.rmi.server.useCodebaseOnly=true $targetport &
	java $rmiflags sd.p1g3.assignment3.registry.ServerRegisterRemoteObject 

else
	sleep 5
	java -ea $rmiflags sd.p1g3.assignment3.mainProgram.SharedRegionMain $targetsr
fi
