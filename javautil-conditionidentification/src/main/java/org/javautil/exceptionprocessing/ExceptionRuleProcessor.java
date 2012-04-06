package org.javautil.exceptionprocessing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.javautil.exceptionprocessing.dto.GttTableRowMsgId;
import org.javautil.exceptionprocessing.dto.GttUtTableRowMsg;
import org.javautil.exceptionprocessing.dto.UtRuleGrpRunParm;
import org.javautil.exceptionprocessing.dto.UtTableRule;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;
import org.javautil.sql.QueryHelper;

public class ExceptionRuleProcessor implements Runnable {

	private final ExceptionProcessingRulesDAO ruleDAO;
	private final ExceptionProcessingDataSourcesDAO datasourceDao;
	private Connection sourceConnection;
	private Connection destinationConnection;
	private final UtTableRule rule;
	private QueryHelper inStmt;
	private ResultSet rset;
	private Exception processingException;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private ResultSetMetaData metaIn;
	private final Integer runNbr;
	private final Set<UtRuleGrpRunParm> binds;
	private boolean done = false;
	private final int batchSize = 100;

	/**
	 * A stupid object to synchronize run and getException - value is
	 * irrelevent.
	 */
	private final Boolean runningMonitor = Boolean.FALSE;

	/**
	 * 
	 * Processes the specified rule query
	 * 
	 * @see UtTableRule
	 * 
	 * @param sourceConn
	 * @param destConn
	 * @param rule
	 * @param runNbr
	 * @param binds
	 *            TODO why is this not a map? TODO populate binds
	 * @throws SQLException
	 */
	public ExceptionRuleProcessor(final UtTableRule rule, final Integer runNbr,
			final Set<UtRuleGrpRunParm> binds,
			final ExceptionProcessingRulesDAO ruledao,
			final ExceptionProcessingDataSourcesDAO datasourcedao)
			throws SQLException {

		if (rule == null) {
			throw new IllegalArgumentException("rule is null");
		}
		if (runNbr == null) {
			throw new IllegalArgumentException("runNbr is null");
		}
		if (binds == null) {
			throw new IllegalArgumentException("binds is null");
		}

		this.rule = rule;
		this.runNbr = runNbr;
		this.binds = binds;
		this.datasourceDao = datasourcedao;
		this.ruleDAO = ruledao;

	}

	/**
	 * @return the processingException
	 */
	public Exception getProcessingException() {
		synchronized (runningMonitor) {
			return processingException;
		}
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// todo get binds
		synchronized (runningMonitor) {
			try {
				getConnections();
				prepareIn();
				pump();
			} catch (final SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				processingException = e;
			} finally {
				dispose();
				done = true;
			}
		}
	}

	private void dispose() {
		try {
			sourceConnection.close();
		} catch (final SQLException e) {
			logger.error(e);
		}
		if (destinationConnection != sourceConnection) {
			try {
				destinationConnection.close();
			} catch (final SQLException e) {
				logger.error(e);
			}
		}

	}

	private void getConnections() {
		try {
			this.sourceConnection = SimpleDatasourcesFactory.getDataSource(
					rule.getDataSrcNmSrc()).getConnection();
			if (rule.getDataSrcNmDest().equals(rule.getDataSrcNmSrc())) {
				this.destinationConnection = this.sourceConnection;
			} else {
				this.destinationConnection = SimpleDatasourcesFactory.getDataSource(
						rule.getDataSrcNmDest()).getConnection();
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void prepareIn() throws SQLException {
		inStmt = new QueryHelper(sourceConnection, rule.getSqlText());
		if (rule.getMaxQuerySec() != null) {
			logger.info("maxQuerySec " + rule.getMaxQuerySec());
		}
		// not support
		// inStmt.setMaxQuerySec(rule.getMaxQuerySec());
		// inStmt.setIgnoreUnnecessaryBinds(true);
		String bindName = "";
		for (final UtRuleGrpRunParm parm : binds) {
			bindName = parm.getId().getParmNm();
			if (parm.getParmValueNumber() != null) {
				logger.info("binding " + bindName + " to "
						+ parm.getParmValueNumber());
				inStmt.setNumber(bindName, parm.getParmValueNumber());
			} else if (parm.getParmValueDate() != null) {
				logger.info("binding " + bindName + " to "
						+ parm.getParmValueDate());
				inStmt.setDate(bindName, parm.getParmValueDate());
			} else if (parm.getParmValueString() != null) {
				logger.info("binding " + bindName + " to "
						+ parm.getParmValueString());
				inStmt.setString(bindName, parm.getParmValueString());
			} else {
				// not supported
				// inStmt.bindNull(bindName);
			}
		}
		try {
			rset = inStmt.executeQuery();
		} catch (final SQLException sqe) {
			throw sqe;
		}
		metaIn = rset.getMetaData();
		if (metaIn.getColumnCount() > 2) {
			throw new IllegalArgumentException(
					"select statement returns more than 2 columns");
		}
		if (!(metaIn.getColumnType(1) == java.sql.Types.NUMERIC)) {
			throw new IllegalArgumentException(
					"first column in select is not a number");
		}
	}

	private void pump() throws SQLException {
		ruleDAO.beginTransaction();
		ruleDAO.clearTemporary();
		final boolean hasMessage = metaIn.getColumnCount() > 1;
		int msgCount = 0;
		while (rset.next()) {
			msgCount++;
			final GttUtTableRowMsg m = new GttUtTableRowMsg();
			final GttTableRowMsgId id = new GttTableRowMsgId();
			id.setPrimaryKey(Integer.valueOf((int) rset.getLong(1)));
			id.setUtRuleGrpRunNbr(runNbr);
			id.setUtTableRuleNbr(rule.getUtTableRuleNbr());
			if (hasMessage) {
				m.setMsg(rset.getString(2));
			}
			m.setId(id);
			ruleDAO.save(m);
			ruleDAO.flush(); // TODO review why flush with every
			if (msgCount % batchSize == 0) {
				ruleDAO.evict(GttUtTableRowMsg.class); // TODO review
			}
		}

		logger.info("inserted " + msgCount + " messages");
		datasourceDao.persist(ruleDAO.connection(), runNbr,
				rule.getUtTableRuleNbr());
		// ruleDAO.commit(tx);
		inStmt.destroy();
	}
}
