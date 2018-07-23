# demo simple json text file processing

## Requirements
1. Simple json processing service that also exposes an HTTP interface.
2. Process a text file that contains lines of event data encodeded in JSON. 
3. The app handles corrupted events.

## Design
1. The app using spring boot
2. Generate a war
3. User upload a file (sunjected to tomcat limit) 
4. when user upload the file it is procesed and redirected to another page for stats results

## Implementation
1. it consumes the events from the text file and gather the following stats: a count of events by event type and count of words encountered in the data field of the events. 
2. it exposes these stats in an HTTP interface.

## Tech stack
1. Java - 1.8
2. Maven - 4
3. Junit 4.12
4. Thymeleaf 3.0.9
5. SpringBoot - 2
6. Slf4j 1.7.25
7. Intellij 2018.1

## Package the app and deploy in tomcat
./mvnw clean package 

## Improvements
1. Tests need to be provided for the controller using MockMultipartFile, WebApplicationContext and mock the service.
2. Need to be improved if need to handle large file