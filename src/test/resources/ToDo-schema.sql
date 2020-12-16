drop table if exists employee CASCADE;
drop table if exists task CASCADE;

create table employee (id bigint PRIMARY KEY AUTO_INCREMENT, name varchar(255) not null);
create table task (id bigint PRIMARY KEY AUTO_INCREMENT, description varchar(255) not null, finished boolean not null, title varchar(255) not null, employee_id bigint);