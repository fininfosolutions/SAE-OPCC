# Use a Debian-based OpenJDK image
FROM bellsoft/liberica-runtime-container:jdk-17-crac-cds-slim-stream-musl

# Set the working directory inside the container
WORKDIR /app

RUN apk add font-dejavu freetype fontconfig
RUN fc-cache -fv # reloads cache

# Copy the JAR file into the container
COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

# Define the command to run the application
CMD ["java", "-Djava.awt.headless=true", "-Dsun.java2d.noddraw=true", "-Dsun.awt.noerasebackground=true", "-jar", "/app/SAE-Opcc.jar"]
