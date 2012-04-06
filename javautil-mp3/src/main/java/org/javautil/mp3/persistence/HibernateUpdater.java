package org.javautil.mp3.persistence;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

public class HibernateUpdater {
	private Session session;

	// Supposing you have to fetch a large resultset and update the single
	// objects. By using the standard Query Object you would retrieve the whole
	// set of Objects in Memory:
	// view plaincopy to clipboardprint?
	//
	// 1. Query q = session.createQuery("from ABC");
	// 2. List l = q.list();
	//
	// Query q = session.createQuery("from ABC");
	// List l = q.list();
	//
	//
	// If you need to operate on an online cursor, then you can use the
	// ScrollableResults:
	// view plaincopy to clipboardprint?
	//
	// 1. Session session = sessionFactory.openSession();
	// 2. Transaction tx = session.beginTransaction();
	// 3. ScrollableResults itemCursor =
	// 4. session.createQuery("from Account").scroll();
	// 5. int count=0;
	// 6. while ( itemCursor.next() ) {
	// 7. Account a = (Account) itemCursor.get(0);
	// 8. modifyObject(a);
	// 9. if ( ++count % 100 == 0 ) {
	// 10. session.flush();
	// 11. session.clear();
	// 12. }
	// 13. }
	// 14. tx.commit();
	// 15. session.close();

	ScrollableResults getScrollableResults() {
		final Query q = session.createQuery("");
		q.getReturnAliases();

		final ScrollableResults results = session.createQuery("from Mp3")
				.scroll();
		return results;
	}

	// TODO Do something with this
	void updateData() {
		final ScrollableResults results = getScrollableResults();
		while (results.next()) {
			// This kind of sucks because there is no way to get by name
		}

	}
}
