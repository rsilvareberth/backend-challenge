# Invillia recruitment challenge

[![Build Status](https://travis-ci.org/shelsonjava/invillia.svg?branch=master)](https://travis-ci.org/shelsonjava/invillia)

![Invillia Logo](https://invillia.com/public/assets/img/logo-invillia.svg)
[Invillia](https://https://www.invillia.com/) - A transformação começa aqui.

The ACME company is migrating their monolithic system to a microservice architecture and you’re responsible to build their MVP (minimum viable product)  .
https://en.wikipedia.org/wiki/Minimum_viable_product

Your challenge is:
Build an application with those features described below, if you think the requirements aren’t detailed enough please leave a comment (portuguese or english) and proceed as best as you can.

You can choose as many features you think it’s necessary for the MVP,  IT’S NOT necessary build all the features, we strongly recommend to focus on quality over quantity, you’ll be evaluated by the quality of your solution.

If you think something is really necessary but you don’t have enough time to implement please at least explain how you would implement it.

## Tasks

Your task is to develop one (or more, feel free) RESTful service(s) to:
* Create a **Store**
* Update a **Store** information
* Retrieve a **Store** by parameters
* Create an **Order** with items
* Create a **Payment** for an **Order**
* Retrieve an **Order** by parameters
* Refund **Order** or any **Order Item**

Fork this repository and submit your code with partial commits.

## Business Rules

* A **Store** is composed by name and address
* An **Order** is composed by address, confirmation date and status
* An **Order Item** is composed by description, unit price and quantity.
* A **Payment** is composed by status, credit card number and payment date
* An **Order** just should be refunded until ten days after confirmation and the payment is concluded.

## Non functional requirements

Your service(s) must be resilient, fault tolerant, responsive. You should prepare it/them to be highly scalable as possible.

The process should be closest possible to "real-time", balancing your choices in order to achieve the expected
scalability.

## Nice to have features (describe or implement):
* Asynchronous processing
* Database
* Docker
* AWS
* Security
* Swagger
* Clean Code

## Developed features : Author Reberth Rodrigues
* Asynchronous processing with Camel WireTap router. The http request trigger asynchronous router to send message to Active MQ queue. And http call is promptly answered for client.

Camel EIP WireTap
![](https://camel.apache.org/manual/latest/_images/eip/WireTap.gif)

Active MQ message queue (scalable)
![](https://activemq.apache.org/assets/img/competing-consumers.png)


```
install AMQ (locally)
run broker with ./artemis run
```


* Database: Postgresql. Using entities mapping and Spring data jpa. Flyway will be Included  to manage database changes in future Releases. 

* Docker: Image can be build with cli:

```
s2i build -e JAR_NAME=invillia-0.0.1.jar -e INCREMENTAL=false git://github.com/rsilvareberth/backend-challenge sarcouy/s2i-springboot:jdk8-mvn3.3.9 backend-challenge
docker run -p 8080:8080 backend-challenge
```

The application.properties is prepared for environment variables of a container. E.g. camel.springboot.name=${BKEND_CAMEL_NAME:bkend-challenge}

* for entity address. Was shared among other objects

* Swagger: Camel router includes details. Included in this project with /api-doc uri.

* The application is ready to use HTTPS. Enabling session on application.properties and modifying server port to 8443