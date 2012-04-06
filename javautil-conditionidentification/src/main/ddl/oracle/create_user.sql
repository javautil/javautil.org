CREATE USER &&user IDENTIFIED BY &&password ;

GRANT CONNECT TO &&user ;

ALTER USER &&user DEFAULT TABLESPACE users ;

ALTER USER &&user TEMPORARY TABLESPACE temp ;

ALTER USER &&user QUOTA UNLIMITED ON users ;


GRANT CREATE TABLE TO &&user ;
GRANT CREATE VIEW TO &&user ;
GRANT CREATE PROCEDURE TO &&user ;
GRANT CREATE TRIGGER TO &&user ;
GRANT CREATE SYNONYM TO &&user ;
GRANT CREATE SEQUENCE TO &&user ;
GRANT CREATE DATABASE LINK TO &&user ;
GRANT CREATE TYPE TO &&user ;
GRANT SELECT ANY TABLE TO &&user ;


GRANT SELECT ON v_$session TO &&user ;
GRANT SELECT ON v_$mystat TO &&user ;
GRANT SELECT ON v_$instance TO &&user ;
GRANT SELECT ON v_$process TO &&user ;
GRANT SELECT ON v_$parameter TO &&user ;
GRANT EXECUTE ON DBMS_PIPE TO &&user ;
GRANT EXECUTE ON DBMS_APPLICATION_INFO TO &&user ;

undefine passwd
undefine deftab
undefine temptab
undefine more_tab1
