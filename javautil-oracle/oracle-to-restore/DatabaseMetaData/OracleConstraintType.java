package com.dbexperts.oracle.DatabaseMetaData;
// see http://vsbabu.org/oracle/sect16.html


/**
 *  From dba_views 
 *      decode(c.type#, 1, 'C', 2, 'P', 3, 'U',
              4, 'R', 5, 'V', 6, 'O', 7,'C', '?'),
 and c.type# != 8        --don't include hash expressions 
  and c.type# != 12       -- don't include log groups 

 */

public enum OracleConstraintType {
    CHECK, UNIQUE, PK, UNDEFINED, READ_ONLY_VIEW, REFERENTIAL, VIEW_WITH_CHECK_OPTION;
  //  Logger logger = Logger.getLogger();
    public static OracleConstraintType getOracleConstraintType(String in) {
	OracleConstraintType type = null;
	if (in != null) {
	    if (in.length() != 1) {
		throw new IllegalArgumentException("invalid constraint type " + in);
	    }
	    char t = in.charAt(0);
	    switch (t) {
	    case 'V':
		type = VIEW_WITH_CHECK_OPTION;
		break;
	    case 'R':
		type = REFERENTIAL;
		break;
	    case 'U':
		type= UNIQUE;
		break;
	    case 'P':
		type = PK;
		break;
	    case '?':
		type = UNDEFINED; // what does this mean
		    break; 
	    case 'C':
		type = CHECK;
		break;
	    case 'O':
		type = READ_ONLY_VIEW;
		break;
	    default:
		throw new IllegalArgumentException("Unknown constraint type " + in);
	    }
	
	}
	   return type;
    }
}
