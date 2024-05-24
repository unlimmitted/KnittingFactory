FROM node:lts-alpine as nodejs
RUN apk add --no-cache git
WORKDIR /usr/src/node
RUN git clone https://github.com/unlimmitted/knitting-factory-front.git
WORKDIR /usr/src/node/knitting-factory-front
RUN npm install vite
RUN npm install
RUN npm run build

FROM gradle:8.7.0-jdk21-alpine AS gradle
COPY --chown=gradle:gradle . /home/gradle/

COPY --from=nodejs /usr/src/node/knitting-factory-front/dist/static/index.js /home/gradle/src/main/webapp/js/index.js
COPY --from=nodejs /usr/src/node/knitting-factory-front/dist/static/vendor.js /home/gradle/src/main/webapp/js/vendor.js
COPY --from=nodejs /usr/src/node/knitting-factory-front/dist/static/index.css /home/gradle/src/main/webapp/css/index.css
WORKDIR /home/gradle/
RUN gradle war

FROM tomcat:10.1.23-jre21 AS tomcat10
RUN rm -rf /usr/local/tomcat/webapps/* /usr/local/tomcat/conf/server.xml

COPY ./src/main/resources/toTomcat/* /usr/local/tomcat/conf/
COPY --from=gradle /home/gradle/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 5432
CMD chmod +x /usr/local/tomcat/bin/catalina.sh
CMD ["catalina.sh", "run"]
