FROM gradle:8.7.0-jdk21-alpine AS gradle
COPY --chown=gradle:gradle . /home/gradle/
WORKDIR /home/gradle/
RUN gradle war

FROM tomcat:10.1.23-jre21 AS tomcat10
RUN rm -rf /usr/local/tomcat/webapps/* /usr/local/tomcat/conf/server.xml

COPY ./src/main/resources/toTomcat/* /usr/local/tomcat/conf/
COPY --from=gradle /home/gradle/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 5432
CMD chmod +x /usr/local/tomcat/bin/catalina.sh
CMD ["catalina.sh", "run"]
