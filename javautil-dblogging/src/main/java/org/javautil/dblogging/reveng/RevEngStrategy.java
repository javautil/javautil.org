package org.javautil.dblogging.reveng;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * This hibernate reveng strategy overrides default strategy.
 * 
 * @author tim@softwareMentor.com
 */
public class RevEngStrategy extends DelegatingReverseEngineeringStrategy {

	private final Logger logger = Logger.getLogger(getClass());
	Map<String, String> tableNames = new HashMap<String, String>();

	public RevEngStrategy(final ReverseEngineeringStrategy delegate) {
		super(delegate);
		System.out.println("in RevEngStrategy");
	}

	/**
	 * Set default generation strategy to be sequence.
	 */
	@Override
	public String getTableIdentifierStrategyName(final TableIdentifier ti) {
		System.out.println("examining " + ti.getName());
		String configuredName = super.getTableIdentifierStrategyName(ti);
		if (configuredName == null) {
			configuredName = "sequence";
		}

		return configuredName;
	}

	// TODO nuke
	// @Override
	// public boolean excludeTable(final TableIdentifier ti) {
	// boolean retval = true;
	// if (tableNames.size() == 0) {
	// populateTableNames();
	// }
	// if ("GUS".equals(ti.getSchema())
	// && ti.getName().equals(tableNames.get(ti.getName()))) {
	// retval = false;
	// System.out.println("include table " + ti.getSchema() + "."
	// + ti.getName());
	// } else {
	// // System.out.println("Exclude table "+ ti.getSchema() + "." +
	// // ti.getName());
	// }
	// return retval;
	// }

	/**
	 * Set sequence generation strategy properties. Convention: Sequence name is
	 * PK column name + _SEC
	 */
	@Override
	public Properties getTableIdentifierProperties(final TableIdentifier ti) {
		Properties props = super.getTableIdentifierProperties(ti);
		if (props == null) {
			props = new Properties();
			props.put("sequence", ti.getName() + "_NBR_SEQ");
		}
		return props;
	}

	// TODO nuke
	// private void populateTableNames() {
	// tableNames.put("UT_PROCESS_STATUS", "UT_PROCESS_STATUS");
	// tableNames.put("UT_PROCESS_LOG", "UT_PROCESS_LOG");
	// tableNames.put("UT_PROCESS_STAT", "UT_PROCESS_STAT");
	// }

}
