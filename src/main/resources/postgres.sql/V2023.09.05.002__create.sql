-- CREATE TABLE IF NOT EXISTS account_table (
--     id BIGSERIAL PRIMARY KEY,
--     login VARCHAR(25) NOT NULL,
--     email TEXT NOT NULL,
--     password TEXT NOT NULL,
--     role VARCHAR(20) NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS role_table (
--     id BIGSERIAL PRIMARY KEY,
--     role VARCHAR(30) CHECK (role IN ('ANONYMOUS', 'USER', 'ADMIN', 'MODERATOR'))
-- );
--
-- CREATE TABLE IF NOT EXISTS account_role (
--     account_id BIGINT NOT NULL,
--     role_id BIGINT  NOT NULL,
--     PRIMARY KEY (account_id, role_id),
--     FOREIGN KEY (account_id) REFERENCES account_table(id),
--     FOREIGN KEY (role_id) REFERENCES role_table(id)
-- );
--
-- CREATE TABLE IF NOT EXISTS "order" (
--     id BIGSERIAL PRIMARY KEY,
--     customer_name VARCHAR(50) NOT NULL,
--     customer_email VARCHAR(50) NOT NULL,
--     customer_phone_number VARCHAR(20) NOT NULL,
--     price FLOAT
-- );
--
-- CREATE TABLE IF NOT EXISTS timetable (
--     id BIGSERIAL PRIMARY KEY,
--     start_of_rent timestamp(6) NOT NULL,
--     finish_of_rent timestamp(6) NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS timetable_order (
--     order_id BIGINT NOT NULL,
--     timetable_id BIGINT NOT NULL,
--     PRIMARY KEY (order_id, timetable_id),
--     FOREIGN KEY (order_id) REFERENCES "order"(id),
--     FOREIGN KEY (timetable_id) REFERENCES timetable(id)
-- );



CREATE TABLE main.yacht_photo_table
(
    id    bigserial primary key,
    photo bytea
);

CREATE TABLE main.yacht_type_table
(
    id   bigserial primary key,
    type Varchar(30) unique
);


CREATE TABLE main.rent_timetable_table
(
    id             bigserial primary key,
    start_of_rent  date not null,
    finish_of_rent date not null
);


CREATE TABLE main.harbor_table
(
    id        bigserial primary key,
    name      Varchar(50) unique not null,
    address   Varchar(150),
    longitude real,
    latitude  real
);

CREATE TABLE main.yacht_table
(
    id             bigserial primary key,
    name           Varchar(30) not null unique,
    age            smallint,
    price_per_hour real,
    length         real,
    width          real,
    crew           smallint,
    captain        varchar(50) not null,
    description    varchar(1000),
    photos_id      bigint references yacht_photo_table (id)
);

CREATE TABLE main.order_table
(
    id              bigserial primary key,
    price           real,
    order_confirmed boolean
);



CREATE TABLE main.order_yacht_table
(
    id       bigserial primary key,
    yacht_id bigint references yacht_table (id) not null,
    order_id bigint references order_table (id) not null
);


CREATE TABLE main.account_table
(
    id                 bigserial primary key,
    name               VARCHAR(30)  NOT NULL,
    last_name          VARCHAR(30)  NOT NULL,
    email              VARCHAR(100) NOT NULL unique,
    password           VARCHAR(300) NOT NULL,
    phone_number       varchar(30)  not null unique,
    avatar             bytea,
    account_registered boolean,
    account_confirmed  boolean,
    orders_id          bigint references order_table (id)
);

create table main.role_table
(
    id   bigserial primary key,
    role VARCHAR(20) NOT NULL unique
);

CREATE TABLE main.role_account_table
(
    id         bigserial primary key,
    account_id bigint references account_table (id) not null,
    role_id    bigint references role_table (id)    not null
);



CREATE TABLE main.rent_timetable_order_table
(
    id           bigserial primary key,
    order_id     bigint references order_table (id)          not null,
    timetable_id bigint references rent_timetable_table (id) not null
);

CREATE TABLE main.yacht_parameter_table
(
    id   bigserial primary key,
    name Varchar(100) unique
);



CREATE TABLE main.rent_timetable_yacht_table
(
    id                bigserial primary key,
    yacht_id          bigint references yacht_table (id)          not null,
    rent_timetable_id bigint references rent_timetable_table (id) not null
);

CREATE TABLE main.yacht_type_yacht_table
(
    id            bigserial primary key,
    yacht_id      bigint references yacht_table (id)      not null,
    yacht_type_id bigint references yacht_type_table (id) not null
);

CREATE TABLE main.yacht_harbor_table
(
    id        bigserial primary key,
    yacht_id  bigint references yacht_table (id)  not null,
    harbor_id bigint references harbor_table (id) not null
);

CREATE TABLE main.yacht_parameter_yacht_table
(
    id                 bigserial primary key,
    yacht_id           bigint references yacht_table (id)           not null,
    yacht_parameter_id bigint references yacht_parameter_table (id) not null
);
