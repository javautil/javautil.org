/**
* @(#) Operation.java
*/

package com.dbexperts.oracle.trace.record;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dbexperts.misc.EventHelper;
import com.dbexperts.oracle.trace.RegularExpressionHelper;

/**
* ----------------------------------------------------------------------------
* PARSE #<CURSOR>:c=0,e=0,p=0,cr=0,cu=0,mis=0,r=0,dep=0,og=4,tim=0
* EXEC  #<CURSOR>:c=0,e=0,p=0,cr=0,cu=0,mis=0,r=0,dep=0,og=4,tim=0
* FETCH #<CURSOR>:c=0,e=0,p=0,cr=0,cu=0,mis=0,r=0,dep=0,og=4,tim=0
* UNMAP #<CURSOR>:c=0,e=0,p=0,cr=0,cu=0,mis=0,r=0,dep=0,og=4,tim=0
* ----------------------------------------------------------------------------
* - OPERATIONS:
*
* PARSE   Parse a statement.
* EXEC    Execute a pre-parsed statement.
* FETCH   Fetch rows from a cursor.
* UNMAP   If the cursor uses a temporary table, when the cursor is
* closed you see an UNMAP when we free up the temporary table
* locks.(Ie: free the lock, delete the state object, free the
* temp segment)
* In tkprof, UNMAP stats get added to the EXECUTE statistics.
* SORT UNMAP
* As above, but for OS file sorts or TEMP table segments.
*
* c       CPU time (100th's of a second in Oracle7 ,8 and 9).
* e       Elapsed time (100th's of a second Oracle7, 8  Microseconds in Oracle 9 onwards).
* p       Number of physical reads.
* cr      Number of buffers retrieved for CR reads.
* cu      Number of buffers retrieved in current mode.
* mis     Cursor missed in the cache.
* r       Number of rows processed.
* dep     Recursive call depth (0 = user SQL, >0 = recursive).
* og      Optimizer goal: 1=All_Rows, 2=First_Rows, 3=Rule, 4=Choose
* tim     Timestamp (large number in 100ths of a second).  Use this to determine the time between any 2 operations.
*/
public abstract class CursorOperation extends AbstractCursorTraceEvent implements Record
{
	private static Logger logger = Logger.getLogger(CursorOperation.class.getName());
	private static EventHelper events = new EventHelper();
	private static final Integer LOG_CONSTRUCTOR = 1;
	/**
	*
	*/
	protected static final Pattern cursorNumberPattern = Pattern.compile("[^ ]* #(\\d*)");
	/**
	*
	*/
	protected static final Pattern cPattern = Pattern.compile("c=(\\d*)");
	/**
	*
	*/
	protected static final Pattern ePattern = Pattern.compile("e=(\\d*)");

	/**
	*
	*/
	protected static final Pattern pPattern = Pattern.compile("p=(\\d*)");
	/**
	*
	*/
	protected static final Pattern crPattern = Pattern.compile("cr=(\\d*).*");
	/**
	*
	*/
	protected static final Pattern cuPattern = Pattern.compile("cu=(\\d*)");
	/**
	*
	*/
	protected static final Pattern misPattern = Pattern.compile("mis=(\\d*)");
	/**
	*
	*/
	protected static final Pattern depPattern = Pattern.compile("dep=(\\d*)");


     /**
	*
	*/
	protected static final Pattern ogPattern = Pattern.compile("og=(\\d*)");
     /**
	*
	*/
	protected static final Pattern timPattern = Pattern.compile("tim=(\\d*)");


     /*****************************************************************
      * elapsed times are centiseconds in 7,8 and microseconds in 8,10
      */
     /**
	* Total cpu consumption for call.
	* Represented in trace file as c=%d
	* c       CPU time (100th's of a second in Oracle7 ,8 and 9).
	*/
	private int cpu;
     /**
      * Elapsed duration of the call.
      * In trace file as "e".
      */
     /**
	* Elapsed curation of timed event;
	*/
	private long elapsedMicroSeconds;

     /**
	* Number of database blocks obtained by O.S. read calls.
	*
	* In trace file as "p".
	*/
	private int physicalBlocksRead;
     /**
	* Number of blocks read from cache in consistent mode.
	*
	* In trace file as "cr".
	*/
	private int consistentReadBlocks;
     /**
	* Number of blocks retrieved from cache in current mode.
	* In trace file as "cu".
	*/
	private int currentModeBlocks;
     /**
	* Number of misses on the library cache.
	*/
	private int libraryCacheMissCount;
     /**
	* Number of rows returned.  In trace file as "r".
	*/
	private int rowCount;

	/**
	* Recursive depth.
	* In trace file as "dep".
	*/
	private int depth;

	/**
	* Optimizer goal. In trace file as "og".
	* og      Optimizer goal: 1=All_Rows, 2=First_Rows, 3=Rule, 4=Choose
	*/
	private int optimizerGoal;

