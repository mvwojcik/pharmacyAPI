INSERT INTO employees (first_name, last_name, login, password) VALUES
    ('Monika', 'Ratownika', 'monisia', '$2a$10$dLcke5LxG6V1h3UYVGhmm.bNdyGNxAYcoPeCvOVQwod5s8letZXYe'  ),
    ('Kinga', 'Robak', 'kingusia', '$2a$10$dLcke5LxG6V1h3UYVGhmm.bNdyGNxAYcoPeCvOVQwod5s8letZXYe'  );
INSERT INTO clients (first_name, last_name, address) VALUES
    ('Monika', 'Ratownika', 'Tutaj');

INSERT INTO medicines (is_Prescript, name) VALUES
(true, 'Apap'),
(false, 'Aspiryna'),
(false, 'Viagra'),
(true, 'Acodin');

insert into orders (id, client_id) values (1,1);
insert into medicine_order(amount, order_id, medicine_id) values
(1, 1, 1),
(2, 1, 2);