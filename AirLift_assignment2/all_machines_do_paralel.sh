#!/bin/bash

for i in $(seq -w 1 10); do
    echo Machine $i
    ssh sd0103@l040101-ws$i".ua.pt" $@ '</dev/null >log 2>&1 &' &
done
