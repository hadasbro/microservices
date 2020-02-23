### Email Supply Order Service
#### Overview
This is Email Supply Order Service based on Spring Integration (email integration).

Module uses PostgreSQL as a database.
 
#### Usage
- Update your email server and DB details in `application.properties` and run application.

- Run PostgreSQL DB to be a storage for supply orders received via email 
(see module `postgres_db` which is just PostreSQL database on Docker project).

- Use any email server/address to send Supply Order from your email to this service and then, from here 
to `stock_service`.

Email used to send an order should be an existing email of any user in `stock_service`, 
so you can send a Supply Order only from email of any user registered in `stock_service`.

Order's format should look as below:

    [order_start]
    product#product_catalog_id x quantity
    [order_end]  
    
For example: 
    
    [order_start]
    product#1 x 14
    product#2 x 1
    product#4 x 3
    product#6 x 8
    [order_end] 

Expected result is that current service (`email_order_service`) reveices that 
email, process it, save to the database and then sends a Supply Order to 
`stock_service` which further process your supply order.


Application starts on port `6061`. 

#### TODO
Actuator / Spring Boot Admin.

#### See more
[Spring Integration](https://spring.io/projects/spring-integration)

[Spring Integration - email](https://docs.spring.io/spring-integration/reference/html/mail.html)
 