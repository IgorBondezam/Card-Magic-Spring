supertypes=legendary&page=121 pages legendary
page=937 pages all cards
# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#web)
* [Validation](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#io.validation)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#using.devtools)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#web.security)


### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

---
## 🏁

### To Start
- Run `./gradlew clean build` to build the project, setup de dependencies and create a `.jar`
- Execute the docker compose up.
  - This will start the database and the spring project.
  - The values of hots, ports, user and password is in `docker-compese.yml`
---
### Here you are some end-points

User
- 

    {
        "email": "igorrrrr@gmail.com",
        "password": "12345678"
    }

- ### Get
  - http://localhost:8080/user
  - http://localhost:8080/user/{userId}
- ### Post
  - http://localhost:8080/user
  - http://localhost:8080/login
- ### Put
  - http://localhost:8080/user

---
Deck
- 

    {
        "name": "The Thanos Deck"
    }

- ### Post
    - http://localhost:8080/deck/user/{userId}
- ### Patch
  - http://localhost:8080/deck/choose/commander/{userId}/{commander's_name}
  - http://localhost:8080/deck/choose/random/99/{userId}