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

INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname, username, address_id)
VALUES (true, false, 'user@userovic.com', false, false, null, 'password', '04203050236', 'userovic', 'Pera','userovic', 1);