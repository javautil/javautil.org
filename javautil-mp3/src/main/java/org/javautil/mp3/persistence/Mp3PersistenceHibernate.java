package org.javautil.mp3.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.io.ResourceText;
import org.javautil.mp3.ArtistSuspect;
import org.javautil.mp3.Mp3;
import org.javautil.mp3.Mp3Metadata;
import org.javautil.mp3.formatter.Mp3Mapper;
import org.javautil.mp3.formatter.Mp3MapperImpl;
import org.springframework.beans.factory.InitializingBean;

public class Mp3PersistenceHibernate extends AbstractPersistence implements
		Mp3Persistence, InitializingBean {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private SessionFactory sessionFactory;

	private Session session;

	private final Mp3Mapper mapper = new Mp3MapperImpl();

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Mp3PersistenceHibernate() {

	}

	@Override
	public void afterPropertiesSet() {
		if (sessionFactory == null) {
			throw new IllegalArgumentException("sessionFactory is null");
		}
		startTransaction();

	}

	void startTransaction() {
		sessionFactory.openSession();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
	}

	String getSql(final String resourceName) throws IOException {
		final ResourceText rt = new ResourceText();
		return rt.getResourceText(resourceName);
	}

	@Override
	public List<ArtistSuspect> getArtistSuspects() {
		final List<ArtistSuspect> suspects = new ArrayList<ArtistSuspect>();
		startTransaction();
		String sql = null;
		try {
			sql = getSql("classpath:sql/selectArtist.sql");
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		final Query q = session.createSQLQuery(sql);

		final List<Object[]> results = q.list();
		for (final Object[] row : results) {
			final String artistName = (String) row[0];
			final Number count = (Number) row[1];
			final ArtistSuspect suspect = new ArtistSuspect(artistName,
					count.intValue());
			suspects.add(suspect);

		}
		return suspects;
		// queryText =
		// "select upper(artist_name), count(*) from mp3 group by upper(artist_name) order by upper(artist_name) "
	}

	@Override
	public void save(final Mp3Metadata mp3data) {
		final Mp3 mp3 = mapper.toMp3(mp3data);
		save(mp3);

	}

	public void save(final Mp3 mp3) {
		session.save(mp3);
		inserted(1);
		checkCommit();
	}

	@Override
	public void flushAndCommit() {
		session.flush();
		commit();
	}

	// TODO begin only if necessary

	@Override
	public void commit() {
		session.getTransaction().commit();
		startTransaction();
	}

	@Override
	public void dispose() {
		flushAndCommit();
		session.close();
	}

	@Override
	public List<Mp3> getByTitle(final String songTitle) {
		final Query q = session
				.createQuery("from Mp3 where songTitle = :title");
		q.setString("title", songTitle);
		final List<Mp3> results = q.list();
		return results;
	}

	@Override
	public List<Mp3> getAll() {
		final Query q = session.createQuery("from Mp3");
		final List<Mp3> results = q.list();
		return results;
	}
}
