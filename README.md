# ShoesStore

This is a simple Web online shoes ordering system.


## Frameworks and Tools

* Backend: Spring + Spring MVC + Spring JDBC
* Database: MySQL
* Cache: Ehcache
* Management Tool: Maven
* Other: Thymeleaf(template engine)


##Usage

1. Import `.sql` files into MySQL.

2. Import this maven project and use `jetty:run` to start it.

3. Now you can visit the homepage `http://localhost:8080/` in your Web browser.

4. There have been two users in database which are shown in the table below:

| Username | Password | Permission    |
|:---------|:---------|:--------------|
| Alice    | 123      | VIP user      |
| Bob      | 456      | ordinary user |

VIP users will enjoy a 5% discount when ordering some kinds of shoes.