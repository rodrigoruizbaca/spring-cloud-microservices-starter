## Spring Cloud Microservices Starter
Starter project for spring cloud microservices based architecture. It can be used to start a microservices based project using the netflix cloud arquitecture over spring cloud.
For more information about the archirecture please see the wiki page: https://github.com/rodrigoruizbaca/spring-cloud-microservices-starter/wiki/Architecture


### Configuration
If the default configuration needs to be changed, it needs be do it as follows:
* For configuration service all its properties needs to be specified on the file named main/resources/application.yml, for obvious reasons this service is the only one its properties are not in the config central repository.
* Since the configuration service endpoint is the only one that needs to be specified before each service can get its properties from the config service, this property needs to be specified on the boostrap.yml file of each service (Eg: AuthService/main/resources/boostrap.yml). Note there is always a *-docker.yml file to change the properties when the project is ran under docker.
* To configure all the properties of each service see the folder config-files on this repository. **Please note you need to do a push in order to be taken by the configuration service.**
  * Every time a change is made on a config file (auth.yml for example) a push needs to be made to be propagated and refreshed.
* The auth service uses mongo as database. In order to configure it, you need to change the mongo uri in the auth.yml file contained in config-files folder.  

### How to build
* All the projects use maven (3.5.3) and java 1.8 to be built.
* Run the following commands
  * mvn -f Configuration/ clean install
  * mvn -f Discovery/ clean install
  * mvn -f Gateway/ clean install
  * mvn -f AuthService/ clean install

### How to run
* Make sure you have started rabbitMQ, you can use docker to do it. See https://hub.docker.com/_/rabbitmq/
  * Use the default port: 5672
  * The default user and password is guest/guest
