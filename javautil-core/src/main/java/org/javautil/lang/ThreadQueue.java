package org.javautil.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author cvw todo review this and figure out what the point is
 * @deprecated if clinton wrote it, it must be shit.
 */
@Deprecated
public abstract class ThreadQueue {
	private static final String THREAD_GROUP_NAME = "ThreadQueue";

	/**
	 * Start a thread and assign it the default name ThreadQueue.
	 * 
	 * @param runnable
	 * @return
	 */
	public static Thread start(final Runnable runnable) {
		return start(runnable, THREAD_GROUP_NAME);
	}

	/**
	 * Start a thread and assign it with the given name.
	 * 
	 * @param runnable
	 * @param name
	 * @return
	 */
	public static Thread start(final Runnable runnable, final String name) {
		final Thread t = new Thread(runnable, name);
		t.start();
		return t;
	}

	/**
	 * Process a collection of threads and assign them with the default name
	 * ThreadQueue.
	 * 
	 * @param threads
	 */
	public static void processThreads(final List<Runnable> threads) {
		processThreads(threads, THREAD_GROUP_NAME);
	}

	/**
	 * Process a collection of threads, assign them the given name, and run no
	 * more than the number of CPU's on the system.
	 * 
	 * @param threads
	 * @param threadName
	 */
	public static void processThreads(final List<Runnable> threads,
			final String threadName) {
		processThreads(threads, threadName,
				Math.max(1, Runtime.getRuntime().availableProcessors() - 1));
	}

	/**
	 * Process a collection of threads, assign them the given name, and run
	 * maxExecuting number of threads simultaneously.
	 * 
	 * @param threads
	 * @param threadName
	 * @param maxExecuting
	 */
	public static void processThreads(final List<Runnable> threads,
			String threadName, int maxExecuting) {
		if (threadName == null) {
			threadName = THREAD_GROUP_NAME;
		}
		maxExecuting = Math.max(maxExecuting, 1);
		final List<Thread> executingThreads = Collections
				.synchronizedList(new ArrayList<Thread>());
		while (threads.size() > 0 || executingThreads.size() > 0) {
			// clean out finished threads
			for (final Iterator<Thread> iter = executingThreads.iterator(); iter
					.hasNext();) {
				final Thread t = iter.next();
				if (!t.isAlive()) {
					iter.remove();
				}
			}

			// add threads to be executed
			while (executingThreads.size() < maxExecuting && threads.size() > 0) {
				final Runnable r = threads.remove(0);
				final Thread started = start(r, threadName);
				executingThreads.add(started);
			}
			sleep(1);
		}
	}

	/**
	 * Sleep for given amount of ms, if interrupted will throw an
	 * IllegalStateException rather than an InterruptedException
	 * 
	 * @param i
	 */
	public static void sleep(final int i) {
		try {
			Thread.sleep(i);
		} catch (final InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
