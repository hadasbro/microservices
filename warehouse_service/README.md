### Distibutor Service
#### Overview
Consumes and handle supplies provided through Kafka and distrubute supplies to specific warehouses.

#### Usage
Update application.properties with Kafka and Zookeeper host and port. 

To run Kafka and Zookeeper for test purposes see module `kafka_docker` 
or [this project](https://github.com/wurstmeister/kafka-docker)

Run Couchbase server (see module `couchbase_db`) and create 
required user with password and bucket (default settings are 
user: `root`, password: `123456`, bucket: `demo`).

By the default module expects below services:
- Kafka (on docker): **192.168.99.100:9092**
- Zookeeper (on docker): **192.168.99.100:2181**
- Couchbase (on docker): **192.168.99.100:8091**

Default topic is `supply_channel.electronic`.
Couchbase password is `123456`, bucket is `default`.

By the default app stats on port `6064`.
