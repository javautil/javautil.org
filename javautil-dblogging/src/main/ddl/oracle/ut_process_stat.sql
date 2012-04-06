  CREATE TABLE UT_PROCESS_STAT 
  (    
	UT_PROCESS_STATUS_NBR 	NUMBER(9,0) NOT NULL ENABLE,
        LOG_SEQ_NBR 		NUMBER(9,0) NOT NULL ENABLE,
        SID 			NUMBER,
        STATISTIC# 		NUMBER,
        VALUE 			NUMBER,
         CONSTRAINT UT_PROCESS_STAT_PK PRIMARY KEY 
	(UT_PROCESS_STATUS_NBR, LOG_SEQ_NBR, STATISTIC#)
   ) organization index;

-- todo compress 2 on index
alter table ut_process_stat 
add constraint up_process_stat_fk 
              foreign key(ut_process_status_nbr, log_seq_nbr)
references ut_process_log(ut_process_status_nbr, log_seq_nbr);
