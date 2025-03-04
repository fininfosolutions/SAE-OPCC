# Use a Debian-based OpenJDK image
FROM openjdk:7u171-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Install required font libraries
RUN apt-get update && apt-get install -y \
    fonts-dejavu \
    libfreetype6 \
    libfontconfig1 \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

# Run font cache update
RUN fc-cache -fv

# Copy the JAR file into the container
COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

# Define the command to run the application
CMD ["java", "-Djava.awt.headless=true", "-Dsun.java2d.noddraw=true", "-Dsun.awt.noerasebackground=true", "-jar", "/app/SAE-Opcc.jar"]
