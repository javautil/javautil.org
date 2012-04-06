package com.dbexperts.oracle.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.dbexperts.oracle.dto.OracleSessionBase;

public class OracleSessionBaseS {
	static final String						selectText			= "" + "SELECT\n" + "    saddr,\n" + "    sid,\n"
																		+ "    serial#,\n" + "    audsid,\n" + "    paddr,\n"
																		+ "    user#,\n" + "    username,\n" + "    command,\n"
																		+ "    ownerid,\n" + "    taddr,\n" + "    lockwait,\n"
																		+ "    status,\n" + "    server,\n" + "    schema#,\n"
																		+ "    schemaname,\n" + "    osuser,\n" + "    process,\n"
																		+ "    machine,\n" + "    terminal,\n" + "    program,\n"
																		+ "    type,\n" + "    sql_address,\n"
																		+ "    sql_hash_value,\n" + "    prev_sql_addr,\n"
																		+ "    prev_hash_value,\n" + "    module,\n"
																		+ "    module_hash,\n" + "    action,\n"
																		+ "    action_hash,\n" + "    client_info,\n"
																		+ "    fixed_table_sequence,\n" + "    row_wait_obj#,\n"
																		+ "    row_wait_file#,\n" + "    row_wait_block#,\n"
																		+ "    row_wait_row#,\n" + "    logon_time,\n"
																		+ "    last_call_et,\n" + "    pdml_enabled,\n"
																		+ "    failover_type,\n" + "    failover_method,\n"
																		+ "    failed_over,\n" + "    resource_consumer_group,\n"
																		+ "    pdml_status,\n" + "    pddl_status,\n"
																		+ "    pq_status,\n" + "    current_queue_duration,\n"
																		+ "    client_identifier\n" + "FROM v$session\n";

	/**
	 * Container for rows retrieved from fetches in fetched sequence.
	 */
	private ArrayList<OracleSessionBase>	rows				= new ArrayList<OracleSessionBase>();

	/**
	 * HashMap based on obfuscated primary key.
	 */
	// HashMap map = new HashMap();
	/**
	 * Maintain persistent connection true, connection pool safe, true.
	 */
	boolean									persistConnection	= false;

	ResultSet								rset				= null;

	PreparedStatement						selectStmt			= null;

	protected static void getRow(final ResultSet rset, final OracleSessionBase row) throws java.sql.SQLException {
		String columnName = null;

		try {
			row.setSaddr(rset.getBytes(columnName = "saddr"));

			row.setSid(rset.getInt(columnName = "sid"));

			row.setSerialNbr(rset.getInt(columnName = "serial#"));

			row.setAudsid(rset.getInt(columnName = "audsid"));
			row.setPaddr(rset.getBytes(columnName = "paddr"));

			row.setUserNbr(rset.getInt(columnName = "user#"));
			row.setUsername(rset.getString(columnName = "username"));

			row.setCommand(rset.getInt(columnName = "command"));

			row.setOwnerid(rset.getInt(columnName = "ownerid"));
			row.setTaddr(rset.getString(columnName = "taddr"));
			row.setLockwait(rset.getString(columnName = "lockwait"));
			row.setStatus(rset.getString(columnName = "status"));
			row.setServer(rset.getString(columnName = "server"));
			row.setSchemaNbr(rset.getInt(columnName = "schema#"));
			row.setSchemaname(rset.getString(columnName = "schemaname"));
			row.setOsuser(rset.getString(columnName = "osuser"));
			row.setProcess(rset.getString(columnName = "process"));
			row.setMachine(rset.getString(columnName = "machine"));
			row.setTerminal(rset.getString(columnName = "terminal"));
			row.setProgram(rset.getString(columnName = "program"));
			row.setType(rset.getString(columnName = "type"));
			row.setSqlAddress(rset.getBytes(columnName = "sql_address"));
			row.setSqlHashValue(rset.getInt(columnName = "sql_hash_value"));
			row.setPrevSqlAddr(rset.getBytes(columnName = "prev_sql_addr"));
			row.setPrevHashValue(rset.getInt(columnName = "prev_hash_value"));
			row.setModule(rset.getString(columnName = "module"));
			row.setModuleHash(rset.getInt(columnName = "module_hash"));
			row.setAction(rset.getString(columnName = "action"));
			row.setActionHash(rset.getInt(columnName = "action_hash"));
			row.setClientInfo(rset.getString(columnName = "client_info"));
			row.setFixedTableSequence(rset.getInt(columnName = "fixed_table_sequence"));
			row.setRowWaitObjNbr(rset.getInt(columnName = "row_wait_obj#"));
			row.setRowWaitFileNbr(rset.getInt(columnName = "row_wait_file#"));
			row.setRowWaitBlockNbr(rset.getInt(columnName = "row_wait_block#"));
			row.setRowWaitRowNbr(rset.getInt(columnName = "row_wait_row#"));
			row.setLogonTime(rset.getTimestamp(columnName = "logon_time"));
			row.setLastCallEt(rset.getInt(columnName = "last_call_et"));
			row.setPdmlEnabled(rset.getString(columnName = "pdml_enabled"));
			row.setFailoverType(rset.getString(columnName = "failover_type"));
			row.setFailoverMethod(rset.getString(columnName = "failover_method"));
			row.setFailedOver(rset.getString(columnName = "failed_over"));
			row.setResourceConsumerGroup(rset.getString(columnName = "resource_consumer_group"));
			row.setPdmlStatus(rset.getString(columnName = "pdml_status"));
			row.setPddlStatus(rset.getString(columnName = "pddl_status"));
			row.setPqStatus(rset.getString(columnName = "pq_status"));
			row.setCurrentQueueDuration(rset.getInt(columnName = "current_queue_duration"));
			row.setClientIdentifier(rset.getString(columnName = "client_identifier"));
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column" + columnName + "\n" + s.getMessage());
		}
	} // end of getRow

	/** Default constructor for OracleSessionBase. */
	public OracleSessionBaseS() {
	}

	public void add(final OracleSessionBase row) {
		getRows().add(row);
	}

	public void clear() {
		getRows().clear();
	}

	public void connectionPersistenceBegin() {
		persistConnection = true;
	}

	public void connectionPersistenceEnd() throws java.sql.SQLException {
		persistConnection = false;
		if (selectStmt != null) {
			selectStmt.close();
			selectStmt = null;
		}
	}

	/** Return the rows iterator. */
	public Iterator<OracleSessionBase> iterator() {
		return getRows().iterator();
	}

	/** Return the number of the rows contained. */
	public int size() {
		return getRows().size();
	}

	public void setRows(final ArrayList<OracleSessionBase> rows) {
		this.rows = rows;
	}

	public ArrayList<OracleSessionBase> getRows() {
		return rows;
	}
} // end of class
