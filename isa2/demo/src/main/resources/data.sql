INSERT INTO AUTHORITY (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_BOATOWNER'),
       ('ROLE_CLIENT'),
       ('ROLE_COTTAGEOWNER'),
       ('ROLE_INSTRUCTOR');

INSERT INTO addresses(
    city, country, house_number, street)
VALUES ('Belgrade', 'Serbia', '10', 'Kralja Aleksandra I');

INSERT INTO addresses(
    city, country, house_number, street)
VALUES ('Belgrade', 'Serbia', '110', 'Bulevar Kralja Aleksandra I'),
       ('Novi Sad','Serbia','215','Janka Quelika'),
       ('Krusevac','Serbia','22','Seke gasic');
--        ('Novi Sad','Serbia', '10', 'Ulice ulicic')


-- password - password
INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname, address_id)
VALUES (true, false, 'user@userovic.com', false, false, null, '$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC', '04203050236', 'userovic', 'Pera', 1),
       (true,false,'user1@userovic.com',false,false,null,'$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC','06060xxxxxx','cottage','owner', 2),
       (true,false,'arhitekturaracunara111@gmail.com',false,false ,null, '$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC','094i9234kdf','arhi','tektura','3');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1),
                                                          (1, 2),
                                                          (2,5);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, address_id)
values ('Fishing at Gradac', 'This is a beautiful adventure', 10, 10, './../../assets/images/pecanje.png',1);

insert into
    adventure(id, instructor_bio, cancellation_fee, house_rules)
values (1,'This is instructor biography', 10, 'Some house rules');

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo,address_id)
values ('Fishing Adventure', 'This is a beautiful adventure', 10, 10, './../../assets/images/pecanje1.jpg',2);
--        ('Novi Sad | Lux Vikendica','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat',
--         5,15,'',4);

insert into
    adventure(id, instructor_bio, cancellation_fee, house_rules)
values (2,'This is instructor biography', 10, 'Some house rules');

insert into owner (category, loyalty_points, owner_type, id)
    values (0,0,1,2);

insert into client (category, loyalty_points, penalty, id)
values (0,0,0,3);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, average_grade, address_id)
values ('VIKENDICA 1', 'This is a beautiful cottage', 10, 10, './../../assets/images/cottage1.jpg', 2, 1);

INSERT INTO COTTAGE (id) VALUES (3);
INSERT INTO ROOM (number_of_beds, cottage_id) VALUES (2, 3);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, address_id)
values ('VIKENDICA 2', 'Incredible', 10, 10, './../../assets/images/cottage2.jpg', 1);

INSERT INTO COTTAGE (id) VALUES (4);
INSERT INTO ROOM (number_of_beds, cottage_id) VALUES (2, 4);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo,  address_id)
values ('BROD 1', 'Incredible', 10, 10, './../../assets/images/yacht.jpg',  1);

INSERT INTO BOATS (id, type, length, engine_number, engine_power, max_speed, capacity, cancellation_fee, house_rules, fishing_equipment)
VALUES (5, 0, 20.0, 12.3, 200, 15, 15, 20, 'no rules', null);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, address_id)
values ('BROD 2', 'Perfect rest day on our boat', 10, 9, './../../assets/images/fishing-boat.jpg',  1);

INSERT INTO BOATS (id, type, length, engine_number, engine_power, max_speed, capacity, cancellation_fee, house_rules, fishing_equipment)
VALUES (6, 1, 20.0, 12.3, 200, 15, 15, 20, 'no pets', 'Hooks, lines, sinkers, fishing reel');

