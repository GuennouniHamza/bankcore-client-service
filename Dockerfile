#				----Étape 1 — builder (JDK)---

#Java 17 LTS
#Le JDK complet
#Alpine Linux comme système de base
#builder donne nom à cet etape
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
#Définit le dossier de travail dans le container —
# toutes les commandes suivantes s'exécutent dans /app
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn ./.mvn
RUN chmod +x mvnw && ./mvnw package -DskipTests

#chmod +x mvnw → rend mvnw exécutable
#./mvnw package → compile + crée le JAR
#-DskipTests → skip les tests pour accélérer

#      				----Étape 2 — runtime (JRE)----

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
#Copie le JAR compilé depuis l'étape builder vers cette étape
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
#Commande exécutée au démarrage du container,Lance l'application Spring Boot