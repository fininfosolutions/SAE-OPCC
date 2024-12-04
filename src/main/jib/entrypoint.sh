#!/bin/sh

exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.fininfo.saeopcc.src/main/java/com/fininfo/saeopcc/SaeOpccApplication.java"  "$@"