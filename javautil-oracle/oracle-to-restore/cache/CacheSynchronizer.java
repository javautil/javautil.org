package com.dbexperts.oracle.cache;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;

/**
Version:   $Id: CacheSynchronizer.java,v 1.1 2011/08/31 21:09:58 jjs Exp $
*/
//public class CacheManager extends Thread {
class CacheSynchronizer extends Thread {
	private static Logger		logger					= Logger.getLogger(CacheSynchronizer.class.getName());
	private CacheManager		manager					= null;
	private final OracleConnection	dbc;
	private final boolean				refreshInvalidCaches	= true;

	private boolean	fatal	= false;

	CacheSynchronizer(final CacheManager manager, final OracleConnection conn) {
		this.manager = manager;
		this.dbc = conn;
	}

	/**
	 * @throws SQLException
	 * @todo support multiple refresh threads through multiple connections.
	 *
	 */
	public void refresh() {
		// OracleCache cache = null;
		final UtCacheS utCacheS = manager.getUtCacheS();
		try {
			utCacheS.refresh(dbc);
		} catch (final SQLException e1) {
			logger.error(e1.getMessage());
			fatal = true;
		}
		OracleCache current = null;
		UtCache utCache = null;
		CacheStatus status = null;
		for (final OracleCache cache : manager.getCachedObjects().values()) {
			if (logger.isDebugEnabled()) {
			logger.debug("examining " + cache.getClass());
			}
			current = cache;
			logger.info("cache class " + cache.getClass().getName());

			if (!manager.isCorrupt(cache)) {
				status = cache.getCacheStatus();
				final String cacheName = cache.getClass().getName();
				utCache = utCacheS.getForClassName(cache.getClass().getName());
				if (utCache == null) {
//					StringBuilder a = new StringBuilder();
//					a.append("could not find '" + cacheName + "' in utCacheS\n");
//					for (UtCache c : utCacheS) {
//						a.append(c)
//					}
					final StringBuilder b = new StringBuilder();
					b.append("cache class: ");
					b.append(cache.getClass().getName());
					b.append(" ");
					b.append("CacheStatus: ");
					b.append(status.getObjectName());
					b.append("cacheStatus not in UtCaches");
					b.append("\n");
					b.append("known keys are ");
					b.append(utCacheS.getKeysAsString());
					logger.error(b.toString());
					logger.error("cache with status.getObjectName " + status.getObjectName() + " not in utCacheS for cache "
							+ cache.toString() + " marking as invalid");
					manager.markInvalid(cache);
					// manager.put(status.getObjectName(),status.getObjectName());
					if (refreshInvalidCaches) {
						logger.warn("refreshing improperly cached table with " + cache.getClass().getName());
						status.refreshStarts();
						try {
							cache.refresh(dbc);
						} catch (final SQLException e) {
							logger.error("Error while processing " + current.getCacheStatus().getObjectName() + "\n"
									+ e.getMessage());
							manager.markCorrupt(current);
						}
						status.refreshEnds();
					}
				} else if (cache.getCacheStatus().getChangeTime() < utCache.getChangeTime().getTime()) {
					if (logger.isDebugEnabled()) {
					logger.debug("refreshing " + status.getObjectName() +
							" cacheChangeTime " + cache.getCacheStatus().getChangeTime() +
							" utCacheChangeTime " + utCache.getChangeTime().getTime() + " " + utCache.getChangeTime());
					}
					try {
						cache.refresh(dbc);
					} catch (final SQLException e) {
						logger.error("Error while processing " + current.getCacheStatus().getObjectName() + "\n" + e.getMessage());
						manager.markCorrupt(current);
					}
				} else {
					if (logger.isDebugEnabled()) {
					logger.debug("not refreshing " + status.getObjectName() + " " +
							cache.getCacheStatus().getChangeTime() + " utcache " + utCache.getChangeTime().getTime() + " " +
							utCache.getChangeTime());
					}
				}
			}
		}
	}
	@Override
	public void run() {
		while (!fatal) {
			try {
				Thread.sleep(60 * 1 * 1000);
			} catch (final java.lang.InterruptedException i) { // ignore
			}
			refresh();
		}
	}

	public void setLogger(final Logger logger) {
		CacheSynchronizer.logger = logger;
	}

	void message(final String text) {
		System.out.println("CacheManager: " + text);
		logger.info(text);
	}
}