* Run the following commands to start each service:
  * java -jar -Dspring.cloud.config.server.git.username=<Replace this with the username of the config files repository> -Dspring.cloud.config.server.git.password=<Replace this with the password of the cong files repository> Configuration/docker/Configuration-0.0.1-SNAPSHOT-spring-boot.jar
    * For the purpose of this project the git repository is this one.
    * The service by defaut will be exposed on port 8081. [If need to change it please see configuration instructions.](#configuration)
  * java -jar Discovery/docker/Discovery-0.0.1-SNAPSHOT-spring-boot.jar
    * The service by defaut will be exposed on port 8082. [If need to change it please see configuration instructions.](#configuration)
  * java -jar Gateway/docker/Gateway-0.0.1-SNAPSHOT-spring-boot.jar
    * The service by defaut will be exposed on port 8084. [If need to change it please see configuration instructions.](#configuration)
  * java -jar -DisMaster=true AuthService/docker/AuthService-0.0.1-SNAPSHOT-spring-boot.jar
    * The service by defaut will be exposed on port 9090. [If need to change it please see configuration instructions.](#configuration)
* When the auth services starts it will create a super admin
  * username: admin
  * password: @dm1n

### Running on Docker Compose
#### The starter is prepared to be used with docker compose:
* You need rabbitMQ image. See https://hub.docker.com/_/rabbitmq/
  * The image needs to be called rabbitmq
* Run the build.sh file included in this repo.
* Run docker-compose up
* On docker the services will be exposed in different ports than default:
  * Configuration: 8091
  * Discovery: 8092
  * Gateway: 8094
  * Auth Service: 8090

### Current Endpoints
Note: In order to call each endpoint you can do it by the gateway (recommended) or directly to the service, see both examples for each route.

#### POST
* [POST /user](#post-user)
* [POST /role](#post-role)
* [POST /login](#post-login)

#### PATCH
* [PATCH /user/:id](#patch-userid)
* [PATCH /role/:id](#patch-roleid)

#### DELETE
* [DELETE /user/:id](#delete-userid)
* [DELETE /role/:id](#delete-roleid)

#### GET
* [GET /user](#get-user)
* [GET /role](#get-role)

---
<a name="add-user"></a>


#### POST /user
[Back to Menu](#current-endpoints)

Creates a new user.

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
username     | String | Body         | Yes        | The username
password     | String | Body         | Yes        | The password (will be encrypted)
roles        | Array  | Body         | Yes        | The roles associated to the user

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
POST http://localhost:8084/auth/user - If calling through gateway
POST http://localhost:9090/user - If calling directly

{
	"username": "reader",
	"password": "reader",
	"roles": ["5b982fd7dfb3890f02eb7f00"]
}
```

---
<a name="add-role"></a>

#### POST /role
[Back to Menu](#current-endpoints)

Creates a new role.

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
roleCd       | String | Body         | Yes        | The role code
permissions  | Array  | Body         | Yes        | The permissions associated to the role, See that you can use a wildcard if needed. 

The current permissions are:
* add-user
* add-role
* update-user
* update-role
* delete-user
* delete-role
* get-user
* get-role

***You can use * as superadmin to have access to everything***

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
POST http://localhost:8084/auth/role - If calling through gateway
POST http://localhost:9090/role - If calling directly

{
	"roleCd": "writer",
	"permissions": ["add-*", "update-*", "delete-*"]
}
```
Other example
```
{
	"roleCd": "admin-for-roles",
	"permissions": ["role-*"]
}
```

---
<a name="update-user"></a>

#### PATCH /user/:id
[Back to Menu](#current-endpoints)

Updates an existing user.

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
password     | String | Body         | Yes        | The password (will be encrypted)
roles        | Array  | Body         | Yes        | The roles associated to the user
id           | String | Path         | Yes        | The Id role

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
PATCH https://localhost:8084/auth/user/5b97ee9ddfb3890c6b16256a - If calling through gateway
PATCH https://localhost:9090/user/5b97ee9ddfb3890c6b16256a - If calling directly

{
	"password": "reader",
	"roles": ["5b982fd7dfb3890f02eb7f00"]
}
```
---
<a name="update-role"></a>

#### PATCH /role/:id
[Back to Menu](#current-endpoints)

Updates an existing role

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
roleCd       | String | Body         | Yes        | The role code
permissions  | Array  | Body         | Yes        | The permissions associated to the role, See that you can use a wildcard if needed. 
id           | String | Path         | Yes        | The Id user

The current permissions are:
* add-user
* add-role
* update-user
* update-role
* delete-user
* delete-role
* get-user
* get-role

***You can use * as superadmin to have access to everything***

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
PATCH http://localhost:8084/auth/role/5b97efd3dfb3890c6eb19d2d - If calling through gateway
PATCH http://localhost:9090/role/5b97efd3dfb3890c6eb19d2d - If calling directly

{
	"roleCd": "writer",
	"permissions": ["add-*", "update-*", "delete-*"]
}
```
---
<a name="delete-role"></a>

#### DELETE /role/:id
[Back to Menu](#current-endpoints)

Deletes an existing role

***Parameters***
Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
id           | String | Path         | Yes        | The Id role

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
DELETE http://localhost:8084/auth/role/5b97efd3dfb3890c6eb19d2d - If calling through gateway
DELETE http://localhost:9090/role/5b97efd3dfb3890c6eb19d2d - If calling directly
```
---
<a name="delete-user"></a>

#### DELETE /user/:id
[Back to Menu](#current-endpoints)

Deletes an existing user

***Parameters***
Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
id           | String | Path         | Yes        | The Id user

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
DELETE http://localhost:8084/auth/user/5b97efd3dfb3890c6eb19d2d - If calling through gateway
DELETE http://localhost:9090/user/5b97efd3dfb3890c6eb19d2d - If calling directly
```
---

<a name="get-user"></a>

#### GET /user
[Back to Menu](#current-endpoints)

Gets all the users in a paginated way

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
size         | String | Query        | No         | The amount of items per page
page         | String | Query        | No         | The page number to see (zero-index)
sort         | String | Query        | No         | The sort object: sort=username,desc
search       | String | Query        | No         | The search query

**Comparison operators are in FIQL notation and some of them has an alternative syntax as well: See https://github.com/jirutka/rsql-parser**

* Equal to : ==
* Not equal to : !=
* Less than : =lt= or <
* Less than or equal to : =le= or <=
* Greater than operator : =gt= or >
* Greater than or equal to : =ge= or >=

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
GET localhost:8094/auth/user?sort=username,desc&page=0&size=1&search=username==a - If calling through gateway
GET http://localhost:9090/user - If calling directly
```

***Sample Response***
```
{
    "content": [
        {
            "id": "5b97ee9ddfb3890c6b16256a",
            "username": "admin",
            "password": "$2a$10$qufBNGdc/IOVH8/cyNDQe.eiNw/vOGGHpqLVyGcLle4BXXkl/PEUC",
            "roles": [
                "5b97ee9ddfb3890c6b162569"
            ],
            "token": null,
            "audience": null
        },
        {
            "id": "5b984383dfb38911afaa3588",
            "username": "reader",
            "password": "$2a$10$2LTkcXtbEe0Flj206RN10O0KbgUW4keu8/qTTa/WXLv2/rFMHwuLu",
            "roles": [
                "5b982fd7dfb3890f02eb7f00"
            ],
            "token": null,
            "audience": null
        }
    ],
    "pageable": {
        "sort": {
            "unsorted": true,
            "sorted": false
        },
        "pageSize": 20,
        "pageNumber": 0,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 1,
    "last": true,
    "totalElements": 2,
    "first": true,
    "sort": {
        "unsorted": true,
        "sorted": false
    },
    "numberOfElements": 2,
    "size": 20,
    "number": 0
}
```
---

<a name="get-role"></a>

#### GET /role
[Back to Menu](#current-endpoints)

Gets all the roles in a paginated way

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
x-auth       | String | Header       | Yes        | The beaear token (bearer token)
size         | String | Query        | No         | The amount of items per page
page         | String | Query        | No         | The page number to see (zero-index)
sort         | String | Query        | No         | The sort object: sort=username,desc
search       | String | Query        | No         | The search query

**Comparison operators are in FIQL notation and some of them has an alternative syntax as well: See https://github.com/jirutka/rsql-parser**

* Equal to : ==
* Not equal to : !=
* Less than : =lt= or <
* Less than or equal to : =le= or <=
* Greater than operator : =gt= or >
* Greater than or equal to : =ge= or >=

***Note that the auth prefix is added to the endpoint if its called through the gateway**

***Sample Request***
```
GET localhost:8094/auth/role?sort=roleCd,desc&page=0&size=1&search=roleCd==a - If calling through gateway
GET http://localhost:9090/role - If calling directly
```

***Sample Response***
```
{
    "content": [
        {
            "id": "5b982fd7dfb3890f02eb7f00",
            "roleCd": "reader",
            "permissions": [
                "get-*"
            ]
        }
    ],
    "pageable": {
        "sort": {
            "unsorted": false,
            "sorted": true
        },
        "pageSize": 1,
        "pageNumber": 0,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 3,
    "last": false,
    "totalElements": 3,
    "first": true,
    "sort": {
        "unsorted": false,
        "sorted": true
    },
    "numberOfElements": 1,
    "size": 1,
    "number": 0
}
```
---

<a name="login"></a>

#### POST /login
[Back to Menu](#current-endpoints)

Performs a login

***Parameters***

Param        | Type   | In           | Required?  | Description
---          | ---    | ---          | ---        | ---
username     | String | form data    | Yes        | The username
password     | String | form data    | Yes        | The password

***Sample Request***
```
POST http://localhost:8094/auth/login- If calling through gateway
POST http://localhost:9090/login - If calling directly
```

***Sample Response***
```
{
    "accessToken": "eyJraWQiOiJmODVmN2MzYS1iYjAyLTQ4ZDEtYjZmOS0wOTg2Y2E0NTM0YjEiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJlYXN5cnVuLmF1dGgiLCJhdWQiOiJlYXN5cnVuLnVzZXIiLCJleHAiOjE1MzY5NjA0OTMsImp0aSI6IjBaLW5iMFRIYnZtUzNnQzBWUkJDU0EiLCJpYXQiOjE1MzY5NTk4OTMsIm5iZiI6MTUzNjk1OTc3Mywic3ViIjoiYWRtaW4iLCJpZCI6IjViOTdlZTlkZGZiMzg5MGM2YjE2MjU2YSIsInNjb3BlIjpbIioiLCJ1c2VyLSoiLCJyb2xlLSoiXX0.UWeE9Ur2G7mIMd2pQSx1WIetEO59V3q0Mrg2NOy5OvFCU9oe-P4qlFns3aJROgGEfiqef_CK_dfiFJSodlSbtw8NilZGWXrZQUBVteCZkvt62obGO8dzxXGBfh2XZxrMTmAny2pXs1vcdPUJaWjUqO2drq9aqUMBgdbXzsAKBztPAG4JvxLBt6gR9sWcksogLOsPOVeG4sQOeR8IUUvmZ8GPpYX01vB9bbIlYj-Zj7oLMyuLbe7qf1QnuoBf-kHEpu3Q69jOatM7alJjZU_Wtlzmg5I7e586W7B5asTvhh6YPYD0RdpyLJr1agY8_avaYNU69mG2ex3wWBcUtlyU-w",
    "type": "bearer"
}
```