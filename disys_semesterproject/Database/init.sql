drop table	public.station ;
drop table	public.charging;
drop table	public.customer;

CREATE TABLE public.station (
	id serial4 NOT NULL,
	available bool NULL,
	CONSTRAINT station_pkey PRIMARY KEY (id)
);

CREATE TABLE public.customer (
	id serial4 NOT NULL,
	firstname varchar(50) NULL,
	lastname varchar(50) NULL,
	address varchar(150) NULL,
	zip int4 NULL,
	city varchar(50) NULL,
	country varchar(100),
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);

CREATE TABLE public.charging (
	id serial4 NOT NULL,
	customer_id int4 NULL,
	kwh float8 NULL,
	chargingdate date NULL,
	station_id int4 NULL,
	CONSTRAINT charging_pkey PRIMARY KEY (id)
);

insert into customer (Firstname, Lastname, Address, ZIP, City, Country) VALUES ('Tom','Turbo','Hauptstraße 1',4330,'Düsenbach','Österreich');
insert into customer (Firstname, Lastname, Address, ZIP, City, Country) VALUES ('Franz','Muster','Am Bach 5',4190,'Hasendorf','Österreich');
insert into customer (Firstname, Lastname, Address, ZIP, City, Country) VALUES ('Susi','Huber','Kirchenplatz 2',2140,'Altenhofen','Schweiz');

select * from customer c ;

insert into station (available) VALUES (TRUE);
insert into station (available) VALUES (TRUE);
insert into station (available) VALUES (TRUE);
insert into station (available) VALUES (TRUE);

select * from station;

insert into charging (customer_id, kwh, chargingdate, station_id) VALUES (1, 12, '2022-03-12', 1);
insert into charging (customer_id, kwh, chargingdate, station_id) VALUES (1, 5, '2022-03-13', 1);
insert into charging (customer_id, kwh, chargingdate, station_id) VALUES (1, 20, '2022-03-14', 2);
insert into charging (customer_id, kwh, chargingdate, station_id) VALUES (2, 25, '2022-03-16', 4);
insert into charging (customer_id, kwh, chargingdate, station_id) VALUES (3, 40, '2022-03-14', 3);
insert into charging (customer_id, kwh, chargingdate, station_id) VALUES (3, 10, '2022-03-15', 2);

select * from charging;

