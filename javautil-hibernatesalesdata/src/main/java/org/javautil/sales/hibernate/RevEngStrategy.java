package org.javautil.sales.hibernate;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * This hibernate reveng strategy overrides default strategy.
 * 
 */
public class RevEngStrategy extends DelegatingReverseEngineeringStrategy {
	private final Logger logger = Logger.getLogger(getClass());

	static {
		System.out.println("Right RevEngStrategy");
	}

	public RevEngStrategy(final ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	/**
	 * Set default generation strategy to be sequence.
	 */
	@Override
	public String getTableIdentifierStrategyName(final TableIdentifier ti) {
		String configuredName = super.getTableIdentifierStrategyName(ti);
		if (configuredName == null) {
			configuredName = "sequence";
		}

		return configuredName;
	}

	/**
	 * Set sequence generation strategy properties. Convention: Sequence name is
	 * PK column name + _SEQ
	 */
	@Override
	public Properties getTableIdentifierProperties(final TableIdentifier ti) {
		logger.info("processing table " + ti.getName());
		System.out.println("processing table " + ti.getName());
		Properties props = super.getTableIdentifierProperties(ti);
		if (props == null) {
			props = new Properties();
			// TODO shouldn't this be done whether or not it is null?
			props.put("sequence", ti.getName() + "_SEQ");
		}
		return props;
	}

}
