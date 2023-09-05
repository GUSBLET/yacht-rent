CREATE TABLE yacht_photo_table
(
    id    bigserial primary key,
    photo bytea
);

CREATE TABLE yacht_type_table
(
    id   bigserial primary key,
    type Varchar(30)
);


CREATE TABLE rent_timetable_table
(
    id             bigserial primary key,
    start_of_rent  date not null,
    finish_of_rent date not null
);


CREATE TABLE harbor_table
(
    id        bigserial primary key,
    name      Varchar(50),
    address   Varchar(150),
    longitude real,
    latitude  real
);

CREATE TABLE yacht_table
(
    id             bigserial primary key,
    name           Varchar(30),
    age            smallint,
    price_per_hour real,
    length         real,
    width          real,
    crew           smallint,
    captain        smallint,
    description    varchar(1000),
    photos_id      bigint references yacht_photo_table (id)
);

CREATE TABLE order_table
(
    id              bigserial primary key,
    price           real,
    order_confirmed boolean
);



CREATE TABLE order_yacht_table
(
    id       bigserial primary key,
    yacht_id bigint references yacht_table (id) not null,
    order_id bigint references order_table (id) not null
);


CREATE TABLE account_table
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

create table role_table
(
    id   bigserial primary key,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE role_account_table
(
    id         bigserial primary key,
    account_id bigint references account_table (id) not null,
    role_id    bigint references role_table (id)    not null
);





CREATE TABLE rent_timetable_order_table
(
    id           bigserial primary key,
    order_id     bigint references order_table (id)          not null,
    timetable_id bigint references rent_timetable_table (id) not null
);

CREATE TABLE yacht_parameter_table
(
    id   bigserial primary key,
    name Varchar(100)
);






CREATE TABLE rent_timetable_yacht_table
(
    id                bigserial primary key,
    yacht_id          bigint references yacht_table (id)          not null,
    rent_timetable_id bigint references rent_timetable_table (id) not null
);

CREATE TABLE yacht_type_yacht_table
(
    id            bigserial primary key,
    yacht_id      bigint references yacht_table (id)      not null,
    yacht_type_id bigint references yacht_type_table (id) not null
);

CREATE TABLE yacht_harbor_table
(
    id        bigserial primary key,
    yacht_id  bigint references yacht_table (id)  not null,
    harbor_id bigint references harbor_table (id) not null
);

CREATE TABLE yacht_parameter_yacht_table
(
    id                 bigserial primary key,
    yacht_id           bigint references yacht_table (id)           not null,
    yacht_parameter_id bigint references yacht_parameter_table (id) not null
);



