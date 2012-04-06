connect sys/&&syspassword as sysdba 
create directory ut_process_log as '/scratch';
@01-create-user.sql
grant read, write on directory ut_process_log to &&username;
connect &&user/&&userpassword
@02-create_sequences.sql
@ut_process_status.sql
@ut_process_log.sql
@ut_process_stat.sql
@logger.pks
@logger.pkb
