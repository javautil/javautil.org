
create sequence ut_process_status_nbr_seq cache 1000;

  CREATE TABLE UT_PROCESS_STATUS
   (    UT_PROCESS_STATUS_NBR NUMBER(9,0),
        SCHEMA_NM VARCHAR2(30),
        PROCESS_NM VARCHAR2(128),
        THREAD_NM VARCHAR2(128),
        PROCESS_RUN_NBR NUMBER(9,0),
        STATUS_MSG VARCHAR2(256),
        STATUS_ID VARCHAR2(1),
        STATUS_TS TIMESTAMP (6),
        TOTAL_ELAPSED_SECONDS number(11,5),
        SID NUMBER(9),
        SERIAL_NBR NUMBER(9),
        IGNORE_FLG VARCHAR2(1) DEFAULT 'N',
         CHECK ( IGNORE_FLG IN ('Y', 'N')),
         CONSTRAINT UT_PROCESS_STATUS_PK PRIMARY KEY (UT_PROCESS_STATUS_NBR)
   );


  CREATE TABLE UT_PROCESS_LOG
   (    UT_PROCESS_LOG_NBR 	NUMBER(9,0),
        UT_PROCESS_STATUS_NBR 	NUMBER(9,0),
        LOG_MSG_ID 		VARCHAR2(8),
        LOG_MSG 		VARCHAR2(256),
        LOG_MSG_CLOB 		CLOB,
        LOG_MSG_TS 		TIMESTAMP (6),
        ELAPSED_SECONDS 	number(11,5),	
        LOG_SEQ_NBR 		NUMBER(18,0) NOT NULL, 
        CALLER_NAME 		VARCHAR2(100),
        LINE_NBR 		NUMBER(5,0),
        CALL_STACK 		CLOB,
        LOG_LEVEL 		NUMBER(2,0),
         CONSTRAINT UT_PROCESS_LOG_PK PRIMARY KEY (UT_PROCESS_STATUS_NBR, LOG_SEQ_NBR)
  );

alter table ut_process_log 
add constraint upl_ups_fk 
foreign key (ut_process_status_nbr) 
references ut_process_status(ut_process_status_nbr);

  CREATE TABLE UT_PROCESS_STAT 
  (    
	UT_PROCESS_STATUS_NBR 	NUMBER(9,0) NOT NULL,
        LOG_SEQ_NBR 		NUMBER(9,0) NOT NULL,
        SID 			NUMBER(9),
        STATISTIC_NBR 		NUMBER(9),
        VALUE 			NUMBER(11,5),
         CONSTRAINT UT_PROCESS_STAT_PK PRIMARY KEY 
	(UT_PROCESS_STATUS_NBR, LOG_SEQ_NBR, STATISTIC_NBR)
   );

alter table ut_process_stat 
add constraint up_process_stat_fk 
              foreign key(ut_process_status_nbr, log_seq_nbr)
references ut_process_log(ut_process_status_nbr, log_seq_nbr);


