package com.dbexperts.oracle.DatabaseMetaData;

import java.util.ArrayList;
import java.util.List;

import com.dbexperts.jdbc.DatabaseMetaData.Table;

public class OracleTableConstraints {
    private Table table;
    private List<OracleConstraint> tableConstraints = new ArrayList<OracleConstraint>();
//    private Map<String,OracleConstraint> checkConstraintsByConstraintName = new TreeMap<String,OracleConstraint>();
//    private Map<String,OracleConstraint> constraintsByConstraintName = new TreeMap<String,OracleConstraint>();
//    private Map<Integer,OracleConstraint> checkConstraintsByColumnId = new TreeMap<Integer,OracleConstraint>();
//    private Map<String,OracleConstraint> checkConstraintsByColumnName = new TreeMap<String, OracleConstraint>();
   // private Map<String,OracleConstraint> importedForeignKeysByConstraintName = new TreeMap<String, OracleConstraint>();
    
    public Table getTable() {
        return table;
    }
    
    public void addConstraint(OracleConstraint constraint) {
	// todo should check that this constraint is for this table
	tableConstraints.add(constraint);
    }
    
//    public Map<String, OracleConstraint> getCheckConstraintsByConstraintName() {
//        return checkConstraintsByConstraintName;
//    }
//    public Map<String, OracleConstraint> getConstraintsByConstraintName() {
//        return constraintsByConstraintName;
//    }
//    public Map<Integer, OracleConstraint> getCheckConstraintsByColumnId() {
//        return checkConstraintsByColumnId;
//    }
//    public Map<String, OracleConstraint> getCheckConstraintsByColumnName() {
//        return checkConstraintsByColumnName;
//    }
   
    public List<OracleConstraint> getImportedForeignKeysByConstraintName() {
	    List<OracleConstraint> imports = new ArrayList<OracleConstraint>();
	    // todo should validate the constraints to ensure that they are unique by name within owner but
	    // if we are getting this from the database that is unnecessary, work on this latter when populating
	    // from some other source.
	    for (OracleConstraint constraint : tableConstraints) {
		if (constraint.getOracleConstraintType().equals(OracleConstraintType.REFERENTIAL)) {
		    imports.add(constraint);
		}
	    }
	    return imports;
    }
}
