# 📱 Mobile Recharge Management System

A **console-based Java application** developed using **Core Java, JDBC**, and **MySQL**, simulating real-world mobile recharge platforms like PhonePe or Paytm. 
This project manages user recharges, plan selection, secure bank transactions, and recharge history.

---

## 🔧 Technologies Used

- 🟦 Java (JDK 17+)
- 🛢️ JDBC
- 🐬 MySQL
- 🧠 IntelliJ IDEA (IDE)

---

## 📁 Features

- ✅ Recharge any mobile number with operator selection
- 📲 Choose plans with validity & amount
- 💳 Bank account verification (UPI, IFSC, balance check)
- 🧾 Recharge history saved and viewable
- 🏦 Bank account creation via console
- 🔒 Secure transaction handling with rollback on failure

---

## 📌 MySQL Tables

```sql
-- Bank Account Table
CREATE TABLE bank_account (
  account_no VARCHAR(20) PRIMARY KEY,
  ifsc_code VARCHAR(11),
  upi_id VARCHAR(50),
  balance DECIMAL(10, 2)
);

-- Recharge Details Table
CREATE TABLE recharge_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  mobile_number VARCHAR(15),
  provider VARCHAR(50),
  plan VARCHAR(100),
  amount DECIMAL(10, 2),
  validity VARCHAR(50),
  upi_id VARCHAR(50),
  account_no VARCHAR(20),
  recharge_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

---

🚀 How to Run
Prerequisites
JDK 17 or above

MySQL Server (localhost)

IntelliJ IDEA

MySQL JDBC Driver

Setup Instructions
Clone this repo or extract the ZIP

bash
Copy
Edit
git clone https://github.com/your-username/mobile-recharge-system.git
Open in IntelliJ IDEA
Go to: File → Open → Select Project Folder

Configure Project SDK

File → Project Structure → Project SDK → Add JDK (if not already)

Add MySQL JDBC Driver

Right-click lib folder → Add as Library (if .jar is included)

OR add Maven dependency manually (if using Maven)

Create MySQL database

sql
Copy
Edit
CREATE DATABASE mobile_recharge_db;
USE mobile_recharge_db;
Run SQL Table Scripts (Above)

Run Main.java file
Follow the console menu to interact with the system.

📷 Sample Output
bash
Copy
Edit
Enter your mobile number: 9876543210
Select Network Provider:
1. Jio
2. Airtel
3. Vi
4. BSNL
> 2
Choose a plan:
1. ₹199 - 28 days
2. ₹399 - 56 days
> 1
Enter Account No: 1234567890
Enter IFSC: SBIN0001234
Enter UPI ID: user@upi

✅ Recharge Successful!
🙏 Thanks for recharging. Visit again!

🤝 Contributing
Pull requests are welcome. For major changes, open an issue first to discuss what you would like to change.

📬 Contact
Built by Suyog Pawara,
Feel free to reach out for collaboration or feedback.
