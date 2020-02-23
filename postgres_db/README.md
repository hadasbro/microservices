### Email Orders Storage (PostgreSQL database)
#### Overview
PostgreSQL's on Docker.

#### How to run

    $ docker run --name email-orders-storage -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres

#### Info
By the default PostgreSQL DB run on port **5432**.

Docker default's machine IP is **192.168.99.100**.

By the default Postgres server on Docker is on **192.168.99.100:5432**

Default user is **postgres**.

Default password is **password**.

This DB is required by module `email_order_service`. 

This DB is storage for email orders.