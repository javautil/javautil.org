set timing on
declare
       long_msg clob := 'this is an absurdly long message, ' || 
                ' exceeding the length of the log_msg field ' ||
                ' this should be inserted into the log_msg_clob column.  ' || 
                ' This message is part of ' ||
                ' a unit test of from sample_job_02 of the logging package. ' || 
                '  I am running out of ' ||
                ' interesting stuff to say so I will just write meaningless ' ||
                ' stuff for a little while. ' ||
                ' The quick brown fox jumped over the lazy dog. ' ||
                ' The quick brown fox jumped over the lazy dog. ' ||
                ' The quick brown fox jumped over the lazy dog. ' ||
                ' The quick brown fox jumped over the lazy dog. ' ||
                ' The quick brown fox jumped over the lazy dog. ' ||
                ' The quick brown fox jumped over the lazy dog. ' ;
 
begin
        -- jobs should begin with logger.begin_job this inserts a record into ut_process_stat
    logger.begin_job('sample_job_02');
    logger.info($$PLSQL_UNIT,$$PLSQL_LINE,'begin loop');
        -- all messages should go to log file
    logger.set_filter_level(9);
        -- this message will go to the log file and into ut_process_log
         logger.info($$PLSQL_UNIT,$$PLSQL_LINE,long_msg); 

    for i in 1..10  
    loop
            -- this message will go to the log file but not into ut_process_log as the default logging level for
                -- the database is 5 and logger.fine is 7
        logger.fine($$PLSQL_UNIT,$$PLSQL_LINE,'i is ' || to_char(i));
    end loop; 
    logger.end_job;
exception when others
then
        -- a severe condition is not necessarily fatal
    logger.severe($$PLSQL_UINIT,$$PLSQL_LINE,sqlerrm);
    -- updates ut_process_stat to indicate the the job aborted
    logger.abort_job;
raise;
end;
/
