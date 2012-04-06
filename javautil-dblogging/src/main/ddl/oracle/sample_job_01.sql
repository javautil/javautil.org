set timing on
begin
	logger.begin_job('sample_job_01');
	logger.info($$PLSQL_UNIT,$$PLSQL_LINE,'begin loop');
        -- all messages should go to log file
	logger.set_filter_level(9);
	for i in 1..10000  
	loop
		logger.fine($$PLSQL_UNIT,$$PLSQL_LINE,'i is ' || to_char(i));
	end loop; 
	logger.end_job;
exception when others
then
	logger.severe($$PLSQL_UINIT,$$PLSQL_LINE,sqlerrm);
	logger.abort_job;
raise;
end;
/
