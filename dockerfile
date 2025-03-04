# Utiliser une image de base Java
FROM openjdk:17 

# Ajouter le fichier jar dans le conteneur
WORKDIR /app
COPY COPY target/saeopcc-0.0.1-SNAPSHOT.jar /app/SAE-Opcc.jar

RUN chmod +x /app/SAE-Opcc.jar 
# Définir la commande par défaut d'exécution
CMD ["java", "-Djava.awt.headless=true", "-jar", "/app/SAE-Opcc.jar"]
