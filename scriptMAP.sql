Create user IF not exists 'MapUser'@'localhost' identified by 'map';
Grant all privileges on MapDB.* to 'MapUser'@'localhost';
create database if not exists mapdb;
use mapdb;
drop table if exists exampletab;
Create table exampletab(
X1 float default null,
X2 float default null,
X3 float default null);
