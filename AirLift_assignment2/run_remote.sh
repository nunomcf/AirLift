#!/bin/bash

$(dirname $0)/all_machines_do_paralel.sh "killall -u \$(whoami) java rmiregistry || ./scripts/run_mapping.sh"
