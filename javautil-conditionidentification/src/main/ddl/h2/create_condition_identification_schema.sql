
    create table GTT_UT_TABLE_ROW_MSG (
        UT_TABLE_RULE_NBR number(10,0) not null,
        UT_RULE_GRP_RUN_NBR number(10,0) not null,
        PRIMARY_KEY number(10,0) not null,
        MSG varchar2(64 char),
        primary key (UT_TABLE_RULE_NBR, UT_RULE_GRP_RUN_NBR, PRIMARY_KEY)
    );

    comment on table gtt_ut_table_row_msg is 'Temporary table for storing results eventually persisted in ut_table_row_msg.';

    create table UT_AREA_RESP (
        UT_AREA_RESP_NBR number(10,0) not null,
        FUNC_AREA_CD varchar2(8 char) not null unique,
        FUNC_SHORT_DESCR varchar2(60 char) not null,
        FUNC_LONG_DESCR varchar2(255 char),
        primary key (UT_AREA_RESP_NBR)
    );

    comment on table UT_AREA_RESP is
        'Classification of business areas of functional responsibility. The primary key should be populated from sequence ut_area_resp_nbr_seq';

    comment on column UT_AREA_RESP.FUNC_AREA_CD is
        'Description of functional area for display on reports and screens';

    comment on column UT_AREA_RESP.FUNC_SHORT_DESCR is
        'Short description of the business area of responsibility';

    comment on column UT_AREA_RESP.FUNC_LONG_DESCR is
        'Long description of the business area of responsibility';

    create table UT_DATA_LOCATION (
        SCHEMA varchar2(30 char) not null,
        TABLE_NAME varchar2(30 char) not null,
        primary key (SCHEMA, TABLE_NAME)
    );
	comment on table ut_data_location is 'Identifier for a table that contains data against which identification conditions are run.';

    create table UT_MEASURE (
        MEAS_NBR number(10,0) not null,
        MEAS_ID varchar2(20 char) not null unique,
        SHORT_DESCR varchar2(60 char) not null,
        LONG_DESCR varchar2(255 char),
        ENTRY_USER number(10,0) not null,
        ENTRY_TM raw(255) not null,
        MOD_USER number(10,0),
        MOD_TM raw(255),
        primary key (MEAS_NBR)
    );

    comment on table ut_measure is 'A measure of a value found in a monitored table.';

    create table UT_METRIC (
        METRIC_NBR number(10,0) not null,
        METRIC_ID varchar2(16 char) unique,
        METRIC_DESCR varchar2(80 char),
        METRIC_QRY varchar2(255 char),
        primary key (METRIC_NBR)
    );

    create table UT_METRIC_THRESHOLD (
        UT_METRIC_THRESHOLD_NBR number(10,0) not null,
        MEAS_NBR number(10,0) not null,
        DIMEN_TABLE_ID varchar2(8 char),
        FACT_TABLE_ID varchar2(8 char) not null,
        ORG_NBR_SRC number(10,0) not null,
        DIMEN_PK number(10,0),
        THRESHOLD_VAL_LOWER_FATAL number(10,0),
        THRESHOLD_VAL_LOWER_SEVERE number(10,0),
        THRESHOLD_VAL_LOWER_WARN number(10,0),
        THRESHOLD_VAL_UPPER_WARN number(10,0),
        THRESHOLD_VAL_UPPER_SEVERE number(10,0),
        THRESHOLD_VAL_UPPER_FATAL number(10,0),
        THRESHOLD_ORDER varchar2(1 char) not null,
        ENTRY_USER number(10,0) not null,
        ENTRY_TM raw(255) not null,
        MOD_USER number(10,0),
        MOD_TM raw(255),
        primary key (UT_METRIC_THRESHOLD_NBR)
    );
	/* TOD what is FACT_TABLE_ID, what does it reference? */

    create table UT_PROCESS_LOG (
        UT_PROCESS_STATUS_NBR number(10,0) not null,
        LOG_SEQ_NBR number(10,0) not null,
        UT_PROCESS_LOG_NBR number(10,0),
        LOG_MSG_ID varchar2(8 char),
        LOG_MSG varchar2(256 char),
        LOG_MSG_CLOB varchar2(255 char),
        LOG_MSG_TS raw(255),
        ELAPSED_TIME raw(255),
        CALLER_NAME varchar2(100 char),
        LINE_NBR number(10,0),
        CALL_STACK varchar2(255 char),
        LOG_LEVEL number(10,0),
        primary key (UT_PROCESS_STATUS_NBR, LOG_SEQ_NBR)
    );

    create table UT_PROCESS_STAT (
        UT_PROCESS_STATUS_NBR number(10,0) not null,
        LOG_SEQ_NBR number(10,0) not null,
        STATISTIC number(10,0) not null,
        SID number(10,0),
        VALUE number(10,0),
        primary key (UT_PROCESS_STATUS_NBR, LOG_SEQ_NBR, STATISTIC)
    );

    create table UT_PROCESS_STATUS (
        UT_PROCESS_STATUS_NBR number(10,0) not null,
        SCHEMA_NM varchar2(30 char),
        PROCESS_NM varchar2(128 char),
        THREAD_NM varchar2(128 char),
        PROCESS_RUN_NBR number(10,0),
        STATUS_MSG varchar2(256 char),
        STATUS_ID varchar2(1 char),
        STATUS_TS raw(255),
        TOTAL_ELAPSED raw(255),
        SID number(10,0),
        SERIAL number(10,0),
        IGNORE_FLG varchar2(1 char),
        primary key (UT_PROCESS_STATUS_NBR)
    );

    create table UT_QUERY (
        UT_QUERY_NBR number(10,0) not null,
        SCHEMA varchar2(30 char) not null,
        TABLE_NAME varchar2(30 char) not null,
        QUERY_NM varchar2(32 char) not null,
        QUERY_DESCR varchar2(128 char),
        QUERY_TEXT varchar2(255 char),
        APEX_RPT_HEADING varchar2(4000 char),
        TABLE_ID varchar2(8 char),
        primary key (UT_QUERY_NBR)
    );

    create table UT_RULE_GRP (
        UT_RULE_GRP_NBR number(10,0) not null,
        UT_RULE_GRP_NM varchar2(32 char) unique,
        UT_RULE_GRP_DESCR varchar2(60 char),
        UT_RULE_GRP_LONG_DESCR varchar2(255 char),
        THREAD_CNT number(10,0),
        MAX_QUERY_SEC_DFLT number(10,0),
        primary key (UT_RULE_GRP_NBR)
    );

    create table UT_RULE_GRP_DTL (
        UT_RULE_GRP_NBR number(10,0) not null,
        UT_TABLE_RULE_NBR number(10,0) not null,
        RUN_SEQ number(10,0),
        primary key (UT_RULE_GRP_NBR, UT_TABLE_RULE_NBR)
    );

    create table UT_RULE_GRP_RUN (
        UT_RULE_GRP_RUN_NBR number(10,0) not null,
        UT_PROCESS_STATUS_NBR number(10,0),
        UT_RULE_GRP_NBR number(10,0) not null,
        UT_RUN_GRP_DESCR varchar2(80 char),
        LAST_RUN_TM date not null,
        CTRL_PT_NBR number(10,0) not null,
        primary key (UT_RULE_GRP_RUN_NBR)
    );

    comment on table ut_rule_grp_run is 'Header table for a group of rules to be run.';

    create table UT_RULE_GRP_RUN_PARM (
        UT_RULE_GRP_RUN_NBR number(10,0) not null,
        PARM_NM varchar2(30 char) not null,
        PARM_VALUE_STRING varchar2(255 char),
        PARM_VALUE_NUMBER number(10,0),
        PARM_VALUE_DATE date,
        primary key (UT_RULE_GRP_RUN_NBR, PARM_NM)
    );
	comment on table ut_rule_grp_run_parm is 'Parameters for SQL statement bind variables used in ???';

    create table UT_SCHEMA (
        SCHEMA_NM varchar2(30 char) not null,
        SCHEMA_DESCR varchar2(60 char),
        primary key (SCHEMA_NM)
    );

    create table UT_SCHEMA_GRP (
        UT_SCHEMA_GRP_NBR number(10,0) not null,
        SCHEMA_GRP_NM varchar2(16 char) unique,
        SCHEMA_GRP_DESCR varchar2(30 char),
        primary key (UT_SCHEMA_GRP_NBR)
    );

    create table UT_SCHEMA_GRP_DTL (
        SCHEMA_NM varchar2(30 char) not null,
        UT_SCHEMA_GRP_NBR number(10,0) not null,
        primary key (UT_SCHEMA_GRP_NBR, SCHEMA_NM)
    );

    create table UT_TABLE (
        TABLE_ID varchar2(8 char) not null,
        TABLE_NAME varchar2(30 char) unique,
        primary key (TABLE_ID)
    );

    create table UT_TABLE_MSG (
        UT_RULE_GRP_RUN_NBR number(10,0) not null,
        UT_TABLE_RULE_NBR number(10,0) not null,
        MSG varchar2(64 char),
        primary key (UT_RULE_GRP_RUN_NBR, UT_TABLE_RULE_NBR)
    );

    create table UT_TABLE_ROW_DIMEN_MEASURE (
        FACT_TABLE_ID varchar2(8 char) not null,
        FACT_TABLE_PK number(10,0) not null,
        DIMEN_PK number(10,0) not null,
        MEAS_NBR number(10,0) not null,
        MEAS number(10,0) not null,
        primary key (FACT_TABLE_ID, FACT_TABLE_PK, DIMEN_PK, MEAS_NBR, MEAS)
    );

    create table UT_TABLE_ROW_MEASURE (
        FACT_TABLE_ID varchar2(8 char) not null,
        FACT_TABLE_PK number(10,0) not null,
        MEAS_NBR number(10,0) not null,
        MEAS number(10,0) not null,
        primary key (FACT_TABLE_ID, FACT_TABLE_PK, MEAS_NBR, MEAS)
    );

    create table UT_TABLE_ROW_MSG (
        UT_TABLE_RULE_NBR number(10,0) not null,
        UT_RULE_GRP_RUN_NBR number(10,0) not null,
        PRIMARY_KEY number(10,0) not null,
        MSG varchar2(64 char),
        UT_USER_NBR_ALLOW number(10,0),
        DISP_CD_ENUM varchar2(1 char),
        UT_USER_NBR_DISP number(10,0),
        primary key (UT_TABLE_RULE_NBR, UT_RULE_GRP_RUN_NBR, PRIMARY_KEY)
    );

    create table UT_TABLE_RULE (
        UT_TABLE_RULE_NBR number(10,0) not null,
        UT_AREA_RESP_NBR number(10,0),
        SCHEMA varchar2(30 char) not null,
        TABLE_NAME varchar2(30 char) not null,
        UT_TABLE_RULE_NBR_EXCLUDE number(10,0),
        UT_QUERY_NBR number(10,0),
        MSG_ID varchar2(16 char) unique,
        SQL_TEXT varchar2(255 char),
        MSG_DESCR varchar2(255 char),
        RULE_TYPE varchar2(8 char),
        DATA_SRC_NM_SRC varchar2(20 char),
        DATA_SRC_NM_DEST varchar2(20 char),
        MAX_QUERY_SEC number(10,0),
        SEVERE_RULE_FLG varchar2(1 char) not null,
        SHORT_DESCR varchar2(80 char),
        EXCEPTION_TYPE_CD varchar2(1 char) not null,
        DISP_CD_ENUM varchar2(1 char),
        LOG_LVL number(10,0),
        primary key (UT_TABLE_RULE_NBR)
    );

    create table UT_TABLE_RULE_HIST (
        UT_TABLE_RULE_HIST_NBR number(10,0) not null,
        UT_AREA_RESP_NBR number(10,0),
        UT_TABLE_RULE_NBR number(10,0),
        SCHEMA varchar2(30 char) not null,
        TABLE_NAME varchar2(30 char) not null,
        UT_TABLE_RULE_NBR_EXCLUDE number(10,0),
        UT_QUERY_NBR number(10,0),
        MSG_ID varchar2(16 char),
        SQL_TEXT varchar2(255 char),
        MSG_DESCR varchar2(255 char),
        RULE_TYPE varchar2(8 char),
        DATA_SRC_NM_SRC varchar2(20 char),
        DATA_SRC_NM_DEST varchar2(20 char),
        MAX_QUERY_SEC number(10,0),
        SEVERE_RULE_FLG varchar2(1 char) not null,
        SHORT_DESCR varchar2(80 char),
        EXCEPTION_TYPE_CD varchar2(1 char) not null,
        DISP_CD_ENUM varchar2(1 char),
        LOG_LVL number(10,0),
        primary key (UT_TABLE_RULE_HIST_NBR)
    );

    comment on table ut_table_rule_hist is 'Historical values for table UT_TABLE_RULE.';

    create table UT_TABLE_RULE_PARM (
        UT_TABLE_RULE_NBR number(10,0) not null,
        PARM_NM varchar2(30 char) not null,
        PARM_DATA_TYPE varchar2(10 char),
        primary key (UT_TABLE_RULE_NBR, PARM_NM)
    );

    create table UT_TABLE_RULE_RPT_SUM (
        UT_TABLE_RULE_RPT_SUM_NBR number(10,0) not null,
        UT_TABLE_RULE_NBR number(10,0) not null,
        RPT_DESCR varchar2(80 char) not null,
        APEX_PAGE_NBR number(10,0) not null,
        primary key (UT_TABLE_RULE_RPT_SUM_NBR)
    );

    alter table UT_METRIC_THRESHOLD 
        add constraint umt_ut_fk
        foreign key (FACT_TABLE_ID) 
        references UT_TABLE;

    alter table UT_METRIC_THRESHOLD 
        add constraint umt_um_fk
        foreign key (MEAS_NBR) 
        references UT_MEASURE;

    alter table UT_METRIC_THRESHOLD 
        add constraint umt_ut_2_fk
        foreign key (DIMEN_TABLE_ID) 
        references UT_TABLE;

    alter table UT_PROCESS_LOG 
        add constraint upl_ups_fk
        foreign key (UT_PROCESS_STATUS_NBR) 
        references UT_PROCESS_STATUS;

    alter table UT_PROCESS_STAT 
        add constraint ups_ups_fk
        foreign key (UT_PROCESS_STATUS_NBR) 
        references UT_PROCESS_STATUS;

    alter table UT_PROCESS_STAT 
        add constraint ups_upl_fk
        foreign key (UT_PROCESS_STATUS_NBR, LOG_SEQ_NBR) 
        references UT_PROCESS_LOG;

    alter table UT_QUERY 
        add constraint uq_udl_fk
        foreign key (SCHEMA, TABLE_NAME) 
        references UT_DATA_LOCATION;

    alter table UT_RULE_GRP_DTL 
        add constraint urgd_urg_fk
        foreign key (UT_RULE_GRP_NBR) 
        references UT_RULE_GRP;

    alter table UT_RULE_GRP_DTL 
        add constraint urgd_utr_fk
        foreign key (UT_TABLE_RULE_NBR) 
        references UT_TABLE_RULE;

    alter table UT_RULE_GRP_RUN 
        add constraint urgr_ups_fk
        foreign key (UT_PROCESS_STATUS_NBR) 
        references UT_PROCESS_STATUS;

    alter table UT_RULE_GRP_RUN 
        add constraint urgr_urg_fk
        foreign key (UT_RULE_GRP_NBR) 
        references UT_RULE_GRP;

    alter table UT_RULE_GRP_RUN_PARM 
        add constraint urgrp_urgr_fk
        foreign key (UT_RULE_GRP_RUN_NBR) 
        references UT_RULE_GRP_RUN;

    alter table UT_SCHEMA_GRP_DTL 
        add constraint usgd_usg_fk
        foreign key (UT_SCHEMA_GRP_NBR) 
        references UT_SCHEMA_GRP;

    alter table UT_SCHEMA_GRP_DTL 
        add constraint usgd_us_fk
        foreign key (SCHEMA_NM) 
        references UT_SCHEMA;

    alter table UT_TABLE_MSG 
        add constraint utm_urgr_fk
        foreign key (UT_RULE_GRP_RUN_NBR) 
        references UT_RULE_GRP_RUN;

    alter table UT_TABLE_ROW_MSG 
        add constraint utrm_urgr_fk
        foreign key (UT_RULE_GRP_RUN_NBR) 
        references UT_RULE_GRP_RUN;

    alter table UT_TABLE_ROW_MSG 
        add constraint utrm_utr_fk
        foreign key (UT_TABLE_RULE_NBR) 
        references UT_TABLE_RULE;

    alter table UT_TABLE_RULE 
        add constraint utrl_uar_fk
        foreign key (UT_AREA_RESP_NBR) 
        references UT_AREA_RESP;

    alter table UT_TABLE_RULE 
        add constraint utrl_udl_fk
        foreign key (SCHEMA, TABLE_NAME) 
        references UT_DATA_LOCATION;

    alter table UT_TABLE_RULE 
        add constraint utr_uq_fk
        foreign key (UT_QUERY_NBR) 
        references UT_QUERY;

    alter table UT_TABLE_RULE 
        add constraint utr_utr_fk
        foreign key (UT_TABLE_RULE_NBR_EXCLUDE) 
        references UT_TABLE_RULE;

    alter table UT_TABLE_RULE_HIST 
        add constraint utrh_uar_fk
        foreign key (UT_AREA_RESP_NBR) 
        references UT_AREA_RESP;

    alter table UT_TABLE_RULE_HIST 
        add constraint utrh_utr_fk
        foreign key (UT_TABLE_RULE_NBR) 
        references UT_TABLE_RULE;

    alter table UT_TABLE_RULE_HIST 
        add constraint utrh_udl_fk
        foreign key (SCHEMA, TABLE_NAME) 
        references UT_DATA_LOCATION;

    alter table UT_TABLE_RULE_HIST 
        add constraint utrh_uq_fk
        foreign key (UT_QUERY_NBR) 
        references UT_QUERY;

    alter table UT_TABLE_RULE_HIST 
        add constraint utrh_utr_fk
        foreign key (UT_TABLE_RULE_NBR_EXCLUDE) 
        references UT_TABLE_RULE;

    alter table UT_TABLE_RULE_PARM 
        add constraint utrp_utr_fk
        foreign key (UT_TABLE_RULE_NBR) 
        references UT_TABLE_RULE(UT_TABLE_RULE_NBR);

    alter table UT_TABLE_RULE_RPT_SUM 
        add constraint utrrs_utr_fk
        foreign key (UT_TABLE_RULE_NBR) 
        references UT_TABLE_RULE;

    create sequence UT_AREA_RESP_NBR_SEQ;

    create sequence UT_MEASURE_NBR_SEQ;

    create sequence UT_METRIC_NBR_SEQ;

    create sequence UT_METRIC_THRESHOLD_NBR_SEQ;

    create sequence UT_PROCESS_STATUS_NBR_SEQ;

    create sequence UT_QUERY_NBR_SEQ;

    create sequence UT_RULE_GRP_NBR_SEQ;

    create sequence UT_RULE_GRP_RUN_NBR_SEQ;

    create sequence UT_SCHEMA_GRP_NBR_SEQ;

    create sequence UT_SCHEMA_NBR_SEQ;

    create sequence UT_TABLE_NBR_SEQ;

    create sequence UT_TABLE_RULE_HIST_NBR_SEQ;

    create sequence UT_TABLE_RULE_NBR_SEQ;

    create sequence UT_TABLE_RULE_RPT_SUM_NBR_SEQ;
