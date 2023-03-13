## Car Lease Web Service

Car Lease Web Service is specifically designed to  adding, updating, removing and fetching recipes and ingredients from database and render the requested details as JSON response to end user. It also has the search capability with helps to search recipes based on any filter criteria.The response from ReST APIs can be further integrated with front end view for better presentation.
maintain vehicle versions, customers and
other data needed to service a broker.
The end-users of the Car-lease Platform API are:
- Brokers that calculate the lease rate for a customer and maintain customer data.
- Leasing company that maintains data to make an accurate calculation.
## System Design

Car Lease Web Service is microservice based layered architecture with RESTful Web Service. There are total 3 services:
- Customer Service\
 Customer service basically designed to add,update,retrieve and remove customer details. Broker will be able to add,update,retrieve,delete
 customer details whereas Company employees will be able to retrieve customer details.
 Once customer service is up 2 basic customer details will be autopopulated in the app.

- Car Service\
 Car service is designed to add,update,retrieve and remove car details. Company employees will be able to add,update,retrieve,delete
 car details whereas Broker will be able to retrieve car details.
 Once car service is up,2 basic car records will be autopopulated in the app

- Car-Lease Service\
 Car Lease service is designed to lease a car in the app. Car lease api fetches the car details from car api and checks if the car is already leased bu other customer.
 If it is not leased ,it gets leased by the customer and appropriate details gets added in db. The company employee has authority to create lease between a car and customer.
 After creation of lease,Broker has access to view lease details by customer id. The company employee can view a lease car details by providing car details or customer details. 

