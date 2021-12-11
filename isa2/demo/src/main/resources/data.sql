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
VALUES ('Belgrade', 'Serbia', '110', 'Bulevar Kralja Aleksandra I');
-- password - password
INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname)
VALUES (true, false, 'user@userovic.com', false, false, null, '$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC', '04203050236', 'userovic', 'Pera');

INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname)
VALUES (true, false, 'user1@userovic.com', false, false, null, '$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC', '04203050236', 'userovic', 'Pera');

INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname)
VALUES (true, false, 'user2@userovic.com', false, false, null, '$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC', '04203050236', 'userovic', 'Pera');

-- INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 1);

INSERT INTO client (id, loyalty_points, penalty, category) VALUES (2, 3, 1, 0);
insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, address_id)
values ('Fishing at Gradac', 'This is a beautiful adventure', 10, 10, './../../assets/images/pecanje.png', 1);

insert into
    adventure(id, instructor_bio, cancellation_fee, house_rules)
values (1,'This is instructor biography', 10, 'Some house rules');

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, address_id)
values ('Fishing Adventure', 'This is a beautiful adventure', 10, 10, './../../assets/images/pecanje1.jpg', 2);

insert into
    adventure(id, instructor_bio, cancellation_fee, house_rules)
values (2,'This is instructor biography', 10, 'Some house rules');

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, average_grade)
values ('VIKENDICA 1', 'This is a beautiful cottage', 10, 10, './../../assets/images/cottage1.jpg', 2);

INSERT INTO COTTAGE (id) VALUES (3);
INSERT INTO ROOM (id, number_of_beds, cottage_id) VALUES (1, 2, 3);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo)
values ('VIKENDICA 2', 'Incredible', 10, 10, './../../assets/images/cottage2.jpg');

INSERT INTO COTTAGE (id) VALUES (4);
INSERT INTO ROOM (id, number_of_beds, cottage_id) VALUES (2, 2, 4);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, average_grade)
values ('BROD 1', 'Incredible', 10, 10, './../../assets/images/yacht.jpg', 4);

INSERT INTO BOATS (id, type, length, engine_number, engine_power, max_speed, capacity, cancellation_fee, house_rules, fishing_equipment)
VALUES (5, 0, 20.0, 12.3, 200, 15, 15, 20, 'no rules', null);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo, average_grade)
values ('BROD 2', 'Perfect rest day on our boat', 10, 9, './../../assets/images/fishing-boat.jpg', 5);

INSERT INTO BOATS (id, type, length, engine_number, engine_power, max_speed, capacity, cancellation_fee, house_rules, fishing_equipment)
VALUES (6, 1, 20.0, 12.3, 200, 15, 15, 20, 'no pets', 'Hooks, lines, sinkers, fishing reel');

INSERT INTO periods (id, end_date, start_date) VALUES (1, '2008-11-11', '2008-11-12');

INSERT INTO reservations (additional_notes, number_of_guests, price,  client_id, reservation_entity_id, reserved_period_id)
VALUES   ('Additional note1', 3, 25, 2, 1, 1)
