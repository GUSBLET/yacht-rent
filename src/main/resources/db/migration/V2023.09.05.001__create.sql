CREATE TABLE IF NOT EXISTS account_table (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(25) NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS role_table (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(30) CHECK (role IN ('ANONYMOUS', 'USER', 'ADMIN', 'MODERATOR'))
);

CREATE TABLE IF NOT EXISTS account_role (
    account_id BIGINT NOT NULL,
    role_id BIGINT  NOT NULL,
    PRIMARY KEY (account_id, role_id),
    FOREIGN KEY (account_id) REFERENCES account_table(id),
    FOREIGN KEY (role_id) REFERENCES role_table(id)
);

CREATE TABLE IF NOT EXISTS "order" (
    id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    customer_email VARCHAR(50) NOT NULL,
    customer_phone_number VARCHAR(20) NOT NULL,
    price FLOAT
);

CREATE TABLE IF NOT EXISTS timetable (
    id BIGSERIAL PRIMARY KEY,
    start_of_rent timestamp(6) NOT NULL,
    finish_of_rent timestamp(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS timetable_order (
    order_id BIGINT NOT NULL,
    timetable_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, timetable_id),
    FOREIGN KEY (order_id) REFERENCES "order"(id),
    FOREIGN KEY (timetable_id) REFERENCES timetable(id)
);
