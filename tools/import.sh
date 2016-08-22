#!/bin/bash -ex

F=/tmp/cgss-filt

cut -d' ' -f2- logs/gpsstatus-*.log | egrep '^{' > "$F"
mongoimport --db gps --collection a --file "$F"
rm "$F"

