package org.javautil.oracle.performance;



	import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.javautil.oracle.OracleConnectionHelper;
import org.javautil.oracle.dto.OracleSessionWait;

//import com.dbexperts.oracle.OracleSessionStatSet;
//import com.dbexperts.oracle.WrappedOracleConnection;
//import com.dbexperts.oracle.dao.OracleSessionWaits;
//import com.dbexperts.oracle.dto.OracleSessionWait;


	public class OracleSessionStatistics {

	        private final Logger logger = Logger.getLogger(getClass().getName());
	        private static final String newline = System.getProperty("line.separator");


	        public static void showStats(final Connection conn) throws SQLException {
	        	// TODO return String
	        	throw new UnsupportedOperationException("TODO FIX");
//	                final Logger logger = Logger.getLogger(OracleSessionStatistics.class);
//	                OracleConnectionHelper oconn = OracleConnectionHelper.getInstance(conn);
//	                // TODO need to check if this is ORACLE in some way that doesn't suck
//	                        final OracleSessionStatSet stats = oconn.getSessionStats();
//	                        final String waitsFormatted = stats.asString(oconn.getStatNames());
//	                        // Todo this is an exceptionally squirrelly way to do anything and will muck things up if Running\
//	                        // multiple threads against the root logger
//	                        /// todo strip dexterous
//	                        System.out.println("Dexterous showStats " + waitsFormatted);
//	                        final Level before = logger.getLevel();
//	                        final Logger l = Logger.getLogger("");
//	                        l.setLevel(Level.INFO);
//
//	                        l.info(waitsFormatted);
//	                        l.setLevel(before);
	        }

	        public static void showWait(final Connection conn ) throws SQLException {
	        	throw new UnsupportedOperationException("TODO FIX");
//	                final Logger logger = Logger.getLogger(OracleSessionStatistics.class);
//	                //logger.error("in showWait");
//	                OracleConnectionHelper oconn = new OracleConnectionHelper(conn);
//	                if (conn instanceof WrappedOracleConnection) {
//	                        final WrappedOracleConnection oconn = (WrappedOracleConnection) conn;
//	                        final ArrayList<OracleSessionWait> waits = oconn.getSessionWaits();
//	                        final String waitsFormatted = OracleSessionWaits.format(waits);
//	                        final Level before = logger.getLevel();
//	                        final Logger l = Logger.getLogger("");
//	                        l.setLevel(Level.INFO);
//	                        // TODO evil
//	                        System.out.println("Dexterous.showWait " + newline + waitsFormatted);
//	                        l.info(waitsFormatted);
//	                        l.setLevel(before);
//	                } else {
//	                        logger.warn("show waits ignored - connection is not to an oracle database");
//	                }
	        }
	}
