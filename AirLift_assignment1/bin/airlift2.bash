for i in $(seq 1 $1)
 do
 zsh --window-with-profile=Unnamed -e "/Users/sergiogasalho/Desktop/UA/Quarto_Ano/Segundo_Semestre/SD/AirLift/AirLift_assignment1/src/airlift.bash $2 $i "> /dev/null 2>&1
 done
