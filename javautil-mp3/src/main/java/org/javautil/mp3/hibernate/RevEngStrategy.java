package org.javautil.mp3.hibernate;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * This hibernate reveng strategy overrides default strategy.
 * 
 * @see http 
 *      ://docs.jboss.org/tools/2.1.0.Beta1/hibernatetools/html/reverseengineering
 *      .html#custom-reveng-strategy
 */
public class RevEngStrategy extends DelegatingReverseEngineeringStrategy {

	private final Logger logger = Logger.getLogger(getClass());

	public RevEngStrategy(final ReverseEngineeringStrategy delegate) {
		super(delegate);
		System.out.println("**************************************");
		logger.info("instantiating");
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
	 * PK column name + _SEC
	 */

	@Override
	public Properties getTableIdentifierProperties(final TableIdentifier ti) {
		Properties props = super.getTableIdentifierProperties(ti);
		if (props == null) {
			props = new Properties();
			props.put("sequence", ti.getName() + "_ID_SEQ");
		}
		return props;
	}

}
