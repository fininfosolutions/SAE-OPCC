# Use a Debian-based OpenJDK image
FROM openjdk:17-windowsservercore

# Set the working directory inside the container
WORKDIR /app

RUN apt-get update && apt-get install -y \
    libfreetype6 \
    fontconfig \
    x11-utils \
    libx11-dev \
    libxext-dev \
    libxrender1 \
    && rm -rf /var/lib/apt/lists/*

# Copy the JAR file into the container
COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

# Define the command to run the application
CMD ["java", "-Djava.awt.headless=true", "-Dsun.java2d.noddraw=true", "-Dsun.awt.noerasebackground=true", "-jar", "/app/SAE-Opcc.jar"]
