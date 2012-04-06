
  CREATE TABLE UT_TABLE_MSG
   (	UT_RULE_GRP_RUN_NBR NUMBER(18,0) NOT NULL ENABLE,
	UT_TABLE_RULE_NBR NUMBER(18,0) NOT NULL ENABLE,
	MSG VARCHAR2(64)
   );

alter table ut_table_msg add constraint ut_table_msg_pk primary key(ut_rule_grp_run_nbr, ut_table_rule_nbr);

alter table ut_table_msg add constraint utm_urgrn_fk foreign key (ut_rule_grp_run_nbr)
references ut_rule_grp_run(ut_rule_grp_run_nbr);

