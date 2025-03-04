# Use Bellsoft Liberica Musl-based image
FROM bellsoft/liberica-runtime-container:jdk-17-crac-cds-slim-stream-musl

# Set the working directory inside the container
WORKDIR /app

# Install required libraries manually
COPY --from=alpine:latest /etc/fonts /etc/fonts
COPY --from=alpine:latest /usr/share/fonts /usr/share/fonts

RUN apt-get update && apt-get install -y \
    fonts-dejavu \
    libfreetype6 \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

# Copy the JAR file into the container
COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

# Run font cache update
RUN fc-cache -fv

# Define the command to run the application
CMD ["java", "-Djava.awt.headless=true", "-Dsun.java2d.noddraw=true", "-Dsun.awt.noerasebackground=true", "-jar", "/app/SAE-Opcc.jar"]
