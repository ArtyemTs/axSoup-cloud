create table if not exists Ingredient (
 id varchar(4) not null,
 name varchar(25) not null,
 type varchar(10) not null
);
create table if not exists Soup (
 id identity,
 name varchar(50) not null,
 createdAt timestamp not null
);
create table if not exists Soup_Ingredients (
 soup bigint not null,
 ingredient varchar(4) not null
);
alter table Soup_Ingredients
 add foreign key (soup) references Soup(id);
alter table Soup_Ingredients
 add foreign key (ingredient) references Ingredient(id);
create table if not exists Soup_Order (
 id identity,
 deliveryName varchar(50) not null,
 deliveryStreet varchar(50) not null,
 deliveryCity varchar(50) not null,
 deliveryState varchar(2) not null,
 deliveryZip varchar(10) not null,
 ccNumber varchar(16) not null,
 ccExpiration varchar(5) not null,
 ccCVV varchar(3) not null,
 placedAt timestamp not null
);
create table if not exists Soup_Order_Soups (
 soupOrder bigint not null,
 soup bigint not null
);
alter table Soup_Order_Soups
 add foreign key (soupOrder) references Soup_Order(id);
alter table Soup_Order_Soups
 add foreign key (soup) references Soup(id);