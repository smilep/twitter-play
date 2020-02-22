# Twitter Play

## Demonstrates connecting to Twitter API using oAuth 1.0a. oAuth 1.0a header generated purely using Java 8 without 3rd party application.

* Check [TwitterOauthHeaderGenerator](/src/main/java/com/smilep/twitter/helper/TwitterOauthHeaderGenerator.java) to directly see the code to generate oAuth 1.0a Authorization header.

* Sample code to connect to Twitter API using RestTemplate avaiable [here](/src/main/java/com/smilep/twitter/service/impl/FollowersServiceImpl.java)

* This application also contains a SpringBoot based REST service which checks if a Twitter handle follows another. You can run the application by executing the main method of [TwitterPlayApplication](/src/main/java/com/smilep/twitter/TwitterPlayApplication.java) and then access the Swagger UI to try out the webservice at http://localhost:8090/swagger-ui.html
