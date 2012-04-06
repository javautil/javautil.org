package org.javautil.exceptionprocessing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.javautil.exceptionprocessing.dto.UtRuleGrpDtl;
import org.javautil.exceptionprocessing.dto.UtRuleGrpRun;
import org.javautil.exceptionprocessing.dto.UtRuleGrpRunParm;
import org.javautil.exceptionprocessing.dto.UtTableRule;

/**
 * Service Parameters SQL_TEXT - the SQL statement to be validated.
 * DATASOURCE_NAME the name of the DataSource
 * 
 * @author jim
 * 
 */

public class ExceptionRuleServiceImpl implements ExceptionRuleService {

	public static final String revision = "$Revision: 1.2 $";

	private ExceptionProcessingRulesDAO ruledao;
	private ExceptionProcessingDataSourcesDAO datasourcedao;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private Integer runNbr;

	private boolean processSuccess;

	private static final int THREAD_POOL_SIZE = 3;

	private ExecutorService threadpool;

	public ExceptionRuleServiceImpl() {
		logger.info("instantiated " + revision);
		if (ruledao == null) {
			ruledao = new ExceptionProcessingRulesDAOImpl();
		}
		if (datasourcedao == null) {
			datasourcedao = new ExceptionProcessingDataSourcesDAOImpl();
		}
	}

	@Override
	public boolean process(final ExceptionProcessingServerArgs arguments) {

		// long processStartTime = System.nanoTime();

		try {
			runNbr = arguments.getRunNumber();
			final UtRuleGrpRun ruleGroupRun = ruledao.getUtGrpRun(runNbr);
			threadpool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			final Set<UtRuleGrpDtl> Set = Collections
					.synchronizedSet(ruleGroupRun.getUtRuleGrp()
							.getUtRuleGrpDtls());
			for (final UtRuleGrpDtl ruleHdr : Set) {
				final UtTableRule rule = ruleHdr.getUtTableRule();
				queueRule(rule, ruleGroupRun.getUtRuleGrpRunParms(), ruledao,
						datasourcedao);
			}

			threadpool.shutdown();
			processSuccess = true;
		} catch (final Exception sqe) {
			processSuccess = false;
			throw new RuntimeException(sqe);
		}
		// long endTime = System.nanoTime();
		// double elapsedSeconds = (endTime - processStartTime) / 1e9;
		return processSuccess;
	}

	public void queueRule(final UtTableRule rule,
			final Set<UtRuleGrpRunParm> parms,
			final ExceptionProcessingRulesDAO ruledao,
			final ExceptionProcessingDataSourcesDAO datasourcedao)
			throws SQLException, IOException {
		logger.info("processing rule " + rule.getUtTableRuleNbr() + " "
				+ rule.getMsgId());
		// long startTime = System.currentTimeMillis();
		// Timestamp startTimestamp = new Timestamp(startTime);
		final ExceptionRuleProcessor erp = new ExceptionRuleProcessor(rule,
				runNbr, parms, ruledao, datasourcedao);
		threadpool.execute(erp);

		// long endTime = System.currentTimeMillis();
		// Timestamp endTimestamp = new Timestamp(endTime);
		// Exception runException = erp.getProcessingException();
	}

}
