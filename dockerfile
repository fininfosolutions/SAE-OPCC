FROM openjdk:17-buster 

WORKDIR /app

RUN apt-get update && apt-get install -y libfreetype6 && rm -rf /var/lib/apt/lists/*

COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

CMD ["java", "-Djava.awt.headless=true", "-jar", "/app/SAE-Opcc.jar"]
