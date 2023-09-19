CREATE TABLE main.token_table
(
    id                 bigserial primary key,
    token              VARCHAR(100) NOT NULL,
    time_of_creation   timestamp,
    lifetime           timestamp,
    confirmation_at    timestamp,
    account_id         bigint references main.account_table(id) on delete cascade
);

create unique index token_index on main.token_table(token);
create unique index email_index on main.account_table(id, email);
