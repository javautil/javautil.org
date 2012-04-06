spool install
set echo on 
@ut_data_location.sql
@ut_area_resp.sql
@ut_process_status.sql
@ut_process_log.sql
@ut_process_stat.sql
@ut_table.sql
@ut_query.sql
@ut_table_rule.sql
@ut_table_rule_hist.sql
@ut_schema.sql
@ut_schema_grp.sql
@ut_schema_grp_dtl.sql
@ut_measure.sql
@ut_metric.sql
@ut_metric_threshold.sql
@ut_rule_grp.sql
@ut_rule_grp_dtl.sql
@ut_rule_grp_run.sql
@ut_rule_grp_run_parm.sql
@ut_table_msg.sql
rem @ut_table_note.sql
rem @ut_table_note_type.sql
rem @ut_table_priv.sql
rem @ut_table_role_priv.sql
@ut_table_row_dimen_measure.sql
@ut_table_row_measure.sql
@ut_table_row_msg.sql
@ut_table_rule_parm.sql
rem @ut_table_rule_priv.sql
@ut_table_rule_rpt_sum.sql

spool off
exit