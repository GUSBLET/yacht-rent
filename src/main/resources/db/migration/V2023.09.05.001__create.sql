CREATE TABLE main.account_table
(
    id                 bigserial primary key,
    name               VARCHAR(30)  NOT NULL,
    last_name          VARCHAR(30)  NOT NULL,
    email              VARCHAR(100) NOT NULL unique,
    password           VARCHAR(300) NOT NULL,
    phone_number       varchar(30)  not null,
    avatar             bytea,
    account_registered boolean,
    account_confirmed  boolean,
    company_name       varchar(100) unique
);

CREATE TABLE main.yacht_type_table
(
    id   bigserial primary key,
    type Varchar(30) unique
);

CREATE TABLE main.harbor_table
(
    id        bigserial primary key,
    name      Varchar(50) unique not null,
    address   Varchar(150),
    city      varchar(30),
    longitude real,
    latitude  real
);

CREATE TABLE main.creator_table
(
    id           bigserial primary key,
    company_name Varchar(100) not null unique,
    phone        Varchar(30)  not null unique,
    mail         Varchar(100) not null unique
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
    capacity       smallint,
    description    varchar(1000),
    account_id     bigint references main.account_table (id),
    type_yacht_id  bigint references main.yacht_type_table (id),
    creator_id     bigint references main.creator_table (id)
);

CREATE TABLE main.review_table
(
    id              bigserial primary key,
    number_of_stars smallint,
    description     Varchar(1000) not null unique,
    yacht_id        bigint references main.yacht_table (id),
    account_id      bigint references main.account_table (id)
);

CREATE TABLE main.facility_table
(
    id       bigserial primary key,
    name     Varchar(100) unique,
    count    smallint,
    yacht_id bigint references main.yacht_table (id)
);

CREATE TABLE main.yacht_photo_table
(
    id       bigserial primary key,
    photo    bytea,
    yacht_id bigint references main.yacht_table (id)
);

CREATE TABLE main.order_table
(
    id             bigserial primary key,
    price          real,
    status         varchar(20) not null,
    account_id     bigint references main.account_table (id),
    start_of_rent  timestamp,
    finish_of_rent timestamp
);

create table main.role_table
(
    id   bigserial primary key,
    role VARCHAR(20) NOT NULL unique
);

CREATE TABLE main.order_yacht_table
(
    yacht_id bigint NOT NULL,
    order_id bigint NOT NULL,
    primary key (yacht_id, order_id),
    foreign key (yacht_id) references main.yacht_table (id),
    foreign key (order_id) references main.order_table (id)
);


CREATE TABLE main.role_account_table
(
    account_id bigint NOT NULL,
    role_id    bigint NOT NULL,
    primary key (account_id, role_id),
    foreign key (account_id) references main.account_table (id),
    foreign key (role_id) references main.role_table (id)
);

CREATE TABLE main.yacht_harbor_table
(
    yacht_id  bigint NOT NULL,
    harbor_id bigint NOT NULL,
    primary key (yacht_id, harbor_id),
    foreign key (yacht_id) references main.yacht_table (id),
    foreign key (harbor_id) references main.harbor_table (id)
);
