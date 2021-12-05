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


-- password - password
INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname, address_id)
VALUES (true, false, 'user@userovic.com', false, false, null, '$2a$10$OyHff7x9UR3atoDyz7.VAuKKSqZOB6ZZvL338Nic2WiPNi.0zdwqC', '04203050236', 'userovic', 'Pera', 1);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo)
values ('Fishing at Gradac', 'This is a beautiful adventure', 10, 10, './../../assets/images/pecanje.png');

insert into
    adventure(id, instructor_bio, cancellation_fee, house_rules)
values (1,'This is instructor biography', 10, 'Some house rules');

insert into
    entities(name, description,  max_guests, price_per_day, entity_photo)
values ('Fishing Adventure', 'This is a beautiful adventure', 10, 10, './../../assets/images/pecanje1.jpg');

insert into
    adventure(id, instructor_bio, cancellation_fee, house_rules)
values (2,'This is instructor biography', 10, 'Some house rules');