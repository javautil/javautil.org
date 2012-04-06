#!/bin/bash
if [ -z $ORACLE_UID ] ; then
 	echo please set ORACLE_UID >&2
	exit 1
fi

time sqlplus $ORACLE_UID @sample_job_01

LOGFILE_NAME=/scratch/sample_job*
wc -l $LOGFILE_NAME 

head  $LOGFILE_NAME 
tail  $LOGFILE_NAME 


