# Banking-Application

Blueseal Bank ios my personal Banking application, hwere an end-user can registerm, log-in, deposit, withdraw, and track their spending.

BlueSeaBank is a Java-based desktop banking application developed to simulate core banking operations in a secure and user-friendly desktop environment. 



The system allows users to register, log in, and manage their bank accounts through an interactive dashboard. Core features include viewing account balances, making deposits and withdrawals, and recording transaction history, all supported by a MySQL database.

The application is built using object-oriented programming principles with a clear separation between the user interface, business logic, and database access layers. Database interactions are handled using prepared statements and transactional queries to maintain data integrity during financial operations.

BlueSeaBank connects to a locally hosted MySQL database, with setup automated through an SQL script that creates the required database and required tables. For security and portability, database credentials are not hardcoded; users are prompted to enter their MySQL login details at runtime.

The project is intended for educational and portfolio purposes, demonstrating practical skills in Java desktop application development, database design, and secure data handling.

## Database Configuration
1. Copy `config.properties.example` and rename it to `config.properties`
2. Update your MySQL username and password
3. Run the provided `database.sql` file
4. Launch the application
