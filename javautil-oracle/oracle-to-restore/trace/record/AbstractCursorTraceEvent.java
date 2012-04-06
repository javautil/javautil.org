package com.dbexperts.oracle.trace.record;

public abstract class AbstractCursorTraceEvent extends AbstractRecord implements CursorEvent {



	private int cursorNumber;

	public final int getCursorNumber() {
		return cursorNumber;
	}

	public final void setCursorNumber(final int cursorNumber) {
		this.cursorNumber = cursorNumber;
	}

}
