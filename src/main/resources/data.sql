INSERT INTO spring.user(id, username, password, algorithm)
VALUES (1, 'john', '$2a$10$SLWhLJ7/QEHW7NnnZq6c6.x4ly99PTpNNcrsXujk6buG3iCFk8f8y', 'BCRYPT');
INSERT INTO spring.authority VALUES (1, 'READ', 1);
INSERT INTO spring.authority VALUES (2, 'WRITE', 1);

INSERT INTO spring.product VALUES (1, 'Chocolate', '10', 'USD');
