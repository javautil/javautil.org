package com.dbexperts.oracle.trace;

import java.util.ArrayList;

public class CursorGroup {
	ArrayList<Cursor> cursorsInGroup = new ArrayList<Cursor>();

	public CursorGroup() {

	}

	public void addCursor(final Cursor c) {
		cursorsInGroup.add(c);
	}

	public int getFirstReference() {
		int min = Integer.MAX_VALUE;
		for (final Cursor c : cursorsInGroup) {
			if (c.getCursorId() < min ) {
				min = c.getCursorId();
			}

		}
		return min;
	}

	public long getMinParseTime() {
		long min = Long.MAX_VALUE;
		for (final Cursor c : cursorsInGroup) {
			final long parseTime = c.getParsing().getTimestamp();
			if (parseTime < min ) {
				min = parseTime;
			}

		}
		return min;
	}
}
