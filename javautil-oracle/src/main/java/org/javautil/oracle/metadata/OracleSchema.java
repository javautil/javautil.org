package org.javautil.oracle.metadata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;
import org.javautil.jdbc.metadata.DatabaseObject;
import org.javautil.jdbc.metadata.DatabaseObjectType;
import org.javautil.jdbc.metadata.NonexistantTableException;
import org.javautil.jdbc.metadata.SchemaImpl;
import org.javautil.jdbc.metadata.containers.DatabaseTables;
import org.javautil.jdbc.metadata.containers.DatabaseTablesImpl;
import org.javautil.jdbc.metadata.containers.ForeignKeys;
import org.javautil.oracle.DdScope;
import org.javautil.oracle.dao.OracleObjectDAO;
import org.javautil.oracle.dao.OracleViewDAO;
import org.javautil.oracle.dto.OracleObject;

public class OracleSchema extends SchemaImpl implements
		org.javautil.jdbc.metadata.Schema {

	// private DatabaseTables tables = new JdbcTables();
	private final Map<String, OracleView> viewsByOwnerDotViewName = new TreeMap<String, OracleView>();
	private Map<String, DatabaseObject> functions = new TreeMap<String, org.javautil.jdbc.metadata.DatabaseObject>();
	private Map<String, DatabaseObject> procedures = new TreeMap<String, DatabaseObject>();
	private Map<String, DatabaseObject> packageSpecifications = new TreeMap<String, DatabaseObject>();
	private Map<String, DatabaseObject> packageBodies = new TreeMap<String, DatabaseObject>();
	private Map<String, DatabaseObject> sequences = new TreeMap<String, DatabaseObject>();
	private Map<String, DatabaseObject> views = new TreeMap<String, DatabaseObject>();
	private final Map<String, DatabaseObject> triggers = new TreeMap<String, DatabaseObject>();
	private DatabaseTables tables = new DatabaseTablesImpl();
	private ForeignKeys foreignKeys;
	private ForeignKeys importedKeys;
	private final String schemaName;
	private DataSource ds;
	private Map<String, OracleObject> objectsByObjectName = new TreeMap<String, OracleObject>();

	//
	private final Map<String, Long> milliTimes = new TreeMap<String, Long>();
	private final Logger logger = Logger.getLogger(getClass());

	// public OracleSchema(DataSource ds, String schemaName) throws
	// SQLException, NonexistantTableException {
	// if (schemaName == null) {
	// throw new IllegalArgumentException("schemaName is null");
	// }
	// if (ds == null) {
	// throw new IllegalArgumentException("ds is null");
	// }
	// this.schemaName = schemaName;
	// Connection conn = ds.getConnection();
	// populate(conn);
	// }

	public OracleSchema(OracleConnection oconn, String schemaName)
			throws SQLException, NonexistantTableException {
		if (schemaName == null) {
			throw new IllegalArgumentException("schemaName is null");
		}
		if (oconn == null) {
			throw new IllegalArgumentException("oconn is null");
		}
		this.schemaName = schemaName;

		populate(oconn);
	}

	public void populate(Connection conn) throws SQLException,
			NonexistantTableException {
		populateObjects(conn);
		populateViews(conn);
		conn.getMetaData();
		for (OracleObject dbo : objectsByObjectName.values()) {
			logger.debug("About to populate DDL for " + dbo);
			if (dbo.getDatabaseObjectType().hasSource()) {
				dbo.populateDDL(conn);
			}
			addObject(dbo);

		}
	}

	private void populateViews(Connection conn) throws SQLException {
		logger.info("populating views");
		List<OracleView> viewList = OracleViewDAO.get(conn, schemaName, "%");
		logger.debug("read " + viewList.size() + " views ");
		for (int i = 0; i < viewList.size(); i++) {
			OracleView v = viewList.get(i);
			String key = v.getOwner() + "." + v.getViewName();
			viewsByOwnerDotViewName.put(key, v);
		}
		logger.info("view count " + viewsByOwnerDotViewName.size());
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
			case TABLE:
				addTable(o);
				break;
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

	private void addTable(OracleObject o) {
		// TODO Auto-generated method stub

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

	// private void addTable(Connection conn, DatabaseObject o) throws
	// SQLException, NonexistantTableException {
	//
	// String tableName = o.getName();
	// Table t = new OracleTable(conn, o);
	// tables.addTable(t);
	//
	// }

	private void addView(DatabaseObject o) {
		views.put(o.getName(), o);
		logger.warn("addView doesn't do anything yet");

	}

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

	public void addTable(DatabaseObject o) {
		OracleTable t = new OracleTable(o);
		super.addTable(t);
	}

	// @Override
	// public void addTable(JDBCTable table) {
	// super.addTable(table);
	//
	// }

	// @Override
	// public Table getTable(String tableName) {
	// return tables.getTable(tableName);
	// }
	//
	// @Override
	// public DatabaseTables getTables() throws SQLException {
	// return tables;
	// }

	// @Override
	// public void setSchemaName(String schemaName) {
	// this.schemaName = schemaName;
	//
	// }

	public Map<String, DatabaseObject> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, DatabaseObject> functions) {
		this.functions = functions;
	}

	public Map<String, DatabaseObject> getProcedures() {
		return procedures;
	}

	public void setProcedures(Map<String, DatabaseObject> procedures) {
		this.procedures = procedures;
	}

	public Map<String, DatabaseObject> getPackageSpecifications() {
		return packageSpecifications;
	}

	public void setPackageSpecifications(
			Map<String, DatabaseObject> packageSpecifications) {
		this.packageSpecifications = packageSpecifications;
	}

	public Map<String, DatabaseObject> getPackageBodies() {
		return packageBodies;
	}

	public Map<String, DatabaseObject> getTriggers() {
		// TODO
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

	// public void setTables(DatabaseTables tables) {
	// this.tables = tables;
	// }

	public boolean canGetFunctions() {
		return true;
	}

	public boolean canGetPackageBodies() {
		return true;
	}

	public boolean canGetPackageSpecifications() {
		return true;
	}

	public boolean canGetProcedures() {
		return true;
	}

	public boolean canGetTriggers() {
		return true;
	}

	public boolean canGetViewSource() {
		return true;
	}

	/**
	 * @return the tables
	 */
	@Override
	public DatabaseTables getTables() {
		return tables;
	}

	/**
	 * @param tables
	 *            the tables to set
	 */
	@Override
	public void setTables(DatabaseTables tables) {
		this.tables = tables;
	}

}
