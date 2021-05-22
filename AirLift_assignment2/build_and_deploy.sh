#!/bin/bash
#set -e


#Compile
$(dirname $0)/compile.sh


#Clean machines
$(dirname $0)/all_machines_do.sh "rm -rf *" #dont kill me


#Move classes to machines
tmpf=$(mktemp -d)

cd $(dirname $0)/../src

cp --parents $(find -name \*.class | grep assignment3) $tmpf 

for i in $(seq -w 1 10); do
    echo Machine $i
    scp -r $tmpf/* sd0305@l040101-ws$i".ua.pt:~"
    scp -r ../scripts sd0305@l040101-ws$i".ua.pt:~"
    scp -r ../mapping ../java.policy sd0305@l040101-ws$i".ua.pt:~"
done

rm -r $tmpf


#Create public folder
mappingf=$(dirname $0)/../mapping
publicc=$(grep Public $mappingf | sed s/\s*Public\s*//g)

ssh -l sd0103 $publicc "mkdir -p Public/classes/sd/p1g3/assignment3/interfaces &&
					 mkdir -p Public/classes/sd/p1g3/assignment3/common &&
					 cp -r sd/p1g3/assignment3/interfaces/. Public/classes/sd/p1g3/assignment3/interfaces &&
					 cp -r sd/p1g3/assignment3/common/. Public/classes/sd/p1g3/assignment3/common"
