This is a SpringBoot based application.
The application loads message from source folder into database after 2 minutes.
Application derives the spam statics in every 3 minutes and loads data into database.
The application also derives spam trends in every 5 minutes and loads into database.

Below is the instruction to run this application.

1. Create schema in the MYSQL database with name nns_sched
2. Execute the C:\33\nns_java\nns_sched\nns\src\main\resources\schema.sql into MySQL database
3. Open the C:\33\nns_java\nns_sched\nns\src\main\resources\config\application-dev.properties 
   and update the following properties 
   a) spring.datasource.password with the password of root user.
   b) message.file.src 
   c) message.file.proc.success
   d) message.file.proc.failed
   e) logging.path
5) Finally run mvnw spring-boot:run


   
   "# experiment" 
