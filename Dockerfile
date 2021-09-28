FROM maven:3.6-jdk-8-slim

VOLUME results:/test-output/

RUN mkdir -p /downloads
RUN mkdir -p /web-assessment
WORKDIR /web-assessment

COPY . /web-assessment
RUN chmod +x /web-assessment/maven_runner.sh

RUN mvn prepare-package -DskipTests

CMD ["/bin/bash", "/web-assessment/maven_runner.sh"]