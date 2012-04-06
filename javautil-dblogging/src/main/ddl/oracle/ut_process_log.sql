
  CREATE TABLE UT_PROCESS_LOG
   (    UT_PROCESS_LOG_NBR 	NUMBER(9,0),
        UT_PROCESS_STATUS_NBR 	NUMBER(9,0),
        LOG_MSG_ID 		VARCHAR2(8),
        LOG_MSG 		VARCHAR2(256),
        LOG_MSG_CLOB 		CLOB,
        LOG_MSG_TS 		TIMESTAMP (6),
        ELAPSED_TIME 		INTERVAL DAY (2) TO SECOND (6),
        LOG_SEQ_NBR 		NUMBER(18,0) NOT NULL ENABLE,
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
