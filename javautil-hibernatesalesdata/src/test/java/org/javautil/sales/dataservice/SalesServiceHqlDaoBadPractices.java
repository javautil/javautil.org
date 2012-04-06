package org.javautil.sales.dataservice;

import org.hibernate.Query;
import org.javautil.hibernate.persist.Persistence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This illustrates two approaches to emptying a table using HQL.
 * 
 * One see {@link #emptyGttNumberBad()} is evil.
 * 
 * @author jjs
 * 
 */
public class SalesServiceHqlDaoBadPractices {
	@Autowired
	private Persistence daoFactory;

	/**
	 * Delete everything in the gtt_number table.
	 * 
	 * An easy, but expensive way to do a delete.
	 * 
	 * All of the rows in the table are read into memory. Then the database
	 * blocks are revisited to delete them.
	 * 
	 * todo show stats before and after
	 */
	void emptyGttNumberBad() {
		final String hqlText = "from GttNumber";
		final Query q = daoFactory.getSession().createQuery(hqlText);
		q.list().clear();
	}

	void emptyGttNumber() {
		final String hqlText = "delete from GttNumber";
		daoFactory.getSession().createQuery(hqlText).executeUpdate();
	}
}
