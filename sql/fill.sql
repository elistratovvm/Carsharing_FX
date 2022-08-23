--aggregators
INSERT INTO public.aggregators (id, name)
    VALUES ('a01', 'Тындекс');

--customers
INSERT INTO public.customers (id, first_name, last_name, drivers_license)
    VALUES ('c000001', 'Иван', 'Иванов', '123456789123');

INSERT INTO public.customers (id, first_name, last_name, drivers_license)
    VALUES ('c000002', 'Сидор', 'Сидоров', '987654321987');

--car_types
INSERT INTO public.car_types (brand_and_model, rate_in_hour)
    VALUES ('Skoda Rapid', 5);

INSERT INTO public.car_types (brand_and_model, rate_in_hour)
    VALUES ('Volkswagen Polo', 5);

INSERT INTO public.car_types (brand_and_model, rate_in_hour)
    VALUES ('Renault Kaptur', 10);

INSERT INTO public.car_types (brand_and_model, rate_in_hour)
    VALUES ('Audi A3', 15);

INSERT INTO public.car_types (brand_and_model, rate_in_hour)
    VALUES ('BMW 520i', 30);

INSERT INTO public.car_types (brand_and_model, rate_in_hour)
    VALUES ('Volvo C180', 30);

--lease_points
INSERT INTO public.lease_points (id, address, x_coordinate, y_coordinate, agr_id)
    VALUES ('p0001', '1-й Адрес', 235, 85, 'a01');

INSERT INTO public.lease_points (id, address, x_coordinate, y_coordinate, agr_id)
    VALUES ('p0002', '2-й Адрес', 35, 320, 'a01');

INSERT INTO public.lease_points (id, address, x_coordinate, y_coordinate, agr_id)
    VALUES ('p0003', '3-й Адрес', 335, 375, 'a01');

--cars
INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00001', 'AUTO01', 'Отличное', 100, 'Skoda Rapid');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00002', 'AUTO02', 'Хорошее', 90, 'Skoda Rapid');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00003', 'AUTO03', 'Хорошее', 75, 'Skoda Rapid');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00004', 'AUTO04', 'Отличное', 50, 'Volkswagen Polo');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00005', 'AUTO05', 'Хорошее', 65, 'Volkswagen Polo');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00006', 'AUTO06', 'Хорошее', 40, 'Renault Kaptur');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00007', 'AUTO07', 'Хорошее', 60, 'Renault Kaptur');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00008', 'AUTO08', 'Отличное', 95, 'Audi A3');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00009', 'AUTO09', 'Отличное', 100, 'BMW 520i');

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model)
    VALUES ('c00010', 'AUTO10', 'Отличное', 85, 'Volvo C180');

--car_lease_point_details
INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00001', 'p0001');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00002', 'p0002');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00003', 'p0003');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00004', 'p0001');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00005', 'p0002');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00006', 'p0003');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00007', 'p0001');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00008', 'p0002');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00009', 'p0003');

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES ('c00010', 'p0001');

--contracts
INSERT INTO public.contracts (agr_id, ctr_id, id)
    VALUES ('a01', 'c000001', 'p000000');

COMMIT;