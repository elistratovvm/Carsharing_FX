--Drop all tables
DROP TABLE IF EXISTS public.aggregators CASCADE;
DROP TABLE IF EXISTS public.car_lease_point_details CASCADE;
DROP TABLE IF EXISTS public.car_types CASCADE;
DROP TABLE IF EXISTS public.cars CASCADE;
DROP TABLE IF EXISTS public.contract_details CASCADE;
DROP TABLE IF EXISTS public.contracts CASCADE;
DROP TABLE IF EXISTS public.customers CASCADE;
DROP TABLE IF EXISTS public.lease_points CASCADE;

--Create tables and add primary keys
CREATE TABLE public.aggregators (
	id    integer	    NOT NULL,
    name  varchar(20)   NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.aggregators
	ADD CONSTRAINT agr_pk PRIMARY KEY ( id );

CREATE TABLE public.car_lease_point_details (
    car_id  integer NOT NULL,
    lpt_id  integer NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.car_lease_point_details
	ADD CONSTRAINT clp_pk PRIMARY KEY ( car_id, lpt_id );

CREATE TABLE public.car_types (
    brand_and_model  varchar(40)    NOT NULL,
    rate_in_hour     numeric(7, 2)  NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.car_types
	ADD CONSTRAINT cte_pk PRIMARY KEY ( brand_and_model );

CREATE TABLE public.cars (
    id                  integer     NOT NULL,
    license_plate       varchar(6)  NOT NULL,
    technical_condition varchar(20) NOT NULL,
    fuel                integer     NOT NULL,
    cte_brand_and_model varchar(40) NOT NULL,
    agr_id              integer     NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cars
	ADD CONSTRAINT car_pk PRIMARY KEY ( id );

CREATE TABLE public.contract_details (
    contract_date        date       NOT NULL,
    ctt_id               integer    NOT NULL,
    car_id               integer    NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contract_details
    ADD CONSTRAINT cdl_pk PRIMARY KEY ( ctt_id, car_id );

CREATE TABLE public.contracts (
    agr_id  integer NOT NULL,
    ctr_id  integer NOT NULL,
    id      integer NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contracts
    ADD CONSTRAINT ctt_pk PRIMARY KEY ( id );

CREATE TABLE public.customers (
    id                  integer     NOT NULL,
    first_name          varchar(20) NOT NULL,
    last_name           varchar(20) NOT NULL,
    drivers_license     numeric(12) NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customers
	ADD CONSTRAINT ctr_pk PRIMARY KEY ( id );

CREATE TABLE public.lease_points (
    id            integer       NOT NULL,
    address       varchar(50)   NOT NULL,
    x_coordinate  integer       NOT NULL,
    y_coordinate  integer       NOT NULL,
    agr_id        integer       NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lease_points
	ADD CONSTRAINT lpt_pk PRIMARY KEY ( id );

--Add foreign keys
ALTER TABLE IF EXISTS public.cars
    ADD CONSTRAINT car_cte_fk FOREIGN KEY ( cte_brand_and_model )
        REFERENCES public.car_types ( brand_and_model );

ALTER TABLE IF EXISTS public.cars
    ADD CONSTRAINT car_agr_fk FOREIGN KEY ( agr_id )
        REFERENCES public.aggregators ( id );

ALTER TABLE IF EXISTS public.contract_details
    ADD CONSTRAINT cdl_car_fk FOREIGN KEY ( car_id )
        REFERENCES public.cars ( id );

ALTER TABLE IF EXISTS public.contract_details
    ADD CONSTRAINT cdl_ctt_fk FOREIGN KEY ( ctt_id )
        REFERENCES public.contracts ( id );

ALTER TABLE IF EXISTS public.car_lease_point_details
    ADD CONSTRAINT cll_car_fk FOREIGN KEY ( car_id )
        REFERENCES public.cars ( id );

ALTER TABLE IF EXISTS public.car_lease_point_details
    ADD CONSTRAINT cll_lpt_fk FOREIGN KEY ( lpt_id )
        REFERENCES public.lease_points ( id );

ALTER TABLE IF EXISTS public.contracts
    ADD CONSTRAINT ctt_agr_fk FOREIGN KEY ( agr_id )
        REFERENCES public.aggregators ( id );

ALTER TABLE IF EXISTS public.contracts
    ADD CONSTRAINT ctt_ctr_fk FOREIGN KEY ( ctr_id )
        REFERENCES public.customers ( id );

ALTER TABLE IF EXISTS public.lease_points
    ADD CONSTRAINT lpt_agr_fk FOREIGN KEY ( agr_id )
        REFERENCES public.aggregators ( id );