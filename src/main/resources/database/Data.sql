/* Setting up Data */

truncate table users;
insert into users(email,password,balance) values('bill@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 0.00);
insert into users(email,password,balance) values('test@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test2@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test3@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test4@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);
insert into users(email,password,balance) values('test5@paymybuddy.com', '$2a$12$jyCNVUyfvfUS88xBHepybOgAv0MDOUcZh.ihIi0vYUelz/cfSSZzO', 10000.00);


truncate table transactions;
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 3, 150, "test1");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 4, 150, "test2");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 5, 150, "test3");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 3, 2, 150, "test4");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 4, 2, 150, "test5");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 5, 2, 150, "test6");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 3, 150, "test7");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 4, 2, 150, "test8");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 5, 150, "test9");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 4, 2, 150, "test10");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 3, 150, "test11");
insert into transactions(date, sender_id, beneficiary_id, amount, description) values(CURRENT_TIMESTAMP, 2, 5, 150, "test12");

truncate table user_contacts;
insert into user_contacts(user_id, contact_id) values(2, 3);
insert into user_contacts(user_id, contact_id) values(2, 4);
insert into user_contacts(user_id, contact_id) values(2, 5);
insert into user_contacts(user_id, contact_id) values(3, 2);
insert into user_contacts(user_id, contact_id) values(4, 2);
insert into user_contacts(user_id, contact_id) values(5, 2);


commit;