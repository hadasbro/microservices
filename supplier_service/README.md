### Products supplier
#### Overview
This is products supplier. Module produces batches of products depending on Supply Orders and supply 
them to Stock Service (`stock_service`) through Warehouse Service (`warehouse_service`). 

Also module uses MongoDB Database as a storage for 
supply orders. See also module `mongo_docker` which 
is just MongoDB on Docker project.

#### Usage
Run related services described above.

Update `application.properties` with 
- Kafka and Zookeeper host and port.
- Mongo DB credentials

To run Kafka and Zookeeper for test purposes see module `kafka_docker` 
or [this project](https://github.com/wurstmeister/kafka-docker)

By the default module expects below services:
- Kafka (on docker): *192.168.99.100:9092*
- Zookeeper (on docker): *192.168.99.100:2181*

Default topic is `supply_channel.electronic`.

By the default app stats on port `6062`.
