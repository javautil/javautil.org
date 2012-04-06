package org.javautil.oracle.dbmspipe;

import java.sql.CallableStatement;
import java.sql.Connection;

public class PipeWriter {

	public static void packMessage(final Connection dbc, final String message)
			throws java.sql.SQLException {
		CallableStatement packStmt = null;
		try {
			final String packText = " begin \n "
					+ "			dbms_pipe.pack_message(?) ; \n " + " end; \n ";
			packStmt = dbc.prepareCall(packText);
			packStmt.setString(1, message);
			packStmt.executeUpdate();
		} finally {
			if (packStmt != null) {
				packStmt.close();
			}
		}
	}

	public static void sendMessage(final Connection dbc, final String pipeName)
			throws java.sql.SQLException {
		CallableStatement sendStmt = null;
		try {
			final String sendText = " declare \n " + "     rc number ; \n "
					+ " begin \n " + "			rc := dbms_pipe.send_message(?) ; \n "
					+ " end; \n ";
			sendStmt = dbc.prepareCall(sendText);
			sendStmt.setString(1, pipeName);
			sendStmt.executeUpdate();
		} finally {
			sendStmt.close();
		}
	}

}
