# Usar una imagen base de Java
FROM eclipse-temurin:21-jdk-alpine

# Crear y configurar el directorio de la aplicación
WORKDIR /app

# Copiar el archivo JAR de la aplicación en el contenedor
COPY /build/libs/WebBackend.jar /app/WebBackend.jar

# Copiar la carpeta pages al contenedor
COPY pages /app/pages

# Exponer el puerto 8080
EXPOSE 10000

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/WebBackend.jar"]
