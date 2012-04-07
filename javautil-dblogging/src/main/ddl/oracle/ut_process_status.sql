  CREATE TABLE UT_PROCESS_STATUS
   (    UT_PROCESS_STATUS_NBR NUMBER(9,0),
        SCHEMA_NM VARCHAR2(30),
        PROCESS_NM VARCHAR2(128),
        THREAD_NM VARCHAR2(128),
        PROCESS_RUN_NBR NUMBER(9,0),
        STATUS_MSG VARCHAR2(256),
        STATUS_ID VARCHAR2(1),
        STATUS_TS TIMESTAMP (6),
        TOTAL_ELAPSED INTERVAL DAY (2) TO SECOND (6),
        SID NUMBER,
        SERIAL# NUMBER,
        IGNORE_FLG VARCHAR2(1) DEFAULT 'N',
         CHECK ( IGNORE_FLG IN ('Y', 'N')) ENABLE,
         CONSTRAINT UT_PROCESS_STATUS_PK PRIMARY KEY (UT_PROCESS_STATUS_NBR)
   );
