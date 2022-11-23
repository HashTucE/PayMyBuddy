/* Setting up PROD DB */

drop database if exists paymybuddy;
create database paymybuddy;

use paymybuddy;

create table users(
id int not null auto_increment,
email varchar(100) not null,
password varchar(100) not null,
balance decimal(10,2) not null default '0.00',
bank_name varchar(100),
account_number varchar(100),
primary key (id)
);

create table transactions(
id int not null auto_increment,
date datetime not null,
sender_id int not null,
beneficiary_id int not null,
amount decimal(10,2) not null,
description varchar(250),
primary key (id),
foreign key (sender_id) references users(id),
foreign key (beneficiary_id) references users(id)
);

commit;
