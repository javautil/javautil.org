package org.javautil.jdbc.metadata;

/**
 * 
 * @author jjs NO_ACTION importedKeyCascade 3 "NO ACTION"
 */
public enum DeleteRule {

	NO_ACTION,
	// * importedKeyNoAction - do not allow delete of primary key if it has been
	// imported
	CASCADE, SET_NULL, SET_DEFAULT;

	public static final String LIT_NO_ACTION = "NO ACTION";
	public static final String LIT_CASCADE = "CASCADE";
	public static final String LIT_SET_NULL = "SET NULL";
	public static final String LIT_SET_DEFAULT = "SET DEFAULT";
	public static final int importedKeyCascade = 0;
	public static final int importedKeyInitiallyDeferred = 5;
	public static final int importedKeyInitiallyImmediate = 6;
	public static final int importedKeyNoAction = 3;
	public static final int importedKeyNotDeferrable = 7;
	public static final int importedKeyRestrict = 1;
	public static final int importedKeySetDefault = 4;
	public static final int importedKeySetNull = 2;

	public String getOracleAction() {
		String returnValue = null;
		switch (this) {
		case NO_ACTION:
			returnValue = LIT_NO_ACTION;
			break;
		case CASCADE:
			returnValue = LIT_CASCADE;
			break;
		case SET_NULL:
			returnValue = LIT_SET_NULL;
		}
		return returnValue;
	}

	public static DeleteRule getDeleteRule(final String val) {
		DeleteRule returnValue = null;
		if (LIT_NO_ACTION.equals(val)) {
			returnValue = NO_ACTION;
		} else if (LIT_CASCADE.equals(val)) {
			returnValue = CASCADE;
		} else if (SET_NULL.equals(val)) {
			returnValue = SET_NULL;
		} else if (SET_DEFAULT.equals(val)) {
			returnValue = SET_DEFAULT;
		}
		return returnValue;
	}

	public static DeleteRule getDeleteRule(final int val) {
		DeleteRule retval = null;
		switch (val) {
		case importedKeyNoAction:
			retval = NO_ACTION;
			break;
		case importedKeyCascade:
			retval = CASCADE;
			break;
		case importedKeySetNull:
			retval = SET_NULL;
			break;
		// todo complete
		}
		return retval;
	}
	// * importedKeyCascade - delete rows that import a deleted key
	// * importedKeySetNull - change imported key to NULL if its primary key has
	// been deleted
	// * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x
	// compatibility)
	// * importedKeySetDefault - change imported key to default if its primary
	// key has been deleted
}
