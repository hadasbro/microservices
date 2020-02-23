### Cassandra on Docker

This is just know-how how to run Cassandra on docker with very basic configuration.

##### Cassandra Server
    docker run -e DS_LICENSE=accept -e CASSANDRA_BROADCAST_ADDRESS=192.168.99.100 -p 9042:9042 --memory 10g --name my-dse -d datastax/dse-server:6.7.0 -g -s -k 

##### Cassandra Studio
    docker run -e DS_LICENSE=accept --link my-dse -p 9091:9091 --name my-studio -d datastax/dse-studio:6.7.0

##### Info
keyspace: *electromarket*

docker default machine IP: *192.168.99.100:9042*

cassandra internal IP/port: *172.17.0.2:9042*

web interface (from host): *192.168.99.100:9091*

host in web-client: *my-dse*

