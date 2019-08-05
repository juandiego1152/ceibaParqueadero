CREATE DATABASE postgres

USE DATABASE postgres

CREATE TABLE public.tb_parqueadero
(
    "placaVehiculo" character varying(6) COLLATE pg_catalog."default" NOT NULL,
    "tipoVehiculo" character varying COLLATE pg_catalog."default" NOT NULL,
    cilindraje int4range NOT NULL,
    "horaFechaEntradaVehiculo" timestamp with time zone NOT NULL,
    CONSTRAINT tb_parqueadero_pkey PRIMARY KEY ("placaVehiculo")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.tb_parqueadero
    OWNER to postgres;