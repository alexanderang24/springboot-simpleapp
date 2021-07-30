CREATE SEQUENCE product_id_seq
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

CREATE TABLE product (
    id bigint NOT NULL,
    product_name character varying(256) NOT NULL,
    price numeric(16,6) NOT NULL,
    weight int NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY(id),
);

CREATE SEQUENCE transaction_id_seq
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

CREATE TABLE transaction (
    id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity int NOT NULL,
    sub_total numeric(16,6) NOT NULL,
    courier_cost numeric(16,6),
    total numeric(16,6),
    inquiry_code character varying(256) NOT NULL,
    status character varying(256) NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY(id),
    CONSTRAINT product_id FOREIGN KEY(product_id)
        REFERENCES product(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
);