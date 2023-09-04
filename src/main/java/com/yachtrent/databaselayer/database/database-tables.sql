CREATE TABLE main.account_table (
    id  serial   primary key,
    login VARCHAR(25) NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL
);


CREATE TABLE main.order_table(
    id bigserial primary key,
    custumer_name varchar(50) not null ,
    custumer_email varchar(50) not null ,
    custumer_phone_number varchar(20) not null ,
    price float
);

CREATE TABLE main.timetable_table(
    id bigserial primary key,
    start_of_rent date not null,
    finish_of_rent date not null
);

CREATE TABLE main.timetable_order_table(
    id bigserial primary key,
    order_id bigint references main.order_table(id) not null,
    timetable_id bigint references main.timetable_table(id) not null
);



