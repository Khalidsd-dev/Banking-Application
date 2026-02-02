# BlueSeal Bank Desktop Application

Eish, this project is basically a desktop bank app made with Java Swing. You can register, log in, and check your account balance, your preferred account type, and your preferred currency. The app runs on a MySQL database, so all your data stays safe and can be loaded anytime. When you log in, the dashboard will show your balance with the right currency symbol automatically. This project is perfect for learning how to build a proper bank system on your own computer and also to see how desktop apps talk to a database using Java.

It's simple but solid: handles users, accounts, balances, and currencies, and shows how to work with Java OOP and JDBC.

---

## Prerequisites

* Java JDK 8 or later
* NetBeans IDE (recommended)
* MySQL Server 8.0+
* MySQL Connector/J (JDBC driver)

---

## Project Structure

```
BlueSealBank/
├─ src/
│  ├─ bluesealbank/
│  │  ├─ DBPrompt.java
│  │  ├─ ConnectDatabase.java
│  │  ├─ frmlogin.java
│  │  ├─ etc.java
│  │  └─ etc.java
│  │  └─ --and more...
│  └─ settings/
│     └─ AppSettings.java
└─ README.md
```

---

## Database Setup (Local)

Start your MySQL server, then run the following script in MySQL Workbench or the MySQL CLI:

```sql
CREATE DATABASE bluesealbank;
USE bluesealbank;

-- Users table
CREATE TABLE `users` (
  `Account_Number` bigint NOT NULL AUTO_INCREMENT,
  `User_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `Name` varchar(100) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Password` varchar(255) NOT NULL,
  `balance` int NOT NULL,
  PRIMARY KEY (`User_id`),
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `unique_email` (`Email`),
  UNIQUE KEY `Account_Number` (`Account_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=100014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- User details table
CREATE TABLE `userdetails` (
  `Detail_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `User_id` binary(16) NOT NULL,
  `Date_of_Birth` date NOT NULL,
  `Residential_Address` text NOT NULL,
  `Preferred_Account_Type` set('Savings','Checking','Credit') NOT NULL,
  `Preferred_Currency_Type` set('USD','ZAR','EUR','GBP') NOT NULL,
  `Nationality` varchar(100) NOT NULL,
  `ID_Number` varchar(50) NOT NULL,
  `Created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `surname` varchar(50) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `gender` varchar(25) NOT NULL,
  `Balance` decimal(15,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`Detail_id`),
  UNIQUE KEY `ID_Number` (`ID_Number`),
  UNIQUE KEY `unique_email` (`Email`),
  KEY `User_id` (`User_id`),
  CONSTRAINT `fk_userdetails_email` FOREIGN KEY (`Email`) REFERENCES `users` (`Email`) ON DELETE CASCADE,
  CONSTRAINT `userdetails_ibfk_1` FOREIGN KEY (`User_id`) REFERENCES `users` (`User_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample user
INSERT INTO users (Name, Email, Password, balance)
VALUES ('Test User', 'test@bank.com', '1234', 5000);

INSERT INTO userdetails (User_id, Date_of_Birth, Residential_Address, Preferred_Account_Type, Preferred_Currency_Type, Nationality, ID_Number, surname, Email, gender, Balance)
VALUES ((SELECT User_id FROM users WHERE Email='test@bank.com'), '1990-01-01', '123 Main St', 'Savings', 'ZAR', 'South African', '1234567890', 'Doe', 'test@bank.com', 'Male', 5000.00);
```

> Note: Passwords are stored in plain text for academic/demo purposes only.

---

## Configuration

<img width="619" height="486" alt="demo new snap" src="https://github.com/user-attachments/assets/8fc9b42c-4a03-4940-81dd-00c7ac79d35a" />

When the application starts, a database prompt appears. Enter your local MySQL credentials:

* **URL**: `jdbc:mysql://localhost:3306/bluesealbank`
* **User**: `root` (or your MySQL username)
* **Password**: your MySQL password

Click **Save** to continue.

---

## Option 1 Running the Project

### NetBeans (Recommended)

1. Open NetBeans
2. File → Open Project
3. Select the `BlueSealBank` folder
4. Right-click the project → Run

### Options 2 Command Line

```bash
javac -cp ".;mysql-connector-j.jar" bluesealbank/*.java
java -cp ".;mysql-connector-j.jar" bluesealbank.DBPrompt
```

Ensure the MySQL JDBC driver is on the classpath.

---

## Test Login

```
Email:    test@bank.com
Password: 1234
```

After login, the dashboard loads and displays the balance with the correct currency symbol.

---

## Supported Currencies

| Code | Symbol |
| ---- | ------ |
| USD  | $      |
| ZAR  | R      |
| EUR  | €      |
| GBP  | £      |

Currency is stored as an abbreviation in the database and mapped to symbols via the `AppSettings` class.

---

## Features

* User authentication
* MySQL-backed data storage
* Dynamic currency symbol display
* Modular Java OOP design

---

## Limitations

* Plain-text passwords (academic use only)
* No transaction history
* Limited validation

---

## Author

**Shadrack** -
Systems Development Student 
Final Year Project – Desktop Application Development
---

## License

Educational use only.
