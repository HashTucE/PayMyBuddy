# PayMyBuddy
![paymybuddy](https://user-images.githubusercontent.com/95872501/205520112-13ed398c-f270-41b4-85c9-ab1e790231b9.png)

Webapp for Money Transfer with the following features :
- Register using an email
- Connection from an account store in a database
- Sign in/log off the application
- Add/delete "Buddies"
- Set/delete a bank account
- Fund PayMyBuddy's account
- Make transactions to a contact
- Transfer money to a bank account
- Automatic debit of 0.5% commission on each transaction

These features are not fonctionals :
- Remember me at sign in
- Forgot Password

# Prerequisites
- Java 11
- Maven 4.0.0
- MySQL 8.0.29

# Prepare the Database

- Install MySQL

- Then use this script to create the datatbase :
https://github.com/HashTucE/PayMyBuddy/blob/main/src/main/resources/database/Schema.sql

- And finally use this script to create some users, relations and transactions for test :
https://github.com/HashTucE/PayMyBuddy/blob/main/src/main/resources/database/Data.sql
- Then to sign in with any of these account, use the password '&&'.
- For this first release all commisions are collected by 'bill@paymybuddy.com'.
- I recommend to use first the account of 'test@paymybuddy.com' because he is concern by all transactions.

# Run the Application

- The datasource is set to this url : spring.datasource.url=jdbc:mysql://localhost:3306/paymybuddy?serverTimezone=UTC
- If needed you can check properties to : src/main/resources/application.properties

- Open a command prompt and run the following command : 
- mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.username=??? --spring.datasource.password=!!!"
- Replacing ??? by your username and !!! by your password of your datasource.
- And finally open your browser to access to this URL : http://localhost:8080/
- Then you can create a new account or use an existing from data script, ENJOY !

# UML Diagram
![Bayeul_Jérémy_1_uml_112022](https://user-images.githubusercontent.com/95872501/205518856-93160098-b087-46ea-92e5-0cf84c86704a.png)

# PDM Diagram
![Bayeul_Jérémy_2_mpd_112022](https://user-images.githubusercontent.com/95872501/205518862-2a903df8-d391-41a0-80ef-f213cdd26a70.png)

# JaCoCo Code Coverage
![Capture d’écran 2022-12-04 à 22 03 23](https://user-images.githubusercontent.com/95872501/205518878-9e83a3ed-3eb0-497f-84fb-25448d5f70a5.png)

# Technology Stack
![Capture d’écran 2022-12-05 à 01 19 56](https://user-images.githubusercontent.com/95872501/205524881-6a809029-414e-4a1f-b339-15154421f01a.png)






