INSERT INTO addresses(
    city, country, house_number, street)
VALUES ('Belgrade', 'Serbia', '10', 'Kralja Aleksandra I');

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');


INSERT INTO public.users(
    activated, deleted, email, first_log_in, is_admin, last_password_reset_date, password, phone_number, first_name,surname, address_id)
VALUES (true, false, 'user@userovic.com', false, false, null, 'password', '04203050236', 'userovic', 'Pera', 1);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);