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
    photos_id      bigint references main.yacht_photo_table (id)
);

CREATE TABLE main.order_table
(
    id              bigserial primary key,
    price           real,
    order_confirmed boolean
);



CREATE TABLE main.order_yacht_table
(

    yacht_id bigint NOT NULL,
    order_id bigint NOT NULL,
    primary key (yacht_id, order_id),
    foreign key (yacht_id) references main.yacht_table(id),
    foreign key (order_id) references main.order_table(id)
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
    orders_id          bigint references main.order_table (id)
);

create table main.role_table
(
    id   bigserial primary key,
    role VARCHAR(20) NOT NULL unique
);

CREATE TABLE main.role_account_table
(
    account_id bigint NOT NULL,
    role_id bigint NOT NULL,
    primary key (account_id, role_id),
    foreign key (account_id) references main.account_table(id),
    foreign key (role_id) references main.role_table(id)
);

CREATE TABLE main.rent_timetable_order_table
(
    timetable_id bigint NOT NULL,
    order_id bigint NOT NULL,
    primary key (timetable_id, order_id),
    foreign key (timetable_id) references main.rent_timetable_table (id),
    foreign key (order_id) references main.order_table(id)
);

CREATE TABLE main.yacht_parameter_table
(
    id   bigserial primary key,
    name Varchar(100) unique
);

CREATE TABLE main.rent_timetable_yacht_table
(
    yacht_id bigint NOT NULL,
    rent_timetable_id bigint NOT NULL,
    primary key (yacht_id, rent_timetable_id),
    foreign key (yacht_id) references main.yacht_table(id),
    foreign key (rent_timetable_id) references main.rent_timetable_table(id)
);

CREATE TABLE main.yacht_type_yacht_table
(
    yacht_id bigint NOT NULL,
    yacht_type_id bigint NOT NULL,
    primary key (yacht_id, yacht_type_id),
    foreign key (yacht_id) references main.yacht_table(id),
    foreign key (yacht_type_id) references main.yacht_type_table (id)
);

CREATE TABLE main.yacht_harbor_table
(
    yacht_id bigint NOT NULL,
    harbor_id bigint NOT NULL,
    primary key (yacht_id, harbor_id),
    foreign key (yacht_id) references main.yacht_table(id),
    foreign key (harbor_id) references main.harbor_table (id)
);

CREATE TABLE main.yacht_parameter_yacht_table
(
    yacht_id bigint NOT NULL,
    yacht_parameter_id bigint NOT NULL,
    primary key (yacht_id, yacht_parameter_id),
    foreign key (yacht_id) references main.yacht_table(id),
    foreign key (yacht_parameter_id) references main.yacht_parameter_table (id)
);
CREATE TABLE yacht_photo_table
(
    id    bigserial primary key,
    photo bytea
);

CREATE TABLE yacht_type_table
(
    id   bigserial primary key,
    type Varchar(30) unique
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
    name      Varchar(50) unique not null,
    address   Varchar(150),
    longitude real,
    latitude  real
);

CREATE TABLE yacht_table
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

CREATE TABLE order_table
(
    id              bigserial primary key,
    price           real,
    order_confirmed boolean
);



CREATE TABLE order_yacht_table
(

    yacht_id bigint NOT NULL,
    order_id bigint NOT NULL,
    primary key (yacht_id, order_id),
    foreign key (yacht_id) references yacht_table(id),
    foreign key (order_id) references order_table(id)
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
    role VARCHAR(20) NOT NULL unique
);

CREATE TABLE role_account_table
(
    account_id bigint NOT NULL,
    role_id bigint NOT NULL,
    primary key (account_id, role_id),
    foreign key (account_id) references account_table(id),
    foreign key (role_id) references role_table(id)
);

CREATE TABLE rent_timetable_order_table
(
    timetable_id bigint NOT NULL,
    order_id bigint NOT NULL,
    primary key (timetable_id, order_id),
    foreign key (timetable_id) references rent_timetable_table (id),
    foreign key (order_id) references order_table(id)
);

CREATE TABLE yacht_parameter_table
(
    id   bigserial primary key,
    name Varchar(100) unique
);

CREATE TABLE rent_timetable_yacht_table
(
    yacht_id bigint NOT NULL,
    rent_timetable_id bigint NOT NULL,
    primary key (yacht_id, rent_timetable_id),
    foreign key (yacht_id) references yacht_table(id),
    foreign key (rent_timetable_id) references rent_timetable_table(id)
);

CREATE TABLE yacht_type_yacht_table
(
    yacht_id bigint NOT NULL,
    yacht_type_id bigint NOT NULL,
    primary key (yacht_id, yacht_type_id),
    foreign key (yacht_id) references yacht_table(id),
    foreign key (yacht_type_id) references yacht_type_table (id)
);

CREATE TABLE yacht_harbor_table
(
    yacht_id bigint NOT NULL,
    harbor_id bigint NOT NULL,
    primary key (yacht_id, harbor_id),
    foreign key (yacht_id) references yacht_table(id),
    foreign key (harbor_id) references harbor_table (id)
);

