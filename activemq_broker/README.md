### ActiveMQ broker

##### Running ActiveMQ on Docker
    
    docker run -d -p 61616:61616 -p 8161:8161 rmohr/activemq:5.14.3
    
##### Useful info

ActiveMQ Admin Panel should be on `HOST:PORT/admin/`, on Docker default machine 
it should be `http://192.168.99.100:8161/admin/`. 

Default user and password is `admin`:`admin`. 
 