	private long time;

	private final boolean echoInput = true;
	public CursorOperation(final String traceLine) {

		   setCursorNumber(RegularExpressionHelper.getInt(traceLine,cursorNumberPattern));
		   setCpu(RegularExpressionHelper.getInt(traceLine,cPattern));
	       setElapsedMicroSeconds(RegularExpressionHelper.getLong(traceLine,ePattern));
	       setPhysicalBlocksRead(RegularExpressionHelper.getInt(traceLine,pPattern));
	       setConsistentReadBlocks(RegularExpressionHelper.getInt(traceLine,crPattern));
	       setCurrentModeBlocks(RegularExpressionHelper.getInt(traceLine,cuPattern));
	       setLibraryCacheMissCount(RegularExpressionHelper.getInt(traceLine,misPattern));
	       setTime(RegularExpressionHelper.getLong(traceLine, timPattern));
	       setDepth(RegularExpressionHelper.getInt(traceLine,depPattern));
	       setOptimizerGoal(RegularExpressionHelper.getInt(traceLine,ogPattern));
           if (events.exists(LOG_CONSTRUCTOR)) {
        	   logRecord(traceLine);
           }
	}

	/**
		* @return
		*/
		public int getConsistentReadBlocks( ) {
	        return consistentReadBlocks;
	    }

	/**
	* @return
	*/
	public int getCpu( ) {
        return cpu;
    }

	/**
	* @return
	*/
	public int getCurrentModeBlocks( ) {
        return currentModeBlocks;
    }

     /**
	* @return Returns the e.
	*/
	public long getElapsedMicroSeconds( ) {
	    return elapsedMicroSeconds;
	}
    /**
	* @return Returns the mis.
	*/
	public int getLibraryCacheMissCount( ) {
        return libraryCacheMissCount;
    }


/**
* @return
*/
public int getPhysicalBlocksRead( ) {
    return physicalBlocksRead;
}
    /**
	*
	*/
//	protected void setFields( ) {
//
//	}

	public abstract RecordType getRecordType();

    /**
	* @return Returns the r.
	*/
	public int getRowCount( ) {
        return rowCount;
    }
    public long getTime() {
    	return time;
    }
    @Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		append(b,"cursorNumber",getCursorNumber());
		append(b,"cpu",cpu);
		append(b,"elapsed",elapsedMicroSeconds);
		append(b,"pr",physicalBlocksRead);
		append(b,"pw",consistentReadBlocks);
		append(b,"cu",currentModeBlocks);
		append(b,"mis",libraryCacheMissCount);
		append(b,"time",time);
		append(b,"dep",depth);
		append(b,"og", optimizerGoal);
		return b.toString();
	}
    private void append(final StringBuilder b, final String label, final long stat) {
		b.append(label);
		b.append(": ");
		b.append(stat);
		b.append(" ");
	}
    private void logRecord(final String traceLine) {
		final StringBuilder b = new StringBuilder();
		if (echoInput ) {
			b.append("input: " + traceLine + "\n");
		}
		b.append(toString());
		logger.info(b.toString());
	}
    private void setTime(final long time) {
    	if (time == 0) {
    		logger.info("time not set");
    	}
    	this.time = time;
    }
    /**
	* @return Returns the dep.
	*/
	protected int getDepth( ) {
        return depth;
    }
    /**
	* @return Returns the og.
	*/
	protected int getOptimizerGoal( ) {
        return optimizerGoal;
    }

    /**
	* @param cr The cr to set.
	*/
	protected void setConsistentReadBlocks( final int cr ) {
        this.consistentReadBlocks = cr;
    }
    /**
     * @param c The c to set.
     */
    protected void setCpu(final int c) {
        this.cpu = c;
    }

    /**
	* @param cu The cu to set.
	*/
	protected void setCurrentModeBlocks( final int cu ) {
        this.currentModeBlocks = cu;
    }
    /**
	* @param dep The dep to set.
	*/
	protected void setDepth( final int dep ) {
        this.depth = dep;
    }
    /**
	* @param ela The ela to set.
	*/
	protected void setElapsedMicroSeconds( final long ela ) {
        this.elapsedMicroSeconds = ela;
    }
    /**
	* @param mis The mis to set.
	*/
	protected void setLibraryCacheMissCount( final int mis ) {
        this.libraryCacheMissCount = mis;
    }

    /**
	* @param og The og to set.
	*/
	protected void setOptimizerGoal( final int og ) {
        this.optimizerGoal = og;
    }


    /**
	* @param p The p to set.
	*/
	protected void setPhysicalBlocksRead( final int p ) {
        this.physicalBlocksRead = p;
    }
	/**
	* @param r The r to set.
	*/
	protected void setRowCount( final int r ) {
        this.rowCount = r;
    }
}
