### Status
[![Build Status](https://travis-ci.org/rgavrysh/spring-hibernate.png?branch=master)](https://travis-ci.org/rgavrysh/spring-hibernate)
# spring-hibernate
This is training project.

The main idea is to build RESTful Spring application.

Application connects to PostgreSQL database in Heroku and do simple transactions. Travis-CI used for building project, then deploys it to Heroku.

# To test and get to know how application works you need:
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