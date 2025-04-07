CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         stock INT NOT NULL,
                         city_id BIGINT NOT NULL,
                         FOREIGN KEY (city_id) REFERENCES city(id)
);