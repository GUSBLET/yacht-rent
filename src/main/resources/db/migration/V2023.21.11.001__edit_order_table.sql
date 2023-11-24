DROP TABLE main.yacht_order_table;

ALTER TABLE main.order_table
    ADD yacht_id bigint;

ALTER TABLE main.order_table
    ADD CONSTRAINT fk_order_yacht_id
        FOREIGN KEY (yacht_id) REFERENCES main.yacht_table (id)
            ON DELETE CASCADE;