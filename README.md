Java Enterprise Project
-------------------------------
Full-featured Spring / JPA Enterprise application with registration / authorization and role-based access rights using 
the most popular Java tools and technologies: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), 
Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API, MySQL.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/585ec2e51214494092adba54d0307db4)](https://www.codacy.com/manual/Kballarock/bestlunch?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Kballarock/bestlunch&amp;utm_campaign=Badge_Grade) [![Build Status](https://travis-ci.org/Kballarock/bestlunch.svg?branch=master)](https://travis-ci.org/Kballarock/bestlunch)

Specifications
-------------------------------
    The task is:
    
    Build a voting system for deciding where to have lunch.
    
     * 2 types of users: admin and regular users
     * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
     * Menu changes each day (admins do the updates)
     * Users can vote on which restaurant they want to have lunch at
     * Only one vote counted per user
     * If user votes again the same day:
        - If it is before 11:00 we asume that he changed his mind.
        - If it is after 11:00 then it is too late, vote can't be changed
    
    Each restaurant provides new menu each day.

Requirements
-------------------------------
Building the API client library requires:

1. Java 1.8+
2. Maven

Install
-------------------------------
Clone repository:
```
git clone https://github.com/Kballarock/bestlunch
```
At first generate the JAR or WAR by executing:
```
mvn clean
```
To install the API client library to your local Maven repository, simply execute:
```
mvn install
```
Run
-------------------------------
1. Open project in IDE
2. Add JPA facets in the project structure and hibernate as provider
3. Add MySQL database:
    * create in MySQL new schema 'bestlunch';
    * user: 'root', no password;
    * configure database connection in IDE.
4. Add new configuration as tomcat server-local.
5. Run tomcat server from IDE.
6. Done.

Basic authentication
-------------------------------     
```
    User login: user@user.com
      password: user
"Authorization: Basic dXNlckB1c2VyLmNvbTp1c2Vy"


   Admin login: admin@admin.com
      password: admin
"Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu"
```

CURL
-------------------------------
###Users handling
1.Get user
```
curl 'http://localhost:8080/bestlunch/rest/admin/users/100001' -i -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
```
2.Get all users
```
curl 'http://localhost:8080/bestlunch/rest/admin/users' -i -H' Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
```
3.Create new user
```
curl 'http://localhost:8080/bestlunch/rest/admin/users' -i -d '{"name" : "NewUser", "email" : "newmail@mail.com", "password" : "12345678", "roles" : ["ROLE_USER"]}' -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
4.Update user
```
curl 'http://localhost:8080/bestlunch/rest/admin/users/100001' -i -X PUT -d '{"name": "UpdatedName", "email": "userasd@user.com", "password": "12345678"}' -H 'Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
5.Delete user
```
curl 'http://localhost:8080/bestlunch/rest/admin/users/100001' -i -X DELETE -H'Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
####Restaurant handling
1.Get restaurant
```
curl 'http://localhost:8080/bestlunch/rest/restaurant/100000' -i -H 'Authorization:Basic dXNlckB1c2VyLmNvbTp1c2Vy'
curl 'http://localhost:8080/bestlunch/rest/admin/100000' -i -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
```
2.Get all restaurants with count per day
```
curl 'http://localhost:8080/bestlunch/rest/restaurant' -i -H 'Authorization:Basic dXNlckB1c2VyLmNvbTp1c2Vy'
curl 'http://localhost:8080/bestlunch/rest/admin' -i -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
```
3.Get all restaurants with votes during the period
```
curl 'http://localhost:8080/bestlunch/rest/restaurant/filter?startDate=2019-12-16&endDate=2019-12-17' -i -H 'Authorization:Basic dXNlckB1c2VyLmNvbTp1c2Vy'
curl 'http://localhost:8080/bestlunch/rest/admin/filter?startDate=2019-12-16&endDate=2019-12-17' -i -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
```
4.Create new restaurant (only Admin)
```
curl 'http://localhost:8080/bestlunch/rest/admin' -i -d '{"name": "NewRestaurant", "description": "NewDescription", "address": "NewAddress"}' -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
5.Update restaurant (only Admin)
```
curl 'http://localhost:8080/bestlunch/rest/admin/100001' -i -X PUT -d '{"name": "UpdatedRestaurant", "description": "UpdatedDescription", "address": "UpdatedAddress"}' -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
6.Delete restaurant (only Admin)
```
curl 'http://localhost:8080/bestlunch/rest/admin/100001' -i -X DELETE -H'Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
####Menu handling
1.Get menu item
```
curl 'http://localhost:8080/bestlunch/rest/admin/restaurant/100000/menu/items/100000' -i -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
```
2.Get all menu items by date
```
curl 'http://localhost:8080/bestlunch/rest/admin/restaurant/100001/menu/items?date=2019-12-17' -i -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu'
curl 'http://localhost:8080/bestlunch/rest/restaurant/100000/menu' -i -H 'Authorization:Basic dXNlckB1c2VyLmNvbTp1c2Vy'
curl 'http://localhost:8080/bestlunch/rest/restaurant/100000/menu?date=2019-12-16' -i -H 'Authorization:Basic dXNlckB1c2VyLmNvbTp1c2Vy'
```
3.Create new menu item (only Admin)
```
curl 'http://localhost:8080/bestlunch/rest/admin/restaurant/100001/menu/items' -i -d '{"name": "NewMenuItem", "price": "2.36"}' -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
4.Update menu item (only Admin)
```
curl 'http://localhost:8080/bestlunch/rest/admin/restaurant/100000/menu/items/100012' -i -X PUT -d '{"name": "UpdatedMenuItem", "price": "15.36"}' -H 'Authorization:Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
5.Delete menu item (only Admin)
```
curl 'http://localhost:8080/bestlunch/rest/admin/restaurant/100000/menu/items/100003' -i -X DELETE -H'Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
####Vote handling
1.Create user vote
```
curl 'http://localhost:8080/bestlunch/rest/restaurant/100000/vote' -i -X POST -H 'Authorization: Basic dXNlckB1c2VyLmNvbTp1c2Vy' -H 'Content-Type: application/json'
curl 'http://localhost:8080/bestlunch/rest/restaurant/100000/vote' -i -X POST -H 'Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```
2.Update user vote
```
curl 'http://localhost:8080/bestlunch/rest/restaurant/100001/vote' -i -X POST -H 'Authorization: Basic dXNlckB1c2VyLmNvbTp1c2Vy' -H 'Content-Type: application/json'
```
3.Delete user vote
```
curl 'http://localhost:8080/bestlunch/rest/restaurant/100000/vote' -i -X DELETE -H'Authorization: Basic YWRtaW5AYWRtaW4uY29tOmFkbWlu' -H 'Content-Type: application/json'
```