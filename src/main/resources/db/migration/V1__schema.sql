CREATE SEQUENCE trx_id_seq
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

CREATE TABLE trx (
    id bigint NOT NULL,
    product_name character varying(256) NOT NULL,
    amount int NOT NULL,
    inquiry_code character varying(256) NOT NULL,
    trx_status character varying(256) NOT NULL,
    CONSTRAINT trx_pkey PRIMARY KEY (id)
);
