### Couchbase NoSQL Database

##### How to run on Docker
    
    docker run -d --name db -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 couchbase

##### Useful info

Couchbase Web Console should be on `HOST:PORT:8091`, on Docker default machine 
it should be `http://192.168.99.100:8091`. 

This DB is used by module `Warehouse Service`, default bucket used by 
this moduleis `demo`, user is `root`, password is `123456`;

