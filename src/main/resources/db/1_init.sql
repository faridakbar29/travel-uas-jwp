CREATE TABLE mytravel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    travel_Id VARCHAR(10) UNIQUE,
    travel_name VARCHAR(50),
    route TEXT,
    travel_schedule DATE
    );