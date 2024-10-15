FROM tomcat:10.0
ADD build/libs/telros-test-task-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
EXPOSE 8085