# SpringInventoryAPI

##RESTful Spring Web Service for Invenotry

This API web service uses REST calls to CREATE, READ, UPDATE and DELETE an item.

##Dependencies
JDK 1.8
maven

Libraries used:
- junit
- mockito
- spring framework boot
- spring framework test

##Usage - CURL Commands

###CREATE an Item

'curl -X POST -H "Content-Type: application/json" -d '{"name": "kitkat", "quantity": "55"}' "http://localhost:8080/item/'

Response:
'{
    "id": 1,
    "name": "kitkat",
    "quantity": 55
}'
