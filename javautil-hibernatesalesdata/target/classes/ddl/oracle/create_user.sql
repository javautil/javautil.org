drop user &&username cascade;
create user &&username identified by &&password;
alter user &&username default tablespace users;
alter user &&username quota unlimited on users;
grant create table    to &&username;
grant create session  to &&username;
grant create sequence to &&username;
grant create view     to &&username;
grant alter session   to &&username;
