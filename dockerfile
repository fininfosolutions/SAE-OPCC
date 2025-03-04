# Utiliser une image de base Java
FROM openjdk:17-jre

# Ajouter le fichier jar dans le conteneur
WORKDIR /app
COPY SAE-Opcc.jar /app/SAE-Opcc.jar

# Définir la commande par défaut d'exécution
CMD ["java", "-Djava.awt.headless=true", "-jar", "/app/SAE-Opcc.jar"]
