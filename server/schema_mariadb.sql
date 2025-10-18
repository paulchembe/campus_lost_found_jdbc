CREATE DATABASE IF NOT EXISTS campus_lost_found;
USE campus_lost_found;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(80) NOT NULL,
  last_name VARCHAR(80) NOT NULL,
  nrc VARCHAR(50) NOT NULL,
  student_number VARCHAR(50) NOT NULL,
  email VARCHAR(120),
  phone VARCHAR(40),
  is_admin TINYINT(1) DEFAULT 0,
  password_hash VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type ENUM('LOST','FOUND') NOT NULL,
  title VARCHAR(255),
  description TEXT,
  category VARCHAR(80),
  location VARCHAR(255),
  date BIGINT,
  photo_uri VARCHAR(1024),
  contact VARCHAR(255),
  status ENUM('OPEN','RETURNED','CONFIRMED') DEFAULT 'OPEN',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE match_hints (
  id INT AUTO_INCREMENT PRIMARY KEY,
  lost_id INT,
  found_id INT,
  score INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- add example admin
INSERT INTO users (first_name,last_name,nrc,student_number,email,phone,is_admin,password_hash) VALUES
('Admin','User','0000000000','ADMIN001','admin@campus.local','+260000000000',1,'admin123');
