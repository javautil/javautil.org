set echo on
CREATE OR REPLACE PACKAGE logger AS
--
	G_SEVERE						CONSTANT PLS_INTEGER := 1 ;
	G_WARNING						CONSTANT PLS_INTEGER := 2 ;
	G_INFO							CONSTANT PLS_INTEGER := 4 ;
	G_SNAP							CONSTANT PLS_INTEGER := 5 ;
	G_ENTERING						CONSTANT PLS_INTEGER := 6 ;
	G_EXITING						CONSTANT PLS_INTEGER := 6 ;
	G_FINE							CONSTANT PLS_INTEGER := 7 ;
	G_FINER							CONSTANT PLS_INTEGER := 8 ;
	G_FINEST						CONSTANT PLS_INTEGER := 9 ;
	G_NONE							CONSTANT PLS_INTEGER := 10 ;

--
PROCEDURE begin_job (
	parm_process_name				IN		VARCHAR2 DEFAULT NULL );
--
--
-- various log convenience methods
PROCEDURE severe (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE warning (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE info (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE entering (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE exiting (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE fine (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE finer (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE finest (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 DEFAULT '',
	p_record_stack					IN		BOOLEAN DEFAULT FALSE ) ;
--
--
PROCEDURE end_job ;
--
--
PROCEDURE abort_job ;
--
--
PROCEDURE log_snap (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER,
	p_log_msg						IN		VARCHAR2 ) ;
--
--
PROCEDURE snap_stats (
	p_snap_name						IN		VARCHAR2 ) ;
--
--
PROCEDURE COMMIT (
	p_unit							IN		VARCHAR2,
	p_line							IN		PLS_INTEGER ) ;
--
--
PROCEDURE set_action (
	p_action						IN		VARCHAR2) ;
--
--
PROCEDURE set_module (
	p_module						IN		VARCHAR2) ;
--
--
PROCEDURE set_dbms_output_level(
	p_level							IN		PLS_INTEGER ) ;
--
--
PROCEDURE set_filter_level (  
	p_level							IN		PLS_INTEGER ) ;
--
--
PROCEDURE set_record_level (
	p_level							IN		PLS_INTEGER ) ;
--
--
END logger ;
/


