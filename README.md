Project
=============
Vedio Link: https://youtu.be/CTi9ynZbaq4

List your group's member's below.

1.652110289 นรภัทร สายอ้าย

2.652110314 อภิรักษ์ ศรีรักษ์

3.652110334 ภูริพล ศรีกา


Project Explaination
=============

***User-service***

    AccountController: This is a Spring Boot REST controller that handles HTTP requests related to user accounts and authentication.

    AccountRepository: It's likely an interface that extends Spring Data JPA's CrudRepository or a similar interface. It provides methods for interacting with a database to perform CRUD (Create, Read, Update, Delete) operations on Account entities.

    AccountMapper: It's a component responsible for mapping data between Account entities and AccountDTO (Data Transfer Objects). This is commonly used to separate the representation of data for client requests and the actual data stored in the database.

    KafkaTemplate: It's part of the Spring Kafka framework and is used to produce messages to a Kafka topic, specifically LoginEvent messages in this case.

    Endpoints:
        GET /accounts: Retrieves a list of all accounts.
        GET /accounts/{id}: Retrieves an account by its ID.
        POST /accounts: Creates a new account.
        PATCH /accounts/{id}: Partially updates an existing account.
        PUT /accounts: Updates an existing account.
        DELETE /accounts/{id}: Deletes an existing account.
        POST /login: Handles user login and authentication.
        POST /addFunds: Adds funds to a user's account.
        POST /bonuses: Adds bonus funds to a user's account.
        POST /withdrawFunds: Withdraws funds from a user's account.
        GET /getUserData: Retrieves user data by their user ID.
        GET /getUserDataByUsername: Retrieves user data by their username.

***Loadbalancer-service***

    LoadBalancerConfiguration Class:

        It configures how load balancing works.
        Specifically, it makes sure that when there are multiple instances of a service, it prefers to use the same instance for subsequent requests.

    UserAccountServiceClient Interface:

        It's a way to talk to another service called "user-account-service" over HTTP.
        The createAccount() method sends a request to create a new account in the "user-account-service."

***Random-service***

    ตัวDomain Random โดยจะมีการสุ่มเลขทั้งหมด3ตัวเพื่อนำไปใช้ในpathต่างๆเช่น
        Get("/camt888s")เพื่อดึงข้อมูลที่มีมาแสดง
        POST("/camt888s")เพื่อการใส่ข้อมูลลงไป
        DELETE("/camt888s")เป็นการลบข้อมูล
        PUT("/camt888s")เพื่ออัพเดตข้อมูล

***User-History Service***

This service about history when customer login the information will be displayed on the History page.

Have about 3 file.

    History

            This file is used to declare variables and store values.

    UserHistoryController 

            This file is used to Get,Put,Delete,Post in path history.

    UserHistoryRepository

            This file is used to create Function.

