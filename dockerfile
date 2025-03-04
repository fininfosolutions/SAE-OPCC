# Use a Debian-based OpenJDK image
FROM openjdk:17-buster

# Set the working directory inside the container
WORKDIR /app

# Install libfreetype6 and fontconfig to resolve font rendering issues
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    fontconfig \
    x11-utils \
    && rm -rf /var/lib/apt/lists/*

# Copy the JAR file into the container
COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

# Define the command to run the application
CMD ["java", "-Djava.awt.headless=true", "-jar", "/app/SAE-Opcc.jar"]
