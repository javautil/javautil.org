set echo on

CREATE OR REPLACE PACKAGE BODY logger
IS
--
--
   g_filter_level          PLS_INTEGER := G_INFO ;
   g_record_level          PLS_INTEGER := G_INFO ;
   g_file_handle           UTL_FILE.file_type;
   g_log_file_name         VARCHAR2 (255);
   g_last_log_seq_nbr      PLS_INTEGER;
   g_dbms_output_level     PLS_INTEGER        := 5;
   g_process_start_tm      TIMESTAMP;
   g_process_end_tm        TIMESTAMP;
   g_process_name          VARCHAR2 (128);
   g_process_status_nbr    PLS_INTEGER;
-- set by get_caller
   g_owner_name            VARCHAR2 (100);
   g_caller_name           VARCHAR2 (100);
   g_line_number           PLS_INTEGER;
   g_caller_type           VARCHAR2 (100);
--
   g_serial_nbr            PLS_INTEGER;
   g_sid                   PLS_INTEGER;
   g_current_schema        VARCHAR2 (32);
   g_current_user          VARCHAR2 (32);
   g_session_user          VARCHAR2 (32);
   g_proxy_user            VARCHAR2 (32);
   g_who_called_me_level   BINARY_INTEGER     := 6;

   PROCEDURE open_log_file (parm_file_name IN VARCHAR2)
   --% opens a log file with the specified file name in the directory 'UT_PROCESS_LOG'
   IS
   BEGIN
      IF (NOT UTL_FILE.is_open (g_file_handle))
      THEN
         DBMS_OUTPUT.put_line ('opening log file ' || parm_file_name);
         g_file_handle :=
                       UTL_FILE.fopen ('UT_PROCESS_LOG', parm_file_name, 'A');
      END IF;
   END open_log_file;

  PROCEDURE create_process_log (
      parm_ut_process_status_nbr   IN   PLS_INTEGER,
      parm_log_msg_id              IN   VARCHAR2,
      parm_log_msg                 IN   VARCHAR2,
      parm_log_level               IN   PLS_INTEGER,
      parm_elapsed_time            IN   INTERVAL DAY TO SECOND DEFAULT NULL,
      parm_caller_name             IN   VARCHAR2,
      parm_line_number             IN   PLS_INTEGER,
      parm_serial_number           IN   PLS_INTEGER,
      parm_call_stack              IN   VARCHAR2 DEFAULT NULL
   )
   --% PROCEDURE create_process_log 
   --% a log file is created in the ut_process_log oracle directory with the name 
   --% g_process_name || '_' || TO_CHAR (CURRENT_TIMESTAMP, 'YYYY-MM-DD_HH24MISSXFF');
   --% \section{log file format}
   --% \begin{itemize}
   --%    \item g_last_log_seq_nbr 
   --%	  \item	parm_ut_process_status_nbr 
   --%    \item	parm_log_msg_id 
   --%	  \item	TO_CHAR (CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SSXFF') 
   --%    \item my_msg 
   --%    \item parm_log_level 
   --%	  \item	parm_caller_name 
   --%	  \item	parm_line_number 
   --%	  \item	parm_call_stack;
   --% \end{itemize}
   IS
      my_message   VARCHAR2 (32767);
      my_msg       VARCHAR2 (32767);
      now          TIMESTAMP        := SYSDATE;
      pragma autonomous_transaction ;
     short_message varchar2(255);
     long_message  clob; 
   --
   BEGIN
      --% messages shorter than 256 go into log_msg
      --% longer messages go into log_msg_clob

      IF g_log_file_name IS NULL
      THEN
         g_log_file_name :=
               g_process_name || '_'
            || TO_CHAR (CURRENT_TIMESTAMP, 'YYYY-MM-DD_HH24MISSXFF');
      END IF;

      open_log_file (g_log_file_name);
      g_last_log_seq_nbr := g_last_log_seq_nbr + 1;

      IF parm_log_level <= g_filter_level
      THEN
         IF INSTR (parm_log_msg, '"') > 0
         THEN
            my_msg := REPLACE (parm_log_msg, '"', '""');
         ELSE
            my_msg := parm_log_msg;
         END IF;

      if (length(parm_log_msg) < 255) then
         short_message := my_msg;
         long_message  := null;
      else 
         short_message := 'see clob';
         long_message  := my_msg;
      end if;

         my_message :=
                g_last_log_seq_nbr || ',' || 
		parm_ut_process_status_nbr || ',"' || 
		parm_log_msg_id || '",' || 
		TO_CHAR (CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SSXFF') || ',"' || 
                 my_msg || '",' ||
            	 parm_log_level || ',"' || 
		parm_caller_name || '",' || 
		parm_line_number || ',' || 
		parm_call_stack;
----
         UTL_FILE.put_line (g_file_handle, my_message);
      END IF;

      IF parm_log_level = g_snap OR parm_log_level <= g_record_level
      THEN
         INSERT INTO ut_process_log (	
		ut_process_status_nbr,  	log_seq_nbr, 
		log_msg_id, 			log_msg, 		
		log_level,   			log_msg_ts, 
		elapsed_time, 			caller_name, 
		line_nbr,                       log_msg_clob
         )
         VALUES (
		parm_ut_process_status_nbr, 	g_last_log_seq_nbr,
                parm_log_msg_id, 		short_message, 
		parm_log_level, 		CURRENT_TIMESTAMP, 
		parm_elapsed_time, 		parm_caller_name, 
		parm_line_number,               long_message
         );
      END IF;
--
      commit;
   END create_process_log;
--

   PROCEDURE TRACE (p_string IN VARCHAR2)
   --% PROCEDURE TRACE (p_string IN VARCHAR2)
   --% Write the messsage to dbms_output
   IS
   BEGIN
      DBMS_OUTPUT.put_line (p_string);
   END TRACE;

--
--
-- public
   PROCEDURE set_dbms_output_level (p_level IN PLS_INTEGER)
   --% PROCEDURE set_dbms_output_level (p_level IN PLS_INTEGER)
   --% set the dbms_output level 
   --% higher number trace levels will not be written to dbms_output
   IS
   BEGIN
      g_dbms_output_level := p_level;
   END set_dbms_output_level;

--
--
-- public
   PROCEDURE set_trace (p_trace_level IN PLS_INTEGER)
   --% PROCEDURE set_trace (p_trace_level IN PLS_INTEGER)
   --% set the trace level for dbms_trace an oracle provided package  
   --% DBMS_TRACE.set_plsql_trace (p_trace_level);
   IS
   BEGIN
      DBMS_TRACE.set_plsql_trace (p_trace_level);
   END set_trace;

--
--


--
--
   PROCEDURE close_log_file
   --% PROCEDURE close_log_file
   --% close the log file
   IS
   BEGIN
      IF utl_file.is_open (g_file_handle) THEN 
	utl_file.fclose (g_file_handle); 
      END IF;
   END close_log_file;

--
--
   PROCEDURE set_context
   --% set sys_context userenv variables 
   IS
   BEGIN
      SELECT SYS_CONTEXT ('USERENV', 'CURRENT_SCHEMA'),
             SYS_CONTEXT ('USERENV', 'CURRENT_USER'),
             SYS_CONTEXT ('USERENV', 'SESSION_USER'),
             SYS_CONTEXT ('USERENV', 'PROXY_USER')
        INTO g_current_schema,
             g_current_user,
             g_session_user,
             g_proxy_user
        FROM DUAL;
   END set_context;


----
--
   PROCEDURE log_level (
      p_log_msg       IN   VARCHAR2,
      p_log_level     IN   PLS_INTEGER,
      p_caller_name   IN   VARCHAR2 DEFAULT NULL,
      p_line_number   IN   PLS_INTEGER DEFAULT NULL,
      p_call_stack    IN   VARCHAR2 DEFAULT NULL
   )
   --% \begin{verbatim}
   --% PROCEDURE log_level (
   --%   p_log_msg       IN   VARCHAR2,
   --%   p_log_level     IN   PLS_INTEGER,
   --%   p_caller_name   IN   VARCHAR2 DEFAULT NULL,
   --%   p_line_number   IN   PLS_INTEGER DEFAULT NULL,
   --%   p_call_stack    IN   VARCHAR2 DEFAULT NULL
   --% \end{verbatim}
   IS
      log_time       TIMESTAMP  := CURRENT_TIMESTAMP;
      elapsed        INTERVAL DAY TO SECOND;
      my_log_level   PLS_INTEGER;
   BEGIN
      my_log_level := p_log_level;

--
      IF my_log_level < 1 THEN my_log_level := 1; END IF;
      IF my_log_level > 9 THEN my_log_level := 9; END IF;

--
      create_process_log (parm_ut_process_status_nbr      => g_process_status_nbr,
                          parm_log_msg_id                 => NULL,
                          parm_log_msg                    => p_log_msg,
                          parm_log_level                  => my_log_level,
                          parm_elapsed_time               => elapsed,
                          parm_caller_name                => p_caller_name,
                          parm_line_number                => p_line_number,
                          parm_serial_number              => g_serial_nbr,
                          parm_call_stack                 => p_call_stack
                         );
--

   --
   END log_level;

--
--
   PROCEDURE begin_job (parm_process_name IN VARCHAR2)
   --% PROCEDURE begin_job (parm_process_name IN VARCHAR2)
   --% *create a record in ut_process_status
   IS
      PRAGMA AUTONOMOUS_TRANSACTION;
   BEGIN
--
      IF parm_process_name IS NULL
      THEN
         g_process_name := g_caller_name;
      ELSE
         g_process_name := parm_process_name;
      END IF;

--
      g_process_start_tm := CURRENT_TIMESTAMP;

--
--   todo why are we no longer using the returning 
      SELECT ut_process_status_nbr_seq.NEXTVAL INTO g_process_status_nbr FROM DUAL; 
--
      INSERT INTO ut_process_status
                  (process_nm, schema_nm, thread_nm,
                   process_run_nbr, status_msg, status_id, status_ts, SID,
                   serial#, ut_process_status_nbr
                  )
           VALUES (g_process_name, g_current_schema, 'main',
                                                -- no threading in pl/sql jobs
                   g_process_status_nbr,       -- no run number was being used
                                        'init',      -- @todo what's the point
                                               'A',
                                                -- active @todo document table
                                                   SYSDATE, g_sid,
                   g_serial_nbr, g_process_status_nbr
                  );

        --ut_surrogate_seq.NEXTVAL )
    --RETURNING ut_process_status_nbr INTO g_process_status_nbr ;
--
      g_last_log_seq_nbr := 1;
--
      COMMIT;
   END begin_job;

--
--
   PROCEDURE end_job
   --% PROCEDURE end_job
   --% update ut_process_status.status_id to 'C' and status_msg to 'DONE'
   IS
      PRAGMA AUTONOMOUS_TRANSACTION;
      elapsed_tm   INTERVAL DAY TO SECOND;
   BEGIN
      g_process_end_tm := CURRENT_TIMESTAMP;
      elapsed_tm := g_process_end_tm - g_process_start_tm;

--
      UPDATE ut_process_status
         SET total_elapsed = elapsed_tm,
             SID = NULL,
             serial# = NULL,
             status_msg = 'DONE',
             status_id = 'C',
             status_ts = SYSDATE
       WHERE ut_process_status_nbr = g_process_status_nbr;

--
      COMMIT;
--
      close_log_file;
   END end_job;

--
-- private
   PROCEDURE abort_job
   --% PROCEDURE abort_job
   --% * update ut_process_status 
   --% # elapsed_time 
   --% # status_id = 'I'
   --% # status_msg = 'ABORT'
   IS
      PRAGMA AUTONOMOUS_TRANSACTION;
      elapsed_tm   INTERVAL DAY TO SECOND;
   BEGIN
      g_process_end_tm := CURRENT_TIMESTAMP;
      elapsed_tm := g_process_end_tm - g_process_start_tm;

--
      UPDATE ut_process_status
         SET total_elapsed = elapsed_tm,
             SID = NULL,
             serial# = NULL,
             status_msg = 'ABORT',
             status_id = 'I',
             status_ts = SYSDATE
       WHERE ut_process_status_nbr = g_process_status_nbr;

--
      close_log_file;
--
      COMMIT;
   END abort_job;

--
--
-- @todo set status messages in info
-- @tod support run as job
--

   --
   PROCEDURE LOG (p_level IN PLS_INTEGER, p_log_msg IN VARCHAR2)
   IS
   BEGIN
      log_level (p_log_level        => p_level,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => g_caller_name,
                 p_line_number      => g_line_number
                );
   END LOG;

--
--
-- @todo print full stack trace
-- public
   PROCEDURE severe (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2 DEFAULT '',
      p_record_stack   IN   BOOLEAN default false
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack
      THEN
         stack := DBMS_UTILITY.format_call_stack ();
      END IF;

      log_level (p_log_level        => g_severe,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END severe;

--
--
-- public
   PROCEDURE warning (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2,
      p_record_stack   IN   BOOLEAN
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack
      THEN
         stack := DBMS_UTILITY.format_call_stack ();
      END IF;

      log_level (p_log_level        => g_warning,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END warning;

--
--
-- public
   PROCEDURE info (
      p_unit           IN   VARCHAR2,       -- should be set with $$PLSQL_UNIT
      p_line           IN   PLS_INTEGER,    -- should be set with $$PLSQL_LINE
      p_log_msg        IN   VARCHAR2,       -- the message to be logged
      p_record_stack   IN   BOOLEAN         -- record the call stack 
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack THEN 
	stack := dbms_utility.format_call_stack (); 
      END IF;

      log_level (p_log_level        => g_info,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END info;

--
--
-- private
   PROCEDURE log_snap (
      p_unit      IN   VARCHAR2,
      p_line      IN   PLS_INTEGER,
      p_log_msg   IN   VARCHAR2 DEFAULT ''
   )
   IS
   BEGIN
      OWA_UTIL.who_called_me (g_owner_name,
                              g_caller_name,
                              g_line_number,
                              g_caller_type
                             );
--
      log_level (p_log_level        => g_snap,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => g_caller_name,
                 p_line_number      => g_line_number
                );
   END log_snap;

--
--
-- public
   PROCEDURE entering (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2 DEFAULT '',
      p_record_stack   IN   BOOLEAN DEFAULT FALSE,
      p_set_action     IN   BOOLEAN DEFAULT TRUE
   )
   IS
      stack   VARCHAR2 (32767) := NULL;
   BEGIN
      IF p_record_stack
      THEN
         stack := DBMS_UTILITY.format_call_stack ();
      END IF;

      log_level (p_log_level        => g_entering,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   --
      IF p_set_action THEN set_action($$PLSQL_UNIT) ; END IF;
   END entering;

--
-- public
   PROCEDURE exiting (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2,
      p_record_stack   IN   BOOLEAN
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack
      THEN
         stack := DBMS_UTILITY.format_call_stack ();
      END IF;

      log_level (p_log_level        => g_exiting,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END exiting;

--
--
-- public
   PROCEDURE fine (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2,
      p_record_stack   IN   BOOLEAN
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack
      THEN
         stack := DBMS_UTILITY.format_call_stack ();
      END IF;

      log_level (p_log_level        => g_fine,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END fine;

--
--
-- public
   PROCEDURE finer (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2,
      p_record_stack   IN   BOOLEAN DEFAULT FALSE
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack THEN stack := DBMS_UTILITY.format_call_stack (); END IF;

      log_level (p_log_level        => g_finer,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END finer;

--
--
-- public
   PROCEDURE finest (
      p_unit           IN   VARCHAR2,
      p_line           IN   PLS_INTEGER,
      p_log_msg        IN   VARCHAR2,
      p_record_stack   IN   BOOLEAN DEFAULT FALSE
   )
   IS
      stack   VARCHAR2 (32767);
   BEGIN
      IF p_record_stack THEN stack := DBMS_UTILITY.format_call_stack (); END IF;

      log_level (p_log_level        => g_finest,
                 p_log_msg          => p_log_msg,
                 p_caller_name      => p_unit,
                 p_line_number      => p_line,
                 p_call_stack       => stack
                );
   END finest;

--
   PROCEDURE LOG (
      p_msg_id         IN   VARCHAR2,
      p_log_msg        IN   VARCHAR2,
      p_long_msg       IN   VARCHAR2,
      p_elapsed_time   IN   INTERVAL DAY TO SECOND DEFAULT NULL,
      p_log_level      IN   PLS_INTEGER,
      p_call_stack     IN   VARCHAR2 DEFAULT NULL
   )
   IS
      my_log_time    TIMESTAMP ( 6 )        := CURRENT_TIMESTAMP;
      my_elapsed     INTERVAL DAY TO SECOND;
      my_log_level   PLS_INTEGER;
   BEGIN
      my_log_level := p_log_level;

--
      IF my_log_level < 1 THEN my_log_level := 1; END IF;

--
      IF my_log_level > 9 THEN my_log_level := 9; END IF;

--
--    IF p_elapsed_time != NULL THEN
--        my_elapsed := p_elapsed_time;
--    ELSE
--        my_elapsed := my_log_time - g_last_log_time;
--    END IF ;
--
--    g_last_log_time := my_log_time;
--
      create_process_log (parm_ut_process_status_nbr   => g_process_status_nbr,
                          parm_log_msg_id              => p_msg_id,
                          parm_log_msg                 => p_log_msg,
                          parm_log_level               => my_log_level,
                          parm_elapsed_time            => my_elapsed,
                          parm_caller_name             => g_caller_name,
                          parm_line_number             => g_line_number,
                          parm_serial_number           => g_serial_nbr,
                          parm_call_stack              => p_call_stack
                         );
   END LOG;

--
--
   PROCEDURE snap_stats (p_snap_name IN VARCHAR2)
   IS
      my_log_time   TIMESTAMP ( 6 )        := CURRENT_TIMESTAMP;
      my_elapsed    INTERVAL DAY TO SECOND;
   BEGIN
--
      create_process_log (parm_ut_process_status_nbr      => g_process_status_nbr,
                          parm_log_msg_id                 => p_snap_name,
                          parm_log_msg                    => p_snap_name,
                          parm_log_level                  => g_snap,
                          parm_elapsed_time               => my_elapsed,
                          parm_caller_name                => g_caller_name,
                          parm_line_number                => g_line_number,
                          parm_serial_number              => g_serial_nbr
                         );

--
      INSERT INTO ut_process_stat (	
	     ut_process_status_nbr, log_seq_nbr,       statistic#,      VALUE
      )
      SELECT g_process_status_nbr, g_last_log_seq_nbr, stat.statistic#, stat.VALUE
        FROM SYS.v_$sesstat stat
       WHERE SID = g_sid;
--
   END snap_stats;

--
----
--    every log entry at or below the filter level are recorded.. only to the file
--
   PROCEDURE set_filter_level (p_level IN PLS_INTEGER)
   --% PROCEDURE set_filter_level (p_level IN PLS_INTEGER)
   --% log levels less than or equal to the specified value are written to the log file
   IS
   BEGIN
      IF p_level < 1 
      THEN
	g_filter_level := 1;
      ELSIF p_level > 9
      THEN 
	g_filter_level := 9;	
      ELSE 
      	g_filter_level := p_level;
      END IF;
   END set_filter_level;

   /**
   	PROCEDURE set_record_level (p_level IN PLS_INTEGER)
   */
   PROCEDURE set_record_level (p_level IN PLS_INTEGER)
   --% PROCEDURE set_record_level (p_level IN PLS_INTEGER)
   --% everything less than or equal to the record level gets written to the trace file
   IS
   BEGIN
      IF p_level < 1 
      THEN
	g_record_level := 1;
      ELSIF p_level > 9
      THEN 
	g_record_level := 9;	
      ELSE 
      	g_record_level := p_level;
      END IF;
   END set_record_level;

--
--
--      Wrapper for DBMS_APPLICATION_INFO.SET_ACTION
--
PROCEDURE set_action ( p_action IN VARCHAR2 )
IS
BEGIN
        dbms_application_info.set_action(SUBSTR(p_action, 1, 32)) ;
END set_action ;
--
--
--
--      Wrapper for DBMS_APPLICATION_INFO.SET_MODULE
--
PROCEDURE set_module ( p_module IN VARCHAR2 )
IS
BEGIN
        dbms_application_info.set_module(SUBSTR(p_module, 1, 48), 'Uncategorized') ;
END set_module ;
--
--




BEGIN
--    DBMS_OUTPUT.ENABLE(1000000) ;
--
   SELECT v.SID, v.serial#
     INTO g_sid, g_serial_nbr
     FROM SYS.v_$session v
    WHERE v.SID = (SELECT SID
                     FROM SYS.v_$mystat
                    WHERE ROWNUM = 1);

--
   set_context;
END logger;
/ 
show errors
