package com.dbexperts.oracle.DatabaseMetaData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;

import com.dbexperts.jdbc.DatabaseMetaData.DDL;
import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObject;
import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObjectType;
import com.dbexperts.jdbc.DatabaseMetaData.DatabaseTables;
import com.dbexperts.jdbc.DatabaseMetaData.ForeignKeys;
import com.dbexperts.jdbc.DatabaseMetaData.JDBCTable;
import com.dbexperts.jdbc.DatabaseMetaData.JdbcSchema;
import com.dbexperts.jdbc.DatabaseMetaData.JdbcTables;
import com.dbexperts.jdbc.DatabaseMetaData.NonexistantTableException;
import com.dbexperts.jdbc.DatabaseMetaData.Schema;
import com.dbexperts.jdbc.DatabaseMetaData.Table;
import com.dbexperts.jdbc.DatabaseMetaData.TableToSQL;
import com.dbexperts.oracle.DdScope;
import com.dbexperts.oracle.dao.OracleObjectDAO;
import com.dbexperts.oracle.dao.OracleViews;
import com.dbexperts.oracle.dto.OracleObject;

public class OracleSchema extends JdbcSchema implements Schema {

    private Map<String, String> events;
    
  //  private DatabaseTables tables = new JdbcTables();
    private Map<String, OracleView> viewsByOwnerDotViewName = new TreeMap<String,OracleView>();
    private Map<String, DatabaseObject> functions = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> procedures = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> packageSpecifications = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> packageBodies = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> sequences = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> views = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> triggers = new TreeMap<String, DatabaseObject>();
    private Map<String, DatabaseObject> tables = new TreeMap<String, DatabaseObject>();
    private ForeignKeys foreignKeys;
    private ForeignKeys importedKeys;
    private String schemaName;
    private DataSource ds;
    private Map<String, OracleObject> objectsByObjectName = new TreeMap<String, OracleObject>();
    
    //
    private Map<String, Long> milliTimes = new TreeMap<String, Long>();
    private Logger logger = Logger.getLogger(getClass());

//    public OracleSchema(DataSource ds, String schemaName) throws SQLException, NonexistantTableException {
//	if (schemaName == null) {
//	    throw new IllegalArgumentException("schemaName is null");
//	}
//	if (ds == null) {
//	    throw new IllegalArgumentException("ds is null");
//	}
//	this.schemaName = schemaName;
//	Connection conn = ds.getConnection();
//	populate(conn);
//    }

    public OracleSchema(OracleConnection oconn, String schemaName) throws SQLException, NonexistantTableException {
	if (schemaName == null) {
	    throw new IllegalArgumentException("schemaName is null");
	}
	if (oconn == null) {
	    throw new IllegalArgumentException("oconn is null");
	}
	this.schemaName = schemaName;

	populate(oconn);
    }

    public void populate(Connection conn) throws SQLException, NonexistantTableException {
	populateObjects(conn);
	    populateViews(conn);
	    DatabaseMetaData meta = conn.getMetaData();
	for (OracleObject dbo : objectsByObjectName.values()) {
	    logger.debug("About to populate DDL for " + dbo);
	    if (dbo.getDatabaseObjectType().hasSource()) {
		dbo.populateDDL(conn);
	    }
	    if (dbo.getDatabaseObjectType().equals(DatabaseObjectType.TABLE)) {
		JDBCTable table = new JDBCTable(conn, meta, dbo.getOwner(), dbo.getName(), "",
			"TABLE");
		//JDBCTable table = new JDBCTable(conn);
		//table.populate(conn);
		TableToSQL tts = new TableToSQL();
//		String text = tts.toSQLString(table);
//		DDL ddl = new DDL(dbo);
//		ddl.setSQL(text);
//		dbo.setDDL(ddl);
		//dbo.setDDL(table.getDDL());
		if (logger.isDebugEnabled()) {
		    String text = tts.toSQLString(table);
		    logger.debug(text);
		}
		addTable(table); 
	    }
	    addObject(dbo);
	  
	}
    }

    private void populateViews(Connection conn) throws SQLException {
	
	List<OracleView> viewList = OracleViews.get(conn,schemaName,"%");
	logger.debug("read " + viewList.size() + " views ");
	for (int i = 0; i < viewList.size(); i++) {
	    OracleView v = viewList.get(i);
	    String key = v.getOwner() + "." + v.getViewName();
	    viewsByOwnerDotViewName.put(key, v);
	}
    }
    
    public void addObject(OracleObject o) {
	DatabaseObjectType dot = o.getDatabaseObjectType();
	if (dot != null) {
	    switch (dot) {
	    case FUNCTION:
		addFunction(o);
		break;
	    case GRANT:
		addGrant(o);
		break;
	    case PACKAGE_SPECIFICATION:
		addPackageSpecification(o);
		break;
	    case PACKAGE_BODY:
		addPackageBody(o);
		break;
	    case SEQUENCE:
		addSequence(o);
		break;
	    case PROCEDURE:
		addProcedure(o);
		break;
//	    case TABLE:
//	     addTable(o);
//	     break;
	    case VIEW:
		addView(o);
		break;
	    default:
		logger.warn("unsupported object type "
			+ o.getDatabaseObjectType());

	    }
	} else {
	    logger.warn("unknown object type " + o.getObjectType());
	}
    }

