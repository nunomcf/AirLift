
for i in $(seq 1 $1)
 do
 java main.AirLift > log$2.txt
 echo "Cicle $i"
 
 done
rm log$2.txt