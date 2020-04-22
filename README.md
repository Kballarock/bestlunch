Java Enterprise Project
-------------------------------
Full-featured Spring / JPA Enterprise application with registration / authorization and role-based access rights using 
the most popular Java tools and technologies: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), 
Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API, MySQL.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/585ec2e51214494092adba54d0307db4)](https://www.codacy.com/manual/Kballarock/bestlunch?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Kballarock/bestlunch&amp;utm_campaign=Badge_Grade)

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

CURL
-------------------------------
####User handling
1.Get user
```

```
2.Get all users
```

```
3.Delete user
```

```
4.Update user
```

```
5.Create new user
```

```
####Restaurant handling
1.Get restaurant
```

```
2.Get all restaurants
```

```
3.Delete restaurant
```

```
4.Update restaurant
```

```
5.Create new restaurant
```

```
####Menu handling
1.Get menu item
```

```
2.Get all menu items by date
```

```
3.Delete menu item
```

```
4.Update menu item
```

```
5.Create new menu item
```

```
####Vote handling
1.Create user vote
```

```
2.Update user vote
```

```
3.Delete user vote
```

```