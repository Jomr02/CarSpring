#!/bin/sh
while ! nc -z db 3306 ; do
    echo "Waiting for MySQL server to be available"
    sleep 2
done

exec java -jar ServicioInternoCarSpring-0.0.1-SNAPSHOT.jar