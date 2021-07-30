# SpringBoot SimpleApp
Simple SpringBoot App that showcase the usage of H2 database with Flyway, RESTful API, external API caching, exception handler, logging, and custom validator annotation.

## APIs

### Inquiry
```
curl --location --request POST 'http://localhost:8080/app/transaction/inquiry' \
--header 'Content-Type: application/json' \
--data-raw '{
    "product_id": 1,
    "quantity": 1
}'
```

### Payment
```
curl --location --request POST 'http://localhost:8080/app/transaction/payment' \
--header 'Content-Type: application/json' \
--data-raw '{
    "inquiry_code":"40d633c7-1c53-4d55-8636-a5b8251a5d2b",
    "courier_cost": 10000
}'
```

### Courier Cost
```
curl --location --request GET 'http://localhost:8080/app/transaction/courier?weight=1000&courier=jne'
```
