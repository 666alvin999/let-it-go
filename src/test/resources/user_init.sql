DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
    USERNAME        VARCHAR(32) PRIMARY KEY,
    MAIL            VARCHAR(255) NOT NULL,
    BIRTH_DATE      VARCHAR(10) NOT NULL,
    USER_IDENTITY   VARCHAR(5) NOT NULL,
    PWD             VARCHAR(255) NOT NULL
);

INSERT INTO USERS VALUES ('ahamaide', 'mail', '2024-01-01', 'HE', 'password');