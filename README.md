# SpringInventoryAPI

##Inventory RESTful Spring Web Service

This API web service uses REST calls to CREATE, READ, UPDATE and DELETE an item.

##Dependencies
- JDK 1.8
- maven

Libraries used:
- json-path
- spring jdbc
- spring boot framework
- spring boot starter web
- spring boot starter test
- spring boot starter jdbc
- spring boot starter data rest
- spring boot starter data jpa
- h2 database

##Unit Tests
Unit tests are written using Mockito to test the API.

##Item
`int id`
`String name`
`int quantity`

##Usage of API - CURL Commands

###CREATE
`curl -X POST -H "Content-Type: application/json" -d '{"name": "kitkat", "quantity": "55"}' " http://localhost:8080/item/"`

####Response
`{
    "id": 1,
    "name": "kitkat",
    "quantity": 55
}`

###READ - get one Item
`curl -X GET -H "Content-Type: application/json" "http://localhost:8080/item/1"`

####Response
`{
    "id": 1,
    "name": "kitkat",
    "quantity": 55
}`

###READ - get all items
`curl -X GET -H "Content-Type: application/json" "http://localhost:8080/"`

####Response
`[
    {
        "id": 1,
        "name": "kitkat",
        "quantity": 55
    },
    {
        "id": 2,
        "name": "mars",
        "quantity": 80
    }
]`

###Update
`curl -X PUT -H "Content-Type: application/json" -H -d '{"name": "kitkat", "quantity": "58"}' "http://localhost:8080/item/1"`

####Response
`{
    "id": 1,
    "name": "kitkat",
    "quantity": 58
}`

###DELETE
`curl -X DELETE -H "Content-Type: application/json" -d '' "http://localhost:8080/item/1"`

####Response
`Item id: 1 deleted!`
