drop database if exists weather_db;
create database weather_db;
use weather_db;

# Dummy data for Locations
insert into location(id, city, zipcode)
	values(1, 'New York', '10001');
insert into location(id, city, zipcode)
	values(2, 'Los Angeles', '90001');
insert into location(id, city, zipcode)
	values(3, 'Chicago', '60007');
insert into location(id, city, zipcode)
	values(4, 'Houston', '77001');
insert into location(id, city, zipcode)
	values(5, 'Philadelphia', '19019');
insert into location(id, city, zipcode)
	values(6, 'Phoenix', '85001');
insert into location(id, city, zipcode)
	values(7, 'San Antonio', '78015');
insert into location(id, city, zipcode)
	values(8, 'San Diego', '91911');
insert into location(id, city, zipcode)
	values(9, 'Dallas', '75001');
insert into location(id, city, zipcode)
	values(10, 'San Jose', '94088');
insert into location(id, city, zipcode)
	values(11, 'Austin', '73301');
insert into location(id, city, zipcode)
	values(12, 'Jacksonville', '32034');
insert into location(id, city, zipcode)
	values(13, 'San Francisco', '94016');
insert into location(id, city, zipcode)
	values(14, 'Indianapolis', '46077');
insert into location(id, city, zipcode)
	values(15, 'Columbus', '43004');
insert into location(id, city, zipcode)
	values(16, 'Fort Worth', '76006');
insert into location(id, city, zipcode)
	values(17, 'Charlotte', '28105');
insert into location(id, city, zipcode)
	values(18, 'Seattle', '98101');
insert into location(id, city, zipcode)
	values(19, 'Denver', '80014');
insert into location(id, city, zipcode)
	values(20, 'El Paso', '79835');
insert into location(id, city, zipcode)
	values(21, 'Detroit', '48127');
insert into location(id, city, zipcode)
	values(22, 'Washington', '20001');
insert into location(id, city, zipcode)
	values(23, 'Boston', '02108');
insert into location(id, city, zipcode)
	values(24, 'Memphis', '37501');
insert into location(id, city, zipcode)
	values(25, 'Nashville', '37011');
insert into location(id, city, zipcode)
	values(26, 'Portland', '97035');
insert into location(id, city, zipcode)
	values(27, 'Oklahoma City', '73008');
insert into location(id, city, zipcode)
	values(28, 'Las Vegas', '88901');
insert into location(id, city, zipcode)
	values(29, 'Baltimore', '21201');
insert into location(id, city, zipcode)
	values(30, 'Louisville', '40018');
insert into location(id, city, zipcode)
	values(31, 'Milwaukee', '53201');
insert into location(id, city, zipcode)
	values(32, 'Albuquerque', '87101');
insert into location(id, city, zipcode)
	values(33, 'Tuscon', '85641');
insert into location(id, city, zipcode)
	values(34, 'Fresno', '93650');
insert into location(id, city, zipcode)
	values(35, 'Sacramento', '94203');
insert into location(id, city, zipcode)
	values(36, 'Kansas City', '64030');
insert into location(id, city, zipcode)
	values(37, 'Long Beach', '90712');
insert into location(id, city, zipcode)
	values(38, 'Mesa', '85201');
insert into location(id, city, zipcode)
	values(39, 'Atlanta', '30033');
insert into location(id, city, zipcode)
	values(40, 'Colorado Springs', '80829');
insert into location(id, city, zipcode)
	values(41, 'Virginia Beach', '23450');
insert into location(id, city, zipcode)
	values(42, 'Raleigh', '27513');
insert into location(id, city, zipcode)
	values(43, 'Omaha', '68007');
insert into location(id, city, zipcode)
	values(44, 'Miami', '33101');
insert into location(id, city, zipcode)
	values(45, 'Oakland', '94501');
insert into location(id, city, zipcode)
	values(46, 'Minneapolis', '55111');
insert into location(id, city, zipcode)
	values(47, 'Tulsa', '74008');
insert into location(id, city, zipcode)
	values(48, 'Wichita', '67052');
insert into location(id, city, zipcode)
	values(49, 'New Orleans', '70032');
insert into location(id, city, zipcode)
	values(50, 'Arlington', '76001');