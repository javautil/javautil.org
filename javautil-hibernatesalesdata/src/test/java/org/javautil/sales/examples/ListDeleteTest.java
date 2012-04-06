//package org.javautil.sales.examples;
//
//import static org.junit.Assert.assertEquals;
//
//import java.math.BigInteger;
//import java.util.List;
//
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
//import org.hibernate.Query;
//import org.javautil.sales.Salesperson;
//import org.junit.BeforeClass;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import org.javautil.lang.AsString;
//import org.javautil.persist.hibernate.Dao;
//import org.javautil.persist.hibernate.DaoImpl;
//import org.javautil.persist.hibernate.Persistence;
//
///**
// * 
// * TODO show how using an Oracle Connection with caching decreases parses
// * @author jjs
// *
// */
////TODO make this some real unit test
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:application-context.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
//public class ListDeleteTest {
//	
//	@Autowired
//	private Persistence persistence;
//	
//	private Logger logger = Logger.getLogger(getClass());
//	
//	@BeforeClass
//	public static void beforeClass() {
//		BasicConfigurator.configure();
//	}
//	
//	public List<Salesperson> getSalesPeople(String lowerLastName, String upperLastName) {
//		persistence.beginTransaction();  // todo think about transaction management
//		Query q = persistence.getSession().createQuery(
//				" from Salesperson" +  //
//				" where upper(last_name) >= :lowerLastName " + 
//				" and upper(last_name) <=  :upperLastName) ");
//				
//		q.setString("lowerLastName", lowerLastName);
//		q.setString("upperLastName",upperLastName);
//		List<Salesperson> list = q.list();
//		logger.debug("lowerLastName " + lowerLastName + " upperLastName '" + upperLastName + " count " + list.size());
//		return list;
//
//	}
//	
//	public List<Salesperson> getAllSalespersons() {
//		persistence.beginTransaction();  // todo think about transaction management
//		Query q = persistence.getSession().createQuery(
//				"from Salesperson");
//		List<Salesperson> salespersons = q.list();
//		return salespersons;
//	}
//
//	@Test
//	public void ensureCount() {
//		persistence.beginTransaction();  // todo think about transaction management
//		Query q = persistence.getSession().createSQLQuery(
//				" select count(*) from Salesperson");
//		List<BigInteger> counts = q.list();
//		BigInteger count = counts.get(0);
//		assertEquals(5,count.intValue()); 
//		// TODO need to use the correct Count object
//	}
//	/**
//	 * The list on an HQL query is not backed .
//	 */
//
//	@Test
//	public void listDelete() {
//		// get some salespersons
//		List<Salesperson> salespersons = getSalesPeople("A","Z");
//		int initialSalespersonsSize = salespersons.size();
//		logger.info("salespersons count " + salespersons.size());
//		// TODO turn into logger messages
//		System.out.println("*******************************");
//		
//		System.out.println("salespersons count " + salespersons.size());
//		// remove one from the list
//		salespersons.remove(0);
//		
//		// get them again
//		List<Salesperson> salespersons2 = getSalesPeople("A","Z");
//		assertEquals(initialSalespersonsSize,salespersons2.size());
//	
//		System.out.println("salespersons2 count " + salespersons2.size());
//		// flushing doesn't affect 
//		persistence.getSession().flush();
//		List<Salesperson> salespersonList = getAllSalespersons();
//		assertEquals(salespersons2.size(),salespersonList.size());
//		persistence.getSession().getTransaction().commit();
//		
//		List<Salesperson> salespersons3 = getSalesPeople("A","Z");
//		assertEquals(initialSalespersonsSize,salespersons3.size());
//		System.out.println("salespersons3 count " + salespersons3.size());		
//	}
//
//	
//	/**
//	 * Explicit deletes are not batched.
//	 * 
//	 * todo query v$wait, etc from another session to eliminate need to show trace file 
//	 */
//	@Test
//	public void explicitDelete() {
//		List<Salesperson> salespersons = getSalesPeople("A","Z");
//
//		logger.info("*******************************");
//		System.out.println("salespersons count " + salespersons.size());
//
//		logger.info("salespersons count " + salespersons.size());
//		Dao dao =  persistence.get(Salesperson.class);
//		Salesperson salesperson = salespersons.get(0);
//		AsString as = new AsString();
//		String salespersonString = as.toString(salesperson);
//		System.out.println("about to delete " + salespersonString);
//		persistence.getSession().delete(salesperson);
//		//dao.delete(salesperson);
//		// note no flush
//		// a query back to the database?  todo demonstrate that happened
//		List<Salesperson> salespersons2 = getSalesPeople("A","Z");
//		assertEquals(salespersons.size(),salespersons2.size());
//		System.out.println("salespersons2 count " + salespersons2.size());
//		
//		// Now we flush
//		persistence.getSession().flush();
//		persistence.getSession().getTransaction().commit();
//		// a query back to the database?  The record is still in the list
//		List<Salesperson> salespersons3 = getSalesPeople("A","Z");
//		assertEquals(salespersons.size() - 1,salespersons3.size());
//		System.out.println("salespersons3 count " + salespersons3.size());
//	}
//	
//	@Test
//	public void manufacturedDelete() {
//		persistence.getSession().beginTransaction();
//		Salesperson cm = new Salesperson();
//		cm.setSalespersonId(1);
//		persistence.getSession().delete(cm);
//		persistence.getSession().flush();
//		List<Salesperson> salespersonsAfterDelete = getAllSalespersons();
//		persistence.getSession().getTransaction().commit();
//
//		logger.info("*******************************");
//		System.out.println("salespersons count " + salespersonsAfterDelete.size());
//	}
//	
//	@Test
//	public void deleteNewAdd() {
//		Salesperson newPerson = new Salesperson();
//		newPerson.setLastName("aa");
//		newPerson.setFirstName("bb");
//		persistence.getSession().beginTransaction();
//		persistence.getSession().save(newPerson);
//		
//		List<Salesperson> salespersons = getAllSalespersons();
//		System.out.println("salespersons count " + salespersons.size());
//		persistence.getSession().flush();
//		List<Salesperson> salespersonsAfterFlush = getAllSalespersons();
//		System.out.println("salespersonsAfterFlush count " + salespersonsAfterFlush.size());
//		persistence.getSession().delete(newPerson);
//		
//		List<Salesperson> salespersonsAfterDelete = getAllSalespersons();
//		
//
//		System.out.println("salespersonsAfterDelete count " + salespersonsAfterDelete.size());
//		
//	}
//
//	/**
//	 * The list on an HQL query is not backed .
//	 * 
//	 * todo prove how many executes took place
//	 */
//	@Test
//	public void explicitMultiDelete() {
//		List<Salesperson> salespersons = getSalesPeople("A","D");
//
//		logger.info("salespersons count " + salespersons.size());
//		System.out.println("*******************************");
//		System.out.println("salespersons count " + salespersons.size());
//		Dao<Salesperson,Integer> dao = new DaoImpl(salespersons.get(0).getClass());
//		for (Salesperson salesperson : salespersons) {
//			dao.delete(salesperson);
//		}
//		List<Salesperson> salespersons2 = getSalesPeople("A","D");
//		assertEquals(0,salespersons2.size());
//	
//	}
//	
//	
////	}
// }
