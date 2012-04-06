package com.dbexperts.oracle.cache;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import com.dbexperts.oracle.WrappedOracleConnection;



/**
Version:   $Id: CacheManager.java,v 1.1 2011/08/31 21:09:57 jjs Exp $
*/

//public class CacheManager extends Thread {
public class CacheManager {
	public static final String revision = "$Revision: 1.1 $";
	public static final String CACHE_MANAGER_LOG_FILE_MASK = "CACHE_MANAGER_LOG_FILE_MASK";
	private static boolean initted = false;
    private static HashMap<String,OracleCache> byClassName = new HashMap<String,OracleCache>();
	/**
     * Objects for which SQL is invalid.
     *
     * Referenced by class.getName().
     */
	private static HashMap<OracleCache,OracleCache> corruptObjects = new HashMap<OracleCache,OracleCache>();
	/**
	 * Improperly cached objects, not in ut_cache.
	 */
	private static HashMap<OracleCache,OracleCache> invalidCaches = new HashMap<OracleCache,OracleCache>();
	private static TreeMap<String,OracleCache> cachedObjects = new TreeMap<String,OracleCache>();
	private static UtCacheS utCacheS = new UtCacheS();
    private static Logger logger = Logger.getLogger(CacheManager.class.getName());
	private static CacheSynchronizer synchronizer = null;
	private WrappedOracleConnection conn;
	public CacheManager()
	throws java.lang.IllegalStateException
	{
		if (!initted) {
			throw new java.lang.IllegalStateException("CacheManager has not been initialized");
		}
	}

	public CacheManager(final OracleConnection conn)
	throws java.sql.SQLException,  ClassNotFoundException, InstantiationException, IllegalAccessException
	{

		this.conn = WrappedOracleConnection.getInstance(conn);
		loadCachedObjects();

	}

	public OracleCache get(final String cacheName) {
		OracleCache returnValue = null;
		synchronized (byClassName) {
		returnValue =  byClassName.get(cacheName);
		}
		if (returnValue == null) {
			throw new IllegalStateException("no object cached " + cacheName + " in\n " + toString());
		}
		return returnValue;
	}


	/**
	 *
	 * @return  the cached objects.
	 */
	public Map<String,OracleCache> getCachedObjects() {
		return cachedObjects;
	}

	public OracleCache [] getObjects() {
		OracleCache rc [];
		synchronized (cachedObjects) {
			rc = new OracleCache[cachedObjects.size()];
			final Iterator<OracleCache> it = cachedObjects.values().iterator();
			for (int ndx = 0; it.hasNext(); ndx++ ) {
				rc[ndx] = it.next();
			}
		}
		return rc;
	}

    public void setLogFileMask(final String cacheLogMask) {
    	final FileAppender fa = new FileAppender();
    	fa.setFile(cacheLogMask);
//		logFileMask = cacheLogMask;
//		FileHandler fh = LoggerHelper.getFileHandler(logFileMask);
//		logger.setUseParentHandlers(false);
//		logger.addHandler(fh);

	}



	public void startSynchronizer(final WrappedOracleConnection dbc)
	throws java.sql.SQLException
	//, java.io.IOException
	{
		dbc.setModule("CacheManager");
		logger = Logger.getLogger(this.getClass().getName());
		message("CacheManager Initialization Begins");

		synchronizer = new CacheSynchronizer(this, dbc);
		synchronizer.refresh();
		synchronizer.start();

		initted = true;
		message("CacheManager Initialization Complete");
	}
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		for (final String className : byClassName.keySet()) {
			b.append(className);
			b.append("\n");
		}
		return b.toString();
	}

	/**
	 * @todo need to defer a request to get from cache if not done loading yet.
	 * @param object
	 * @throws SQLException
	 */
	private void addCachedObject(final OracleCache object) throws SQLException {
		final String className = object.getClass().getName();

        final long start = System.nanoTime();
		logger.info("loading " + className);
		object.load(conn);
		final long end = System.nanoTime();
		final double elapsed = (end - start) / 1e9;
		logger.info("loaded " + className + " in " + elapsed);
		final CacheStatus cacheStatus = object.getCacheStatus();
		final String objectName = cacheStatus.getObjectName();
		byClassName.put(className,object);
		cachedObjects.put(objectName,object);

	}

	@SuppressWarnings("unchecked")
	private void loadCachedObjects() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		utCacheS.refresh(conn);
		for (final UtCache uc : utCacheS) {
			if (uc.getClassName() == null) {
				logger.warn("cache entry has null className");
				continue;
			}
			final String className = uc.getClassName().trim();
			logger.info("instantiating '" + className + "'");

			final Class c = Class.forName(className);
			Object o;
			try {
				o = c.newInstance();
			} catch (final InstantiationException e) {
				throw new InstantiationException("while instantiating " + className + e.getMessage());

			} catch (final IllegalAccessException iae) {
				throw new IllegalAccessException("while instantiating " + className + iae.getMessage());
			}
			OracleCache cached = null;

			try  {

			   cached = (OracleCache) c.newInstance();
				addCachedObject(cached);
			} catch (final ClassCastException cce) {
			   final String message = o.getClass().getName() + " cannot be cast to " + OracleCache.class.getName();
			   logger.error(message);
			   throw new ClassCastException(message);
			}


		}
		startSynchronizer( conn);
	//	synchronizer.setLogger(logger);
		initted = true;
//		if (!initted) {
//			init(conn);
//		}
	}

	private void message(final String text) {
		//System.out.println("CacheManager: " + text);
	//	logger.info(text);
	}


	UtCacheS getUtCacheS() {
		return utCacheS;
	}

	boolean isCorrupt(final OracleCache suspect) {
		final String suspectName = suspect.getClass().getName();
		if (corruptObjects.get(suspectName) != null) {
			return true;
		}
		return false;
	}




	void markCorrupt(final OracleCache cache) {

	}

	void markInvalid(final OracleCache cache) {
    	if (invalidCaches.get(cache) == null) {
    		final CacheStatus status = cache.getCacheStatus();
    	    invalidCaches.put(cache,cache);
    	    logger.error("table not properly cached add to ut_cache " + status.getObjectName() + " " + cache.getClass().getName());

    	}
    }
//	public CacheManager (OracleConnection conn)
//	throws java.sql.SQLException, java.io.IOException
//	{
//		//DbConnector dbc = new DbConnector(properties);
//		init(conn);
//	}

}
