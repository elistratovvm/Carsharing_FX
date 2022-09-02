--aggregators
INSERT INTO public.aggregators (id, name)
    VALUES (1, 'Тындекс');

--customers
INSERT INTO public.customers (id, first_name, last_name, drivers_license)
    VALUES (1, 'Иван', 'Иванов', '123456789123');

INSERT INTO public.customers (id, first_name, last_name, drivers_license)
    VALUES (2, 'Сидор', 'Сидоров', '987654321987');

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
    VALUES (1, '1-й Адрес', 235, 85, 1);

INSERT INTO public.lease_points (id, address, x_coordinate, y_coordinate, agr_id)
    VALUES (2, '2-й Адрес', 35, 320, 1);

INSERT INTO public.lease_points (id, address, x_coordinate, y_coordinate, agr_id)
    VALUES (3, '3-й Адрес', 335, 375, 1);

--cars
INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (1, 'AUTO01', 'Отличное', 100, 'Skoda Rapid', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (2, 'AUTO02', 'Хорошее', 90, 'Skoda Rapid', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (3, 'AUTO03', 'Хорошее', 75, 'Skoda Rapid', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (4, 'AUTO04', 'Отличное', 50, 'Volkswagen Polo', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (5, 'AUTO05', 'Хорошее', 65, 'Volkswagen Polo', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (6, 'AUTO06', 'Хорошее', 40, 'Renault Kaptur', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (7, 'AUTO07', 'Хорошее', 60, 'Renault Kaptur', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (8, 'AUTO08', 'Отличное', 95, 'Audi A3', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (9, 'AUTO09', 'Отличное', 100, 'BMW 520i', 1);

INSERT INTO public.cars (id, license_plate, technical_condition, fuel, cte_brand_and_model, agr_id)
    VALUES (10, 'AUTO10', 'Отличное', 85, 'Volvo C180', 1);

--car_lease_point_details
INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (1, 1);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (2, 2);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (3, 3);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (4, 1);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (5, 2);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (6, 3);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (7, 1);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (8, 2);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (9, 3);

INSERT INTO public.car_lease_point_details (car_id, lpt_id)
    VALUES (10, 1);

COMMIT;