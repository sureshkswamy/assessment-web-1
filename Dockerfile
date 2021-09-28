FROM maven:3.6-jdk-8-slim
WORKDIR /project
COPY src /project/src
COPY pom.xml /project

RUN chmod +x /assessment-web-1/maven_runner.sh

RUN mvn prepare-package -DskipTests

CMD ["/bin/bash", "/assessment-web-1/maven_runner.sh"]