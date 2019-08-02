CREATE DATABASE postgres

USE DATABASE postgres

CREATE TABLE public.tb_parqueadero
(
    "placaVehiculo" character varying(6) NOT NULL,
    "tipoVehiculo" character varying NOT NULL,
    "esAltoCilindraje" boolean NOT NULL,
    "horaFechaEntradaVehiculo" timestamp with time zone NOT NULL,
    PRIMARY KEY ("placaVehiculo")
)


CREATE TABLE public.tb_parqueadero
(
    "placaVehiculo" character varying COLLATE pg_catalog."default" NOT NULL,
    "tipoVehiculo" character varying COLLATE pg_catalog."default",
    "esAltoCilindraje" boolean,
    "horaFechaEntradaVehiculo" timestamp with time zone,
    CONSTRAINT tb_parqueadero_pkey PRIMARY KEY ("placaVehiculo")
)