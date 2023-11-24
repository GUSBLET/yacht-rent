ALTER TABLE main.order_table
    ADD payment_method varchar(20) CHECK (payment_method IN ('DBT', 'CD'));