CREATE TABLE yacht_parameter_yacht_table
(
    yacht_id bigint NOT NULL,
    yacht_parameter_id bigint NOT NULL,
    primary key (yacht_id, yacht_parameter_id),
    foreign key (yacht_id) references yacht_table(id),
    foreign key (yacht_parameter_id) references yacht_parameter_table (id)
);



--
-- CREATE TABLE IF NOT EXISTS yacht_photo_table
-- (
--     id    bigserial primary key,
--     photo bytea
-- );
--
-- CREATE TABLE IF NOT EXISTS yacht_type_table
-- (
--     id   bigserial primary key,
--     type Varchar(30) unique
-- );
--
--
-- CREATE TABLE IF NOT EXISTS rent_timetable_table
-- (
--     id             bigserial primary key,
--     start_of_rent  date not null,
--     finish_of_rent date not null
-- );
--
--
-- CREATE TABLE IF NOT EXISTS harbor_table
-- (
--     id        bigserial primary key,
--     name      Varchar(50) unique not null,
--     address   Varchar(150),
--     longitude real,
--     latitude  real
-- );
--
-- CREATE TABLE IF NOT EXISTS yacht_table
-- (
--     id             bigserial primary key,
--     name           Varchar(30) not null unique,
--     age            smallint,
--     price_per_hour real,
--     length         real,
--     width          real,
--     crew           smallint,
--     captain        varchar(50) not null,
--     description    varchar(1000),
--     photos_id      bigint references yacht_photo_table (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS order_table
-- (
--     id              bigserial primary key,
--     price           real,
--     order_confirmed boolean
-- );
--
--
--
-- CREATE TABLE IF NOT EXISTS order_yacht_table
-- (
--
--     yacht_id bigint NOT NULL,
--     order_id bigint NOT NULL,
--     primary key (yacht_id, order_id),
--     foreign key (yacht_id) references yacht_table(id),
--     foreign key (order_id) references order_table(id)
-- );
--
--
-- CREATE TABLE IF NOT EXISTS account_table
-- (
--     id                 bigserial primary key,
--     name               VARCHAR(30)  NOT NULL,
--     last_name          VARCHAR(30)  NOT NULL,
--     email              VARCHAR(100) NOT NULL unique,
--     password           VARCHAR(300) NOT NULL,
--     phone_number       varchar(30)  not null unique,
--     avatar             bytea,
--     account_registered boolean,
--     account_confirmed  boolean,
--     orders_id          bigint references order_table (id)
-- );
--
-- create table IF NOT EXISTS role_table
-- (
--     id   bigserial primary key,
--     role VARCHAR(20) NOT NULL unique
-- );
--
-- CREATE TABLE IF NOT EXISTS role_account_table
-- (
--     account_id bigint NOT NULL,
--     role_id bigint NOT NULL,
--     primary key (account_id, role_id),
--     foreign key (account_id) references account_table(id),
--     foreign key (role_id) references role_table(id)
-- );
--
-- CREATE TABLE IF NOT EXISTS rent_timetable_order_table
-- (
--     timetable_id bigint NOT NULL,
--     order_id bigint NOT NULL,
--     primary key (timetable_id, order_id),
--     foreign key (timetable_id) references rent_timetable_table (id),
--     foreign key (order_id) references order_table(id)
-- );
--
-- CREATE TABLE IF NOT EXISTS yacht_parameter_table
-- (
--     id   bigserial primary key,
--     name Varchar(100) unique
-- );
--
-- CREATE TABLE IF NOT EXISTS rent_timetable_yacht_table
-- (
--     yacht_id bigint NOT NULL,
--     rent_timetable_id bigint NOT NULL,
--     primary key (yacht_id, rent_timetable_id),
--     foreign key (yacht_id) references yacht_table(id),
--     foreign key (rent_timetable_id) references rent_timetable_table(id)
-- );
--
-- CREATE TABLE IF NOT EXISTS yacht_type_yacht_table
-- (
--     yacht_id bigint NOT NULL,
--     yacht_type_id bigint NOT NULL,
--     primary key (yacht_id, yacht_type_id),
--     foreign key (yacht_id) references yacht_table(id),
--     foreign key (yacht_type_id) references yacht_type_table (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS yacht_harbor_table
-- (
--     yacht_id bigint NOT NULL,
--     harbor_id bigint NOT NULL,
--     primary key (yacht_id, harbor_id),
--     foreign key (yacht_id) references yacht_table(id),
--     foreign key (harbor_id) references harbor_table (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS yacht_parameter_yacht_table
-- (
--     yacht_id bigint NOT NULL,
--     yacht_parameter_id bigint NOT NULL,
--     primary key (yacht_id, yacht_parameter_id),
--     foreign key (yacht_id) references yacht_table(id),
--     foreign key (yacht_parameter_id) references yacht_parameter_table (id)
-- );
