drop table if exists Category;
drop table if exists Item;

create table Category(
   name text primary key
);

create table Item(
   id integer primary key autoincrement,
   name text not null unique,
   category text references Category(name)
);

insert into Category(name) values('Produce');
insert into Category(name) values('Dairy');

insert into Item(name, category) values('potato', 'Produce');
insert into Item(name, category) values('tomato', 'Produce');
insert into Item(name, category) values('carrot', 'Produce');
insert into Item(name, category) values('milk', 'Dairy');
insert into Item(name, category) values('cheese', 'Dairy');
