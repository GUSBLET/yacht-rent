CREATE TABLE main.account_table (
    id  serial   primary key,
    login VARCHAR(25) NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL
);



