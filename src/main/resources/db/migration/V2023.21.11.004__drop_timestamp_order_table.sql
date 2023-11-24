ALTER TABLE main.order_table
    DROP COLUMN start_of_rent,
    DROP COLUMN finish_of_rent;

ALTER TABLE main.order_table
    ADD COLUMN start_of_rent TIME WITHOUT TIME ZONE,
    ADD COLUMN finish_of_rent TIME WITHOUT TIME ZONE;