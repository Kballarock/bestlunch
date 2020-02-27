Java Enterprise Project
-------------------------------
Full-featured Spring / JPA Enterprise application with registration / authorization and role-based access rights using the most popular Java tools and technologies: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API, MySQL.

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