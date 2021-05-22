#!/bin/bash

login=sd0103
host_pref=l040101-ws
host_post=.ua.pt

for i in $(seq -w 1 10); do
	echo Machine $i
	echo ssh-copy-id $login@$host_pref$i$host_post
	ssh $login@$host_pref$i$host_post chmod go-rwx \~
done
