/* Setting up Data */

insert into users(email,password,balance) values('bill@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 0.00);
insert into users(email,password,balance) values('test@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test2@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test3@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test4@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test5@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);

insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 3, 150, "resto");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 4, 150, "ciné");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 5, 150, "noel");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 3, 2, 150, "anniv");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 4, 2, 150, "dette");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 5, 2, 150, "loyer");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 3, 150, "jeu");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 4, 2, 150, "avance");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 5, 150, "course");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 4, 2, 150, "concert");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 3, 150, "resto");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 5, 150, "visite");

insert into user_contacts(user_id, contact_id) values(2, 3);
insert into user_contacts(user_id, contact_id) values(2, 4);
insert into user_contacts(user_id, contact_id) values(2, 5);
insert into user_contacts(user_id, contact_id) values(3, 2);
insert into user_contacts(user_id, contact_id) values(4, 2);
insert into user_contacts(user_id, contact_id) values(5, 2);


commit;