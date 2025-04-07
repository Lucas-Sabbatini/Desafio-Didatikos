CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      cpf VARCHAR(14) UNIQUE NOT NULL,
                      role ENUM('ADMIN', 'USER') NOT NULL
);