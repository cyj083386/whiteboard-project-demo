CREATE TABLE teachers (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(50) NOT NULL UNIQUE,
                          password VARCHAR(100) NOT NULL
);

CREATE TABLE classrooms (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            code VARCHAR(6) NOT NULL UNIQUE,
                            teacher_id INT NOT NULL,
                            FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

INSERT INTO teachers (username, password) VALUES ('teacher1', '$2a$12$qhx4KBRsDSd8qDXrCput3uZGhczej5t4aOtycYRVQsNR4REXLFZAy'); -- 암호: "password"를 BCrypt로 인코딩한 값
INSERT INTO classrooms (code, teacher_id) VALUES ('024588', 1);