package org.javautil.sales.testdata;

import org.hibernate.SessionFactory;
import org.javautil.hibernate.persist.Persistence;
import org.javautil.sales.populate.DataPopulator;
import org.javautil.sales.populate.DataPopulatorArguments;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 

 */
public class SalesDataGenerator implements InitializingBean {

	@Autowired
	private DataPopulatorArguments args;
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static void main(final String args[]) throws Exception {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:SalesDataGenerator-context.xml");
		final SalesDataGenerator generator = (SalesDataGenerator) context
				.getBean("SalesDataGenerator");
		generator.populateData();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory is null");
		}
		if (args == null) {
			throw new IllegalStateException("args is null");
		}
	}

	/**
	 * @return the args
	 */
	public DataPopulatorArguments getArgs() {
		return args;
	}

	

	public void populateData() throws Exception {
		afterPropertiesSet();
		final DataPopulator dp = new DataPopulator();
		dp.setSessionFactory(this.getSessionFactory());
		dp.setArguments(args);
		dp.init();
		dp.populate();
	}

	/**
	 * @param args
	 *            the args to set
	 */
	public void setArgs(final DataPopulatorArguments args) {
		this.args = args;
	}


}
