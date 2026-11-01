CREATE DATABASE IF NOT EXISTS railway_reservation;
USE railway_reservation;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash CHAR(64) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS trains (
  id INT AUTO_INCREMENT PRIMARY KEY,
  train_number INT NOT NULL UNIQUE,
  train_name VARCHAR(120) NOT NULL,
  source_station VARCHAR(100) NOT NULL,
  destination_station VARCHAR(100) NOT NULL,
  departure_time VARCHAR(20) NOT NULL,
  arrival_time VARCHAR(20) NOT NULL,
  total_seats INT NOT NULL,
  available_seats INT NOT NULL,
  CHECK (available_seats >= 0),
  CHECK (total_seats >= available_seats)
);

CREATE TABLE IF NOT EXISTS bookings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  train_id INT NOT NULL,
  passenger_name VARCHAR(100) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(10) NOT NULL,
  journey_date DATE NOT NULL,
  seat_number INT NOT NULL,
  status ENUM('CONFIRMED','CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (train_id) REFERENCES trains(id)
);

INSERT IGNORE INTO trains(train_number,train_name,source_station,destination_station,departure_time,arrival_time,total_seats,available_seats) VALUES
(12601,'Chennai Express','Chennai','Mumbai','06:00','22:30',100,100),
(12602,'Bangalore Mail','Chennai','Bangalore','22:00','06:30',100,100),
(12603,'Coimbatore Intercity','Chennai','Coimbatore','07:15','14:00',80,80),
(12604,'Mysuru Express','Bangalore','Mysuru','08:30','11:45',60,60),
(12605,'Delhi Superfast','Chennai','New Delhi','18:00','20:00',120,120),
(12606,'Hyderabad Express','Chennai','Hyderabad','17:00','07:30',100,100),
(12607,'Madurai Express','Chennai','Madurai','21:00','06:00',90,90),
(12608,'Kochi Express','Bangalore','Kochi','19:00','08:00',80,80);
