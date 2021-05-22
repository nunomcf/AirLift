#!/bin/bash

for i in $(seq -w 1 10); do
    echo Machine $i
    ssh sd305@l040101-ws$i".ua.pt" "$@"
done
