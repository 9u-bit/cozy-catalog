create database if not exists cozyCatalog;
use cozyCatalog;

create table if not exists user(
	username varchar(8) primary key,
    password varchar(20) not null
);

create table if not exists catalog(
	idCatalog int primary key auto_increment,
    name varchar(20) not null
);

create table if not exists record(
	idRecord int primary key auto_increment,
    idCatalog int,
    title varchar(255) not null,
    author varchar(255) not null,
    rating int check (rating between 0 and 5) not null,
    description text not null,
    foreign key (idCatalog) references catalog(idCatalog)
);

insert into user (username, password)
values ('ponkan9u', 'ineedtocodemore');

-- Stored Procedures

delimiter //
create procedure Login(
    in _username varchar(8),
    in _password varchar(20),
    out _success boolean
)
begin
	declare stored_password varchar(20);
    select password into stored_password
    from user
    where username = _username;
    
    if stored_password is not null and stored_password = _password then
        set _success = true; -- Login successful
    else
        set _success = false; -- Login failed
    end if;
end //
delimiter ;