    public Map<String, OracleObject> populateObjects(Connection conn)
	    throws SQLException {
	logger.debug("populating objects for schema " + schemaName);
	long beginMillis = System.currentTimeMillis();
	OracleObjectDAO objectPersister = new OracleObjectDAO();
	List<OracleObject> objects = objectPersister.getForLikeOwner(conn,
		DdScope.ALL, schemaName);
	for (OracleObject object : objects) {
	    objectsByObjectName.put(object.getObjectName(), object);
	}
	long endMillis = System.currentTimeMillis();
	long elapsedMillis = endMillis - beginMillis;
	milliTimes.put("populateObjects", Long.valueOf(elapsedMillis));
	logger.debug("objects populated in " + elapsedMillis + " millis ");
	return objectsByObjectName;
    }

//     private void addTable(Connection conn, DatabaseObject o) throws SQLException, NonexistantTableException {
//     
//      String tableName = o.getName();
//      Table t = new OracleTable(conn, o);
//      tables.addTable(t);
// 
//     }

    private void addView(DatabaseObject o) {
	views.put(o.getName(),o);
	logger.warn("addView doesn't do anything yet");

    }

    @Override
    public String getViewSource(String viewName) {
	String key = schemaName + "." + viewName;
	OracleView v = viewsByOwnerDotViewName.get(key);
	String text = v.getText() == null ? "" : v.getText();
	String oidText = v.getOidText() == null ? "" : v.getOidText();
	String typeText = v.getTypeText() == null ? "" : v.getTypeText();
	return text + oidText + typeText;
    }
    
    private void addProcedure(OracleObject o) {
	String name = o.getName();
	procedures.put(name, o);

    }

    private void addSequence(DatabaseObject o) {
	String name = o.getName();
	sequences.put(name, o);

    }

    private void addPackageBody(DatabaseObject o) {
	String name = o.getName();
	packageBodies.put(name, o);

    }

    private void addFunction(DatabaseObject o) {
	// TODO Auto-generated method stub

    }

    private void addGrant(DatabaseObject o) {
	// TODO Auto-generated method stub

    }

    private void addPackageSpecification(DatabaseObject o) {
	// TODO Auto-generated method stub

    }
    
//    public void addTable(DatabaseObject o) {
//	Table t = new Table(o);
//	super.addTable(t);
//    }

//    @Override
//    public void addTable(JDBCTable table) {
//	super.addTable(table);
//
//    }

//    @Override
//    public Table getTable(String tableName) {
//	return tables.getTable(tableName);
//    }
//
//    @Override
//    public DatabaseTables getTables() throws SQLException {
//	return tables;
//    }

//    @Override
//    public void setSchemaName(String schemaName) {
//	this.schemaName = schemaName;
//
//    }

    @Override
    public Map<String, DatabaseObject> getFunctions() {
	return functions;
    }

    public void setFunctions(Map<String, DatabaseObject> functions) {
	this.functions = functions;
    }

    @Override
    public Map<String, DatabaseObject> getProcedures() {
	return procedures;
    }

    public void setProcedures(Map<String, DatabaseObject> procedures) {
	this.procedures = procedures;
    }

    @Override
    public Map<String, DatabaseObject> getPackageSpecifications() {
	return packageSpecifications;
    }

    public void setPackageSpecifications(
	    Map<String, DatabaseObject> packageSpecifications) {
	this.packageSpecifications = packageSpecifications;
    }

    @Override
    public Map<String, DatabaseObject> getPackageBodies() {
	return packageBodies;
    }
    @Override
    public Map<String, DatabaseObject> getTriggers() {
	logger.warn("not yet implemented");
	return triggers;
    }

    public void setPackageBodies(Map<String, DatabaseObject> packageBodies) {
	this.packageBodies = packageBodies;
    }

    public Map<String, DatabaseObject> getSequences() {
	return sequences;
    }

    public void setSequences(Map<String, DatabaseObject> sequences) {
	this.sequences = sequences;
    }

    public Map<String, DatabaseObject> getViews() {
	return views;
    }

    public void setViews(Map<String, DatabaseObject> views) {
	this.views = views;
    }

    public ForeignKeys getForeignKeys() {
	return foreignKeys;
    }

    public void setForeignKeys(ForeignKeys foreignKeys) {
	this.foreignKeys = foreignKeys;
    }

    public ForeignKeys getImportedKeys() {
	return importedKeys;
    }

    public void setImportedKeys(ForeignKeys importedKeys) {
	this.importedKeys = importedKeys;
    }

    public DataSource getDs() {
	return ds;
    }

    public void setDs(DataSource ds) {
	this.ds = ds;
    }

    public Map<String, OracleObject> getObjectsByObjectName() {
	return objectsByObjectName;
    }

    public void setObjectsByObjectName(
	    Map<String, OracleObject> objectsByObjectName) {
	this.objectsByObjectName = objectsByObjectName;
    }

    public String getSchemaName() {
	return schemaName;
    }

//    public void setTables(DatabaseTables tables) {
//	this.tables = tables;
//    }

	@Override
	public boolean canGetFunctions() {
	
	    return true;
	}

	@Override
	public boolean canGetPackageBodies() {
	
	    return true;
	}

	@Override
	public boolean canGetPackageSpecifications() {

	    return true;
	}

	@Override
	public boolean canGetProcedures() {

	    return true;
	}

	@Override
	public boolean canGetTriggers() {

	    return true;
	}
	   @Override
	    public boolean canGetViewSource() {
	
		return true;
	    }

	  

}