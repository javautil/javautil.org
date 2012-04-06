package org.javautil.hibernate.reveng;

import java.util.Properties;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * This hibernate reveng strategy overrides default strategy.
 * 
 * @author tim@softwareMentor.com
 */
public class RevEngStrategy extends DelegatingReverseEngineeringStrategy {

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

}
