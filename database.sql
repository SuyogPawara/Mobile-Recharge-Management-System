
CREATE DATABASE IF NOT EXISTS mobile_recharge_db;
USE mobile_recharge_db;

CREATE TABLE IF NOT EXISTS recharge_details (
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

CREATE TABLE IF NOT EXISTS bank_account (
    account_no VARCHAR(20) PRIMARY KEY,
    ifsc_code VARCHAR(11),
    upi_id VARCHAR(50),
    balance DECIMAL(10, 2)
);

INSERT INTO bank_account (account_no, ifsc_code, upi_id, balance) 
VALUES ('1234567890', 'SBIN0001234', 'user@upi', 1000.00),
        ('2345678901', 'HDFC0005678', 'john@upi', 1500.00),
        ('3456789012', 'ICIC0003456', 'alice@upi', 800.00),
        ('4567890123', 'AXIS0007890', 'rahul@upi', 1200.00),
        ('5764832901', 'AXIS0007230', 'yash@upi', 1100.00),
        ('101', 'AXIS0002345', 'suyog@upi', 2000.00);