![diagram](https://user-images.githubusercontent.com/65228852/224852256-4d4bd530-b9a7-4fdb-99e9-fd8c1d037ae1.jpeg)


## Service Components
Each Service has 4 components as follows:
- API Layer\
Top layer, which is main interface available for front-end or end user to consume APIs
Contains end points implementation
Springboot-starter-web module used as a framework to implement ReSTful api end points

- Service Layer\
The service layer is a layer in an application that facilitates communication between the api end point and the data access layer. Additionally, business logic is stored in the service layer.
It is responsible for interacting with Data Access Layer and transferring the  data as required by top and below layers
Further it is a module added to decouple business logic of recipes data transfer and mapping from/to API layer

- Data Access Layer\
The persistence layer contains all the database storage logic. It is responsible for converting business objects to the database row and vice-versa with Object Relationship Mapping (ORM).
This layer contains  entity classes and JPA repositories which implement lower level functionality of storing/retrieving  data

- Persistence Layer\
Bottom most layer, responsible for physically storing the car lease data onto database table.
Table - car,customer,lease are used to store the car-lease data for the service
For development and testing purposes, the Embedded H2 Database provided by Spring Boot framework is also utilized




----------------------------------------- 

## Supported Features
Feature	Software Module Used

|       Feature        |          Software Module Used          |
|:--------------------:|:--------------------------------------:|
| Object Relationship  |        Mapping	Spring Data JPA       |
|  Exception Handling  | Controller Advice and ExceptionHandler |
|      Unit Tests      |           Junit 5                      |
|    Security          |      JWT                               |



#### Minimum Requirements

- Java 11
- Maven 3.x
- Git 
- Postman

### Steps to build Web Service

1. Install Car Web Service
-  Download zip https://github.com/jkolay/car-service.git
-  Move to car folder and follow Install application command line in car folder
- Build Project

  ```
  $ mvn clean package 
  ```
  To build by skipping unit tests run maven command 
  ```
  $ mvn clean package -DskipTests= true
   ```
- On successfully build completion, one should have web service jar in target directory named as
```
car-0.0.1-SNAPSHOT.jar
```

Command to execute:

  ```
  $ java -jar car-0.0.1-SNAPSHOT.jar
  ```

### Swagger URL

```text
http://localhost:8081/swagger-ui/index.html#/
```

### Database components

Open browser and navigate to http://localhost:8081/h2-console/. Replace the below field details

|   Field   |      Value      |
|:---------:|:---------------:|
| JDBC URL  | jdbc:h2:mem:car |
| User Name |       sa        |
| Password  |    password     |
-----------------------------------------



2. Install Customer Web Service
-  Download zip https://github.com/jkolay/customer.git
-  Move to car folder and follow Install application command line in customer folder
- Build Project

  ```
  $ mvn clean package 
  ```
  To build by skipping unit tests run maven command
  ```
  $ mvn clean package -DskipTests= true
   ```
- On successfully build completion, one should have web service jar in target directory named as
```
customer-0.0.1-SNAPSHOT.jar
```

Command to execute:

  ```
  $ java -jar customer-0.0.1-SNAPSHOT.jar
  ```

### Database components

Open browser and navigate to http://localhost:8083/h2-console/. Replace the below field details

|   Field   |        Value         |
|:---------:|:--------------------:|
| JDBC URL  | jdbc:h2:mem:customer |
| User Name |          sa          |
| Password  |                      |
-----------------------------------------
### Swagger URL

```text
http://localhost:8083/swagger-ui/index.html#/
```


----------------------------------------- 
3. Install Car-lease Web Service
-  Download zip https://github.com/jkolay/car-lease.git
-  Move to car-lease folder and follow Install application command line in customer folder
- Build Project

  ```
  $ mvn clean package 
  ```
  To build by skipping unit tests run maven command
  ```
  $ mvn clean package -DskipTests= true
   ```
- On successfully build completion, one should have web service jar in target directory named as
```
lease-0.0.1-SNAPSHOT.jar
```

Command to execute:

  ```
  $ java -jar lease-0.0.1-SNAPSHOT.jar
  ```

### Swagger URL

```text
http://localhost:8084/swagger-ui/index.html#/
```

### Database components

Open browser and navigate to http://localhost:8084/h2-console/. Replace the below field details

|   Field   |         Value         |
|:---------:|:---------------------:|
| JDBC URL  | jdbc:h2:mem:car-lease |
| User Name |          sa           |
| Password  |                       |
-----------------------------------------
### Database components

Open browser and navigate to http://localhost:8084/h2-console/. Replace the below field details



## Web Service ReST End Points Usage and Sample Response

### CAR-LEASE API

#### 1. Create auth token
This below end point will create a new token which will authenticate the request as company employee request



2 users has been added to user to all services .


|     username      | password | role    |
|:-----------------:|:--------:|:--------|
| happy@example.com |  12345   | COMPANY |
| jayati@gmail.com  |  12345   | BROKER  |
-------------------------------------------

Retrieve authentication token from the below api

###### GET (http://localhost:8084/user)
In postman add request in below format and retrieve authentication from response headers

<img width="808" alt="Screenshot 2023-03-14 at 00 19 37" src="https://user-images.githubusercontent.com/65228852/224853460-da23d843-af5b-4211-8da7-e8a687605c77.png">



### 2. Calculate lease amount
##### POST (http://localhost:8084/api/v1/car-lease/createLease)
Users having company role can create new lease\
 ```
  {
    "customerId": 1,
    "carId": 2,
    "duration": 60,
    "leaseStartDate": "2023-06-05"
}
 ```
In postman In request add two fields\
Authorization and X-XSRF-TOKEN\

To Execute this request 2 steps needs to be followed.
1. Add only Authentication retrieved from **1. Create auth token** step and execute the request.

<img width="802" alt="Screenshot 2023-03-13 at 23 38 48" src="https://user-images.githubusercontent.com/65228852/224852625-3eda65b8-f09a-4993-8ace-23bbfba2fc43.png">


2. The request will be failed. Copy XSRF-TOKEN token from response cookie and add the value to car-lease create request header 
   under X-XSRF-TOKEN token and execute

<img width="804" alt="Screenshot 2023-03-13 at 23 39 34" src="https://user-images.githubusercontent.com/65228852/224852659-835b1d77-40cf-47e5-9d8f-c380a1c20c72.png">

Response
 ```
 {
    "leaseId": 1,
    "duration": 60,
    "leaseStartDate": "2023-06-05",
    "leaseEndDate": "2028-06-05",
    "interestRate": 4.5,
    "defaultAllottedMileage": 45000.0,
    "leasePerMonth": 225.06,
    "customer": {
        "customerId": 1,
        "name": "Ana ",
        "street": "Charles Road",
        "houseNumber": "112",
        "zipcode": "1189AT",
        "place": "Amstelveen",
        "emailAddress": "ana@gmail.com",
        "phoneNumber": "1234098767"
    },
    "car": {
        "carId": 1,
        "make": "Hyundai",
        "model": "creta",
        "version": "x25",
        "numberOfDoors": 4,
        "co2Emission": 1.45,
        "netPrice": 59000.0,
        "grossPrice": 59000.0,
        "mileage": 10000
    }
}
 ```

#### 2. Get Lease details for a customer
This below end point will fetch lease details of a customer\

##### GET (http://localhost:8084/api/v1/car-lease/calculate_lease/customer/{customerId})
Retrieve the authentication token and add it to the header.\
This api can be accessed by users having COMPANY and BROKER role\
Response
 ```
  [
    {
        "leaseId": 1,
        "leaseStartDate": "2023-06-05",
        "leaseEndDate": "2028-06-05",
        "carId": 1,
        "leasePerMonth": 507.92
    },
    {
        "leaseId": 2,
        "leaseStartDate": "2023-06-05",
        "leaseEndDate": "2028-06-05",
        "carId": 2,
        "leasePerMonth": 299.1
    }
]
  ```
#### 3.Get lease details of a car

This below end point will fetch of lease details of a  car\
Retrieve the authentication token and add it to the header.\
##### GET (http://localhost:8084/api/v1/car-lease/calculate_lease/car/{carId})
Response
 ```
  {
    "leaseId": 1,
    "duration": 60,
    "leaseStartDate": "2023-06-05",
    "leaseEndDate": "2028-06-05",
    "interestRate": 4.5,
    "defaultAllottedMileage": 45000.0,
    "leasePerMonth": 507.92,
    "customer": {
        "customerId": 1,
        "name": "Ana ",
        "street": "Charles Road",
        "houseNumber": "112",
        "zipcode": "1189AT",
        "place": "Amstelveen",
        "emailAddress": "ana@gmail.com",
        "phoneNumber": "1234098767"
    },
    "car": {
        "carId": 1,
        "make": "Hyundai",
        "model": "creta",
        "version": "x25",
        "numberOfDoors": 4,
        "co2Emission": 1.45,
        "netPrice": 59000.0,
        "grossPrice": 59000.0,
        "mileage": 10000
    }
}
  ```

### CAR API

|                                                     endpoint                                                     | method type |      role       | headers required        |
|:----------------------------------------------------------------------------------------------------------------:|:-----------:|:---------------:|:------------------------|
|                                         http://localhost:8081/api/v1/car                                         |    POST     |     COMPANY     | Authorization,X-XSRF-TOKEN |
|                                   http://localhost:8081/api/v1/car/view{carId}                                   |     GET     | BROKER ,COMPANY | Authorization           |
|                                   http://localhost:8081/api/v1/car/retrieveAll                                   |     GET     | COMPANY ,BROKER | Authorization           |
|                                    http://localhost:8081/api/v1/car/update/1                                     |    PUT      |COMPANY          |  Authorization,X-XSRF-TOKEN|
| ---------------------------------------------------------------------------------------------------------------- | -------------- | -----------------|-------------------------|  

For POST and PUT api please follow car lease xsrf step

### CUSTOMER API

|                                                     endpoint                                                     | method type |       role        | headers required        |
|:----------------------------------------------------------------------------------------------------------------:|:-----------:|:-----------------:|:------------------------|
|                                      http://localhost:8083/api/v1/customer                                       |    POST     |      BROKER       | Authorization,X-XSRF-TOKEN |
|                                 http://localhost:8083/api/v1/customer/view/{id}                                  |     GET     |  BROKER ,COMPANY  | Authorization           |
|                         http://localhost:8083/api/v1/customer/customerList?page=0&size=1                         |     GET     |  COMPANY ,BROKER  | Authorization           |
|                                http://localhost:8083/api/v1/customer/update/{id}                                 |    PUT      |      BROKER       |  Authorization,X-XSRF-TOKEN|
| ---------------------------------------------------------------------------------------------------------------- | -------------- | ----------------- |-------------------------|  

For POST and PUT api please follow car lease xsrf step

### Future Enhancements
- API gateway and service discovery implementation.






