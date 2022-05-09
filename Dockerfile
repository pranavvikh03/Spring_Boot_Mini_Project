FROM openjdk:17.0.2
LABEL maintainer = "Pranav Vikharankar"
ADD build/libs/sip-management-0.0.1-SNAPSHOT.jar sip_management_docker.jar
ENTRYPOINT ["java", "-jar", "sip_management_docker.jar"]