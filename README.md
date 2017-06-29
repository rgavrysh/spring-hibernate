### Status
[![Build Status](https://travis-ci.org/rgavrysh/spring-hibernate.png?branch=master)](https://travis-ci.org/rgavrysh/spring-hibernate)
# Spring-Boot (backend REST) + Angular 2 (frontend)
This is training project.

The main idea is to build RESTful Spring application. Frontend built using Angular2.

Application connects to PostgreSQL database in Heroku and do simple transactions. Travis-CI used for building project, then deploys it to Heroku.

# Application structure:
Stack of technologies:
 1) Backend: Spring-boot, Hibernate, JPA, PostgreSQL, Spring-embedded Tomcat
 2) Frontend: Angular 2, Express server

# To test and get to know how application works you need:
 Application consist of two modules: backend and frontend.
 Clone and run ```mvn package```. It will build and package two modules.
 Backend module is a RESTfull service running on spring-embedded server under port 8080. To start backend run
 ```java
 java -jar -Dserver.port=8080 backend/target/backend-1.0-SNAPSHOT.jar
 ```
 Frontend module is Angular 2 application which is accessible by port 4200. To start frontend run
 ```
 node frontend/src/main/frontend/server.js
 ```
 # Additional details
 As frontend is an Angular 2 app you can use angular-cli to run it using ```ng serve```. Otherwise you can use node Express server and start app like ```node server.js```. Frontend then send requests to backend on address: http://localhost:8080/.

 1) Get auth token (make a POST request to http://localhost:8080/oauth/token) to make further requests:
 ```java
  curl -i -X POST -H 'Authorization: Basic cmVzdDpxd2UxMjM=' -d 'username=Volodymyr&password=****&grant_type=password' 'http://localhost:8080/oauth/token'
  ```
  password for user 'Volodymyr' is: 1111

  password for admin-user 'Roman' is: qwe123

 2) You get response like:
  ```shell
  {"access_token":"4feb1c5d-7471-4b02-91b3-cc4ba7eace8e","token_type":"bearer","refresh_token":"ed659793-5b63-49fa-8d14-86ff4854b537","expires_in":43199,"scope":"read trust"}
  ```

 3) Using access_token you can request for other endpoints (in hib.controllers package), like:
 ```shell
 curl -i -X GET -H 'Authorization: Bearer 4feb1c5d-7471-4b02-91b3-cc4ba7eace8e' 'http://localhost:8080/me'
 ```

 then you get response:

 ```shell
 {"id":2,"name":"Volodymyr","phone":665890123,"email":"vov@yopmail.com","username":"Volodymyr","enabled":true,"accountNonExpired":true,"authorities":[{"id":2,"name":"ROLE_USER","authority":"ROLE_USER"}],"accountNonLocked":true,"credentialsNonExpired":true}
 ```