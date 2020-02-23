### Log Service
#### Overview
This is just centralized log service. Global log management module based on Spring Boot and Graylog.

#### Graylog
Graylog is a log management system for capturing, storing, and enabling 
real-time analysis. 

- MongoDB stores metadata 
[MongoDB website](https://www.mongodb.com/)

- Graylog uses Elasticsearch as backend storage system
[Elasticsearch website](https://www.elastic.co/products/elasticsearch)

- Graylog is Open Source log aggregator and analyzer [Graylog website](https://www.graylog.org/)

- ActiveMQ is an open source message broker writte [Apache ActiveMQ website](https://activemq.apache.org/)

#### ActiveMQ
See `activemq_broker` service.


#### Usage

###### Server

This service has 2 entry points - ActiveMQ producer and REST API. You can send logs 
from anyother app here via ActiveMQ or just send log via REST.

Adjust `docker-compose.yml` and logger settings in `log4j2.xml`.

Use docker-compose to run GrayLog on Docker.

    $ cd this_module
    
    $ docker-compose up
    
    

By the default GrayLog dashboard is on `_HOST_:9000` (e.g. for default docker machine it is `http://192.168.99.100:9000`)

Default dashboad user and password is `admin:admin`

Application starts on port `6065`. 

###### Client
There are 2 ways how to send logs here.
- ActiveMQ server
        
        convert and send `GrayLogException` to ActiveMQ server (see `activemg_broker` module)
        
    see example: `com.github.hadasbro.graylog_service.service.graylog.client.sample.ActiveMQLoggerClient`
    
    Default ActiveMQ server "destination" for this service is `app-logs`. 
    
- REST API 
    
        $ POST _HOST_/log-server
        
        payload:  `GrayLogException` 
    
    see REST (Feign) example: `com.github.hadasbro.graylog_service.service.graylog.client.sample.FeignLoggerClient`


#### Testing

**Adding Input in GrayLog Backoffice** 

Go to GrayLog Backoffice `_HOST_:9000` -> System -> Inputs -> Choose Input [ GELF UDP ] -> click [ Launch new input ]

**Generate test logs**

Use test resource `_HOST_:6065/log-server/test` to generate some test logs and check GrayLog's backoffice.

