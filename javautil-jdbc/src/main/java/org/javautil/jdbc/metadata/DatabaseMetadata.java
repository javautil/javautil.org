package org.javautil.jdbc.metadata;

public class DatabaseMetadata {
	/*
	 * Retrieves whether the current user can call all the procedures returned
	 * by the method getProcedures.
	 */
	private boolean allProceduresAreCallable;
	/*
	 * Retrieves whether the current user can use all the tables returned by the
	 * method getTables in a SELECT statement.
	 */
	private boolean allTablesAreSelectable;
	/*
	 * Retrieves whether a data definition statement within a transaction forces
	 * the transaction to commit.
	 */
	private boolean dataDefinitionCausesTransactionCommit;
	/*
	 * Retrieves whether this database ignores a data definition statement
	 * within a transaction.
	 */
	private boolean dataDefinitionIgnoredInTransactions;
	// /* Retrieves whether or not a visible row delete can be detected by
	// calling the method ResultSet.rowDeleted. */
	// private boolean deletesAreDetected(int type)
	/*
	 * Retrieves whether the return value for the method getmaxRowSize includes
	 * the SQL data types LONGVARCHAR and LONGVARBINARY.
	 */
	private boolean doesmaxRowSizeIncludeBlobs;

	/* catalog names available in this database. */
	// ResultSet getCatalogs;
	/*
	 * String that this database uses as the separator between a catalog and
	 * table name.
	 */
	private String catalogSeparator;
	/* database vendor's preferred term for "catalog". */
	private String catalogTerm;
	/* major version number of the underlying database. */
	private int databaseMajorVersion;
	/* minor version number of the underlying database. */
	private int databaseMinorVersion;
	/* name of this database product. */
	private String databaseProductName;
	/* version number of this database product. */
	private String databaseProductVersion;
	/* Retrieves this database's default transactionolation level. */
	private int defaultTransactionIsolation;

	/* Retrieves this JDBC driver's major version number. */
	private int driverMajorVersion;
	/* Retrieves this JDBC driver's minor version number. */
	private int driverMinorVersion;
	/* name of this JDBC driver. */
	private String driverName;
	/* version number of this JDBC driver as a String. */
	private String driverVersion;
	/*
	 * ll the "extra" characters that can be used in unquoted identifier names
	 * (those beyond a-z, A-Z, 0-9 and _).
	 */
	private String extraNameCharacters;
	/* string used to quote SQL identifiers. */
	private String identifierQuoteString;

	/* major JDBC version number for this driver. */
	private int jdbcMajorVersion;
	/* minor JDBC version number for this driver. */
	private int jdbcMinorVersion;
	/*
	 * maximum number of hex characters this database allows in an inline binary
	 * literal.
	 */
	private int maxBinaryLiteralLength;
	/* maximum number of characters that this database allows in a catalog name. */
	private int maxCatalogNameLength;
	/*
	 * maximum number of characters this database allows for a character
	 * literal.
	 */
	private int maxCharLiteralLength;
	/* maximum number of characters this database allows for a column name. */
	private int maxColumnNameLength;
	/* maximum number of columns this database allows in a GROUP BY clause. */
	private int maxColumnsInGroupBy;
	/* maximum number of columns this database allows in an index. */
	private int maxColumnsInIndex;
	/* maximum number of columns this database allows in an ORDER BY clause. */
	private int maxColumnsInOrderBy;
	/* maximum number of columns this database allows in a SELECT list. */
	private int maxColumnsInSelect;
	/* maximum number of columns this database allows in a table. */
	private int maxColumnsInTable;
	/*
	 * maximum number of concurrent connections to this database that are
	 * possible.
	 */
	private int maxConnections;
	/* maximum number of characters that this database allows in a cursor name. */
	private int maxCursorNameLength;
	/*
	 * maximum number of bytes this database allows for an index, including all
	 * of the parts of the index.
	 */
	private int maxIndexLength;
	/*
	 * maximum number of characters that this database allows in a procedure
	 * name.
	 */
	private int maxProcedureNameLength;
	/* maximum number of bytes this database allows in a single row. */
	private int maxRowSize;
	/* maximum number of characters that this database allows in a schema name. */
	private int maxSchemaNameLength;
	/* maximum number of characters this database allows in an SQL statement. */
	private int maxStatementLength;
	/*
	 * maximum number of active statements to this database that can be open at
	 * the same time.
	 */
	private int maxStatements;
	/* maximum number of characters this database allows in a table name. */
	private int maxTableNameLength;
	/* maximum number of tables this database allows in a SELECT statement. */
	private int maxTablesInSelect;
	/* maximum number of characters this database allows in a user name. */
	private int maxUserNameLength;
	/* comma-separated list of math functions available with this database. */
	private String numericFunctions;
	/*
	 * description of the given catalog's stored procedure parameter and result
	 * columns.
	 */
	/* database vendor's preferred term for "procedure". */
	private String procedureTerm;
	/* default holdability of this ResultSet object. */
	private int resultSetHoldability;
	// TODO /* schema names available in this database. */
	/* database vendor's preferred term for "schema". */
	private String schemaTerm;
	/* string that can be used to escape wildcard characters. */
	private String searchStringEscape;
	/*
	 * comma-separated list of all of this database's SQL keywords that are NOT
	 * also SQL92 keywords.
	 */
	private String sqlKeywords;
	// Indicates whether the SQLSTATE returned by SQLException.SQLState X/Open
	// (now known as Open Group) SQL CLI or SQL99.
	// int SQLStateType;
	/* comma-separated list of string functions available with this database. */
	private String stringFunctions;
	/* comma-separated list of system functions available with this database. */
	private String systemFunctions;
	/*
	 * comma-separated list of the time and date functions available with this
	 * database.
	 */
	private String timeDateFunctions;
	/* URL for this DBMS. */
	private String URL;

	/* user name as known to this database. */
	private String UserName;
	// /* Retrieves whether or not a visible row insert can be detected by
	// calling the method ResultSet.rowInserted. */
	// private boolean insertsAreDetected(int type)
	/*
	 * Retrieves whether a catalog appears at the start of a fully qualified
	 * table name.
	 */
	private boolean isCatalogAtStart;
	/* Retrieves whether this database in read-only mode. */
	private boolean isReadOnly;
	/*
	 * Indicates whether updates made to a LOB are made on a copy or directly to
	 * the LOB. private boolean locatorsUpdateCopy; /* Retrieves whether this
	 * database supports concatenations between NULL and non-NULL values being
	 * NULL.
	 */
	private boolean nullPlusNonNullIsNull;
	/*
	 * Retrieves whether NULL values are sorted at the end regardless of sort
	 * order.
	 */
	private boolean nullsAreSortedAtEnd;
	/*
	 * Retrieves whether NULL values are sorted at the start regardless of sort
	 * order.
	 */
	private boolean nullsAreSortedAtStart;
	/* Retrieves whether NULL values are sorted high. */
	private boolean nullsAreSortedHigh;
	/* Retrieves whether NULL values are sorted low. */
	private boolean nullsAreSortedLow;
	/* Retrieves whether deletes made by others are visible. */
	// private boolean othersDeletesAreVisible(int type)
	// /* Retrieves whether inserts made by others are visible. */
	// private boolean othersInsertsAreVisible(int type)
	// /* Retrieves whether updates made by others are visible. */
	// private boolean othersUpdatesAreVisible(int type)
	// /* Retrieves whether a result set's own deletes are visible. */
	// private boolean ownDeletesAreVisible(int type)
	// /* Retrieves whether a result set's own inserts are visible. */
	// private boolean ownInsertsAreVisible(int type)
	// /* Retrieves whether for the given type of ResultSet object, the result
	// set's own updates are visible. */
	// private boolean ownUpdatesAreVisible(int type)
	/*
	 * Retrieves whether this database treats mixed case unquoted SQL
	 * identifiers as case insensitive and stores them in lower case.
	 */
	private boolean storesLowerCaseIdentifiers;
	/*
	 * Retrieves whether this database treats mixed case quoted SQL identifiers
	 * as case insensitive and stores them in lower case.
	 */
	private boolean storesLowerCaseQuotedIdentifiers;
	/*
	 * Retrieves whether this database treats mixed case unquoted SQL
	 * identifiers as case insensitive and stores them in mixed case.
	 */
	private boolean storesMixedCaseIdentifiers;
	/*
	 * Rhis database treats mixed case unquoted SQL identifiers as case
	 * insensitive and stores them in upper case.
	 */
	private boolean storesMixedCaseQuotedIdentifiers;
	/*
	 * Retrieves whether this database treats mixed case quoted SQL identifiers
	 * as case insensitive and stores them in upper case.
	 */
	private boolean storesUpperCaseIdentifiers;
	/* Retrieves whether this database supports ALTER TABLE with add column. */
	private boolean supportsAlterTableWithAddColumn;
	/* Retrieves whether this database supports ALTER TABLE with drop column. */
	private boolean supportsAlterTableWithDropColumn;
	/*
	 * Retrieves whether this database supports the ANSI92 entry level SQL
	 * grammar.
	 */
	private boolean supportsANSI92EntryLevelSQL;
	/*
	 * Retrieves whether this database supports the ANSI92 full SQL grammar
	 * supported.
	 */
	private boolean supportsANSI92FullSQL;
	/*
	 * Retrieves whether this database supports the ANSI92 intermediate SQL
	 * grammar supported.
	 */
	private boolean supportsANSI92IntermediateSQL;
	/* Retrieves whether this database supports batch updates. */
	private boolean supportsBatchUpdates;

	/*
	 * Retrieves whether a catalog name can be used in a data manipulation
	 * statement.
	 */
	private boolean supportsCatalogsInDataManipulation;
	/*
	 * Retrieves whether a catalog name can be used in an index definition
	 * statement.
	 */
	private boolean supportsCatalogsInIndexDefinitions;
	/*
	 * Retrieves whether a catalog name can be used in a privilege definition
	 * statement.
	 */
	private boolean supportsCatalogsInPrivilegeDefinitions;
	/*
	 * Retrieves whether a catalog name can be used in a procedure call
	 * statement.
	 */
	private boolean supportsCatalogsInProcedureCalls;
	/*
	 * Retrieves whether a catalog name can be used in a table definition
	 * statement.
	 */
	private boolean supportsCatalogsInTableDefinitions;
	/* Retrieves whether this database supports column aliasing. */
	private boolean supportsColumnAliasing;
	/*
	 * Retrieves whether this database supports the CONVERT function between SQL
	 * types.
	 */
	private boolean supportsConvert;
	// /* Retrieves whether this database supports the CONVERT for two given SQL
	// types. */
	// private boolean supportsConvert(int fromType, private int toType)
	/* Retrieves whether this database supports the ODBC Core SQL grammar. */
	private boolean supportsCoreSQLGrammar;
	/* Retrieves whether this database supports correlated subqueries. */
	private boolean supportsCorrelatedSubqueries;
	/*
	 * Retrieves whether this database supports both data definition and data
	 * manipulation statements within a transaction.
	 */
	private boolean supportsDataDefinitionAndDataManipulationTransactions;
	/*
	 * Retrieves whether this database supports only data manipulation
	 * statements within a transaction.
	 */
	private boolean supportsDataManipulationTransactionsOnly;
	/*
	 * Retrieves whether, when table correlation names are supported, they are
	 * restricted to being different from the names of the tables.
	 */
	private boolean supportsDifferentTableCorrelationNames;
	/* Retrieves whether this database supports expressions in ORDER BY lists. */
	private boolean supportsExpressionsInOrderBy;
	/* Retrieves whether this database supports the ODBC Extended SQL grammar. */
	private boolean supportsExtendedSQLGrammar;
	/* Retrieves whether this database supports full nested outer joins. */
	private boolean supportsFullOuterJoins;
	/*
	 * Retrieves whether auto-generated keys can be retrieved after a statement
	 * has been executed.
	 */
	private boolean supportsGetGeneratedKeys;
	/* Retrieves whether this database supports some form of GROUP BY clause. */
	private boolean supportsGroupBy;
	/*
	 * Retrieves whether this database supports using columns not included in
	 * the SELECT statement in a GROUP BY clause provided that all of the
	 * columns in the SELECT statement are included in the GROUP BY clause.
	 */
	private boolean supportsGroupByBeyondSelect;
	/*
	 * Retrieves whether this database supports using a column that not in the
	 * SELECT statement in a GROUP BY clause.
	 */
	private boolean supportsGroupByUnrelated;
	/*
	 * Retrieves whether this database supports the SQL Integrity Enhancement
	 * Facility.
	 */
	private boolean supportsIntegrityEnhancementFacility;
	/* Retrieves whether this database supports specifying a LIKE escape clause. */
	private boolean supportsLikeEscapeClause;
	/* Retrieves whether this database provides limited support for outer joins. */
	private boolean supportsLimitedOuterJoins;
	/* Retrieves whether this database supports the ODBC Minimum SQL grammar. */
	private boolean supportsMinimumSQLGrammar;
	/*
	 * Retrieves whether this database treats mixed case unquoted SQL
	 * identifiers as case sensitive and as a result stores them in mixed case.
	 */
	private boolean supportsMixedCaseIdentifiers;
	/*
	 * Retrieves whether this database treats mixed case quoted SQL identifiers
	 * as case sensitive and as a result stores them in mixed case.
	 */
	private boolean supportsMixedCaseQuotedIdentifiers;
	/*
	 * Retrieves whether it possible to have multiple ResultSet objects returned
	 * from a CallableStatement object simultaneously.
	 */
	private boolean supportsMultipleOpenResults;
	/*
	 * Retrieves whether this database supports getting multiple ResultSet
	 * objects from a single call to the method execute.
	 */
	private boolean supportsMultipleResultSets;
	/*
	 * Retrieves whether this database allows having multiple transactions open
	 * at once (on different connections).
	 */
	private boolean supportsMultipleTransactions;
	/*
	 * Retrieves whether this database supports named parameters to callable
	 * statements.
	 */
	private boolean supportsNamedParameters;
	/*
	 * Retrieves whether columns in this database may be defined as
	 * non-nullable.
	 */
	private boolean supportsNonNullableColumns;
	/*
	 * Retrieves whether this database supports keeping cursors open across
	 * commits.
	 */
	private boolean supportsOpenCursorsAcrossCommit;
	/*
	 * Retrieves whether this database supports keeping cursors open across
	 * rollbacks.
	 */
	private boolean supportsOpenCursorsAcrossRollback;
	/*
	 * Retrieves whether this database supports keeping statements open across
	 * commits.
	 */
	private boolean supportsOpenStatementsAcrossCommit;
	/*
	 * Retrieves whether this database supports keeping statements open across
	 * rollbacks.
	 */
	private boolean supportsOpenStatementsAcrossRollback;
	/*
	 * Retrieves whether this database supports using a column that not in the
	 * SELECT statement in an ORDER BY clause.
	 */
	private boolean supportsOrderByUnrelated;
	/* Retrieves whether this database supports some form of outer join. */
	private boolean supportsOuterJoins;
	/* Retrieves whether this database supports positioned DELETE statements. */
	private boolean supportsPositionedDelete;
	/* Retrieves whether this database supports positioned UPDATE statements. */
	private boolean supportsPositionedUpdate;
	// /* Retrieves whether this database supports the given concurrency type in
	// combination with the given result set type. */
	// private boolean supportsResultSetConcurrency(int type, private int
	// concurrency)
	// /* Retrieves whether this database supports the given result set
	// holdability. */
	// private boolean supportsResultSetHoldability(int holdability)
	// /* Retrieves whether this database supports the given result set type. */
	// private boolean supportsResultSetType(int type)
	/* Retrieves whether this database supports savepoints. */
	private boolean supportsSavepoints;
	/*
	 * Retrieves whether a schema name can be used in a data manipulation
	 * statement.
	 */
	private boolean supportsSchemasInDataManipulation;
	/*
	 * Retrieves whether a schema name can be used in an index definition
	 * statement.
	 */
	private boolean supportsSchemasInIndexDefinitions;
	/*
	 * Retrieves whether a schema name can be used in a privilege definition
	 * statement.
	 */
	private boolean supportsSchemasInPrivilegeDefinitions;
	/*
	 * Retrieves whether a schema name can be used in a procedure call
	 * statement.
	 */
	private boolean supportsSchemasInProcedureCalls;
	/*
	 * Retrieves whether a schema name can be used in a table definition
	 * statement.
	 */
	private boolean supportsSchemasInTableDefinitions;
	/* Retrieves whether this database supports SELECT FOR UPDATE statements. */
	private boolean supportsSelectForUpdate;
	/* Retrieves whether this database supports statement pooling. */
	private boolean supportsStatementPooling;
	/*
	 * Retrieves whether this database supports stored procedure calls that use
	 * the stored procedure escape syntax.
	 */
	private boolean supportsStoredProcedures;
	/*
	 * Retrieves whether this database supports subqueries in comparison
	 * expressions.
	 */
	private boolean supportsSubqueriesInComparisons;
	/*
	 * Retrieves whether this database supports subqueries in EXISTS
	 * expressions.
	 */
	private boolean supportsSubqueriesInExists;
	/* Retrieves whether this database supports subqueries in IN statements. */
	private boolean supportsSubqueriesInIns;
	/*
	 * Retrieves whether this database supports subqueries in quantified
	 * expressions.
	 */
	private boolean supportsSubqueriesInQuantifieds;
	/* Retrieves whether this database supports table correlation names. */
	private boolean supportsTableCorrelationNames;
	// /* Retrieves whether this database supports the given transactionolation
	// level. */
	// private boolean supportsTransactionIsolationLevel(int level)
	/* Retrieves whether this database supports transactions. */
	private boolean supportsTransactions;
	/* Retrieves whether this database supports SQL UNION. */
	private boolean supportsUnion;
	/* Retrieves whether this database supports SQL UNION ALL. */
	private boolean supportsUnionAll;
	// /* Retrieves whether or not a visible row update can be detected by
	// calling the method ResultSet.rowUpdated. */
	// private boolean updatesAreDetected(int type)
	/* Retrieves whether this database uses a file for each table. */
	private boolean usesLocalFilePerTable;
	private boolean usesLocalFiles;

	/**
	 * @return the allProceduresAreCallable
	 */
	public boolean isAllProceduresAreCallable() {
		return allProceduresAreCallable;
	}

	/**
	 * @param allProceduresAreCallable
	 *            the allProceduresAreCallable to set
	 */
	public void setAllProceduresAreCallable(boolean allProceduresAreCallable) {
		this.allProceduresAreCallable = allProceduresAreCallable;
	}

	/**
	 * @return the allTablesAreSelectable
	 */
	public boolean isAllTablesAreSelectable() {
		return allTablesAreSelectable;
	}

	/**
	 * @param allTablesAreSelectable
	 *            the allTablesAreSelectable to set
	 */
	public void setAllTablesAreSelectable(boolean allTablesAreSelectable) {
		this.allTablesAreSelectable = allTablesAreSelectable;
	}

	/**
	 * @return the dataDefinitionCausesTransactionCommit
	 */
	public boolean isDataDefinitionCausesTransactionCommit() {
		return dataDefinitionCausesTransactionCommit;
	}

	/**
	 * @param dataDefinitionCausesTransactionCommit
	 *            the dataDefinitionCausesTransactionCommit to set
	 */
	public void setDataDefinitionCausesTransactionCommit(
			boolean dataDefinitionCausesTransactionCommit) {
		this.dataDefinitionCausesTransactionCommit = dataDefinitionCausesTransactionCommit;
	}

	/**
	 * @return the dataDefinitionIgnoredInTransactions
	 */
	public boolean isDataDefinitionIgnoredInTransactions() {
		return dataDefinitionIgnoredInTransactions;
	}

	/**
	 * @param dataDefinitionIgnoredInTransactions
	 *            the dataDefinitionIgnoredInTransactions to set
	 */
	public void setDataDefinitionIgnoredInTransactions(
			boolean dataDefinitionIgnoredInTransactions) {
		this.dataDefinitionIgnoredInTransactions = dataDefinitionIgnoredInTransactions;
	}

	/**
	 * @return the doesmaxRowSizeIncludeBlobs
	 */
	public boolean isDoesmaxRowSizeIncludeBlobs() {
		return doesmaxRowSizeIncludeBlobs;
	}

	/**
	 * @param doesmaxRowSizeIncludeBlobs
	 *            the doesmaxRowSizeIncludeBlobs to set
	 */
	public void setDoesmaxRowSizeIncludeBlobs(boolean doesmaxRowSizeIncludeBlobs) {
		this.doesmaxRowSizeIncludeBlobs = doesmaxRowSizeIncludeBlobs;
	}

	/**
	 * @return the catalogSeparator
	 */
	public String getCatalogSeparator() {
		return catalogSeparator;
	}

	/**
	 * @param catalogSeparator
	 *            the catalogSeparator to set
	 */
	public void setCatalogSeparator(String catalogSeparator) {
		this.catalogSeparator = catalogSeparator;
	}

	/**
	 * @return the catalogTerm
	 */
	public String getCatalogTerm() {
		return catalogTerm;
	}

	/**
	 * @param catalogTerm
	 *            the catalogTerm to set
	 */
	public void setCatalogTerm(String catalogTerm) {
		this.catalogTerm = catalogTerm;
	}

	/**
	 * @return the databaseMajorVersion
	 */
	public int getDatabaseMajorVersion() {
		return databaseMajorVersion;
	}

	/**
	 * @param databaseMajorVersion
	 *            the databaseMajorVersion to set
	 */
	public void setDatabaseMajorVersion(int databaseMajorVersion) {
		this.databaseMajorVersion = databaseMajorVersion;
	}

	/**
	 * @return the databaseMinorVersion
	 */
	public int getDatabaseMinorVersion() {
		return databaseMinorVersion;
	}

	/**
	 * @param databaseMinorVersion
	 *            the databaseMinorVersion to set
	 */
	public void setDatabaseMinorVersion(int databaseMinorVersion) {
		this.databaseMinorVersion = databaseMinorVersion;
	}

	/**
	 * @return the databaseProductName
	 */
	public String getDatabaseProductName() {
		return databaseProductName;
	}

	/**
	 * @param databaseProductName
	 *            the databaseProductName to set
	 */
	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	/**
	 * @return the databaseProductVersion
	 */
	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	/**
	 * @param databaseProductVersion
	 *            the databaseProductVersion to set
	 */
	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}

	/**
	 * @return the defaultTransactionIsolation
	 */
	public int getDefaultTransactionIsolation() {
		return defaultTransactionIsolation;
	}

	/**
	 * @param defaultTransactionIsolation
	 *            the defaultTransactionIsolation to set
	 */
	public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
		this.defaultTransactionIsolation = defaultTransactionIsolation;
	}

	/**
	 * @return the driverMajorVersion
	 */
	public int getDriverMajorVersion() {
		return driverMajorVersion;
	}

	/**
	 * @param driverMajorVersion
	 *            the driverMajorVersion to set
	 */
	public void setDriverMajorVersion(int driverMajorVersion) {
		this.driverMajorVersion = driverMajorVersion;
	}

	/**
	 * @return the driverMinorVersion
	 */
	public int getDriverMinorVersion() {
		return driverMinorVersion;
	}

	/**
	 * @param driverMinorVersion
	 *            the driverMinorVersion to set
	 */
	public void setDriverMinorVersion(int driverMinorVersion) {
		this.driverMinorVersion = driverMinorVersion;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName
	 *            the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the driverVersion
	 */
	public String getDriverVersion() {
		return driverVersion;
	}

	/**
	 * @param driverVersion
	 *            the driverVersion to set
	 */
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	/**
	 * @return the extraNameCharacters
	 */
	public String getExtraNameCharacters() {
		return extraNameCharacters;
	}

	/**
	 * @param extraNameCharacters
	 *            the extraNameCharacters to set
	 */
	public void setExtraNameCharacters(String extraNameCharacters) {
		this.extraNameCharacters = extraNameCharacters;
	}

	/**
	 * @return the identifierQuoteString
	 */
	public String getIdentifierQuoteString() {
		return identifierQuoteString;
	}

	/**
	 * @param identifierQuoteString
	 *            the identifierQuoteString to set
	 */
	public void setIdentifierQuoteString(String identifierQuoteString) {
		this.identifierQuoteString = identifierQuoteString;
	}

	/**
	 * @return the jdbcMajorVersion
	 */
	public int getJdbcMajorVersion() {
		return jdbcMajorVersion;
	}

	/**
	 * @param jdbcMajorVersion
	 *            the jdbcMajorVersion to set
	 */
	public void setJdbcMajorVersion(int jdbcMajorVersion) {
		this.jdbcMajorVersion = jdbcMajorVersion;
	}

	/**
	 * @return the jdbcMinorVersion
	 */
	public int getJdbcMinorVersion() {
		return jdbcMinorVersion;
	}

	/**
	 * @param jdbcMinorVersion
	 *            the jdbcMinorVersion to set
	 */
	public void setJdbcMinorVersion(int jdbcMinorVersion) {
		this.jdbcMinorVersion = jdbcMinorVersion;
	}

	/**
	 * @return the maxBinaryLiteralLength
	 */
	public int getMaxBinaryLiteralLength() {
		return maxBinaryLiteralLength;
	}

	/**
	 * @param maxBinaryLiteralLength
	 *            the maxBinaryLiteralLength to set
	 */
	public void setMaxBinaryLiteralLength(int maxBinaryLiteralLength) {
		this.maxBinaryLiteralLength = maxBinaryLiteralLength;
	}

	/**
	 * @return the maxCatalogNameLength
	 */
	public int getMaxCatalogNameLength() {
		return maxCatalogNameLength;
	}

	/**
	 * @param maxCatalogNameLength
	 *            the maxCatalogNameLength to set
	 */
	public void setMaxCatalogNameLength(int maxCatalogNameLength) {
		this.maxCatalogNameLength = maxCatalogNameLength;
	}

	/**
	 * @return the maxCharLiteralLength
	 */
	public int getMaxCharLiteralLength() {
		return maxCharLiteralLength;
	}

	/**
	 * @param maxCharLiteralLength
	 *            the maxCharLiteralLength to set
	 */
	public void setMaxCharLiteralLength(int maxCharLiteralLength) {
		this.maxCharLiteralLength = maxCharLiteralLength;
	}

	/**
	 * @return the maxColumnNameLength
	 */
	public int getMaxColumnNameLength() {
		return maxColumnNameLength;
	}

	/**
	 * @param maxColumnNameLength
	 *            the maxColumnNameLength to set
	 */
	public void setMaxColumnNameLength(int maxColumnNameLength) {
		this.maxColumnNameLength = maxColumnNameLength;
	}

	/**
	 * @return the maxColumnsInGroupBy
	 */
	public int getMaxColumnsInGroupBy() {
		return maxColumnsInGroupBy;
	}

	/**
	 * @param maxColumnsInGroupBy
	 *            the maxColumnsInGroupBy to set
	 */
	public void setMaxColumnsInGroupBy(int maxColumnsInGroupBy) {
		this.maxColumnsInGroupBy = maxColumnsInGroupBy;
	}

	/**
	 * @return the maxColumnsInIndex
	 */
	public int getMaxColumnsInIndex() {
		return maxColumnsInIndex;
	}

	/**
	 * @param maxColumnsInIndex
	 *            the maxColumnsInIndex to set
	 */
	public void setMaxColumnsInIndex(int maxColumnsInIndex) {
		this.maxColumnsInIndex = maxColumnsInIndex;
	}

	/**
	 * @return the maxColumnsInOrderBy
	 */
	public int getMaxColumnsInOrderBy() {
		return maxColumnsInOrderBy;
	}

	/**
	 * @param maxColumnsInOrderBy
	 *            the maxColumnsInOrderBy to set
	 */
	public void setMaxColumnsInOrderBy(int maxColumnsInOrderBy) {
		this.maxColumnsInOrderBy = maxColumnsInOrderBy;
	}

	/**
	 * @return the maxColumnsInSelect
	 */
	public int getMaxColumnsInSelect() {
		return maxColumnsInSelect;
	}

	/**
	 * @param maxColumnsInSelect
	 *            the maxColumnsInSelect to set
	 */
	public void setMaxColumnsInSelect(int maxColumnsInSelect) {
		this.maxColumnsInSelect = maxColumnsInSelect;
	}

	/**
	 * @return the maxColumnsInTable
	 */
	public int getMaxColumnsInTable() {
		return maxColumnsInTable;
	}

	/**
	 * @param maxColumnsInTable
	 *            the maxColumnsInTable to set
	 */
	public void setMaxColumnsInTable(int maxColumnsInTable) {
		this.maxColumnsInTable = maxColumnsInTable;
	}

	/**
	 * @return the maxConnections
	 */
	public int getMaxConnections() {
		return maxConnections;
	}

	/**
	 * @param maxConnections
	 *            the maxConnections to set
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	/**
	 * @return the maxCursorNameLength
	 */
	public int getMaxCursorNameLength() {
		return maxCursorNameLength;
	}

	/**
	 * @param maxCursorNameLength
	 *            the maxCursorNameLength to set
	 */
	public void setMaxCursorNameLength(int maxCursorNameLength) {
		this.maxCursorNameLength = maxCursorNameLength;
	}

	/**
	 * @return the maxIndexLength
	 */
	public int getMaxIndexLength() {
		return maxIndexLength;
	}

	/**
	 * @param maxIndexLength
	 *            the maxIndexLength to set
	 */
	public void setMaxIndexLength(int maxIndexLength) {
		this.maxIndexLength = maxIndexLength;
	}

	/**
	 * @return the maxProcedureNameLength
	 */
	public int getMaxProcedureNameLength() {
		return maxProcedureNameLength;
	}

	/**
	 * @param maxProcedureNameLength
	 *            the maxProcedureNameLength to set
	 */
	public void setMaxProcedureNameLength(int maxProcedureNameLength) {
		this.maxProcedureNameLength = maxProcedureNameLength;
	}

	/**
	 * @return the maxRowSize
	 */
	public int getMaxRowSize() {
		return maxRowSize;
	}

	/**
	 * @param maxRowSize
	 *            the maxRowSize to set
	 */
	public void setMaxRowSize(int maxRowSize) {
		this.maxRowSize = maxRowSize;
	}

	/**
	 * @return the maxSchemaNameLength
	 */
	public int getMaxSchemaNameLength() {
		return maxSchemaNameLength;
	}

	/**
	 * @param maxSchemaNameLength
	 *            the maxSchemaNameLength to set
	 */
	public void setMaxSchemaNameLength(int maxSchemaNameLength) {
		this.maxSchemaNameLength = maxSchemaNameLength;
	}

	/**
	 * @return the maxStatementLength
	 */
	public int getMaxStatementLength() {
		return maxStatementLength;
	}

	/**
	 * @param maxStatementLength
	 *            the maxStatementLength to set
	 */
	public void setMaxStatementLength(int maxStatementLength) {
		this.maxStatementLength = maxStatementLength;
	}

	/**
	 * @return the maxStatements
	 */
	public int getMaxStatements() {
		return maxStatements;
	}

	/**
	 * @param maxStatements
	 *            the maxStatements to set
	 */
	public void setMaxStatements(int maxStatements) {
		this.maxStatements = maxStatements;
	}

	/**
	 * @return the maxTableNameLength
	 */
	public int getMaxTableNameLength() {
		return maxTableNameLength;
	}

	/**
	 * @param maxTableNameLength
	 *            the maxTableNameLength to set
	 */
	public void setMaxTableNameLength(int maxTableNameLength) {
		this.maxTableNameLength = maxTableNameLength;
	}

	/**
	 * @return the maxTablesInSelect
	 */
	public int getMaxTablesInSelect() {
		return maxTablesInSelect;
	}

	/**
	 * @param maxTablesInSelect
	 *            the maxTablesInSelect to set
	 */
	public void setMaxTablesInSelect(int maxTablesInSelect) {
		this.maxTablesInSelect = maxTablesInSelect;
	}

	/**
	 * @return the maxUserNameLength
	 */
	public int getMaxUserNameLength() {
		return maxUserNameLength;
	}

	/**
	 * @param maxUserNameLength
	 *            the maxUserNameLength to set
	 */
	public void setMaxUserNameLength(int maxUserNameLength) {
		this.maxUserNameLength = maxUserNameLength;
	}

	/**
	 * @return the numericFunctions
	 */
	public String getNumericFunctions() {
		return numericFunctions;
	}

	/**
	 * @param numericFunctions
	 *            the numericFunctions to set
	 */
	public void setNumericFunctions(String numericFunctions) {
		this.numericFunctions = numericFunctions;
	}

	/**
	 * @return the procedureTerm
	 */
	public String getProcedureTerm() {
		return procedureTerm;
	}

	/**
	 * @param procedureTerm
	 *            the procedureTerm to set
	 */
	public void setProcedureTerm(String procedureTerm) {
		this.procedureTerm = procedureTerm;
	}

	/**
	 * @return the resultSetHoldability
	 */
	public int getResultSetHoldability() {
		return resultSetHoldability;
	}

	/**
	 * @param resultSetHoldability
	 *            the resultSetHoldability to set
	 */
	public void setResultSetHoldability(int resultSetHoldability) {
		this.resultSetHoldability = resultSetHoldability;
	}

	/**
	 * @return the schemaTerm
	 */
	public String getSchemaTerm() {
		return schemaTerm;
	}

	/**
	 * @param schemaTerm
	 *            the schemaTerm to set
	 */
	public void setSchemaTerm(String schemaTerm) {
		this.schemaTerm = schemaTerm;
	}

	/**
	 * @return the searchStringEscape
	 */
	public String getSearchStringEscape() {
		return searchStringEscape;
	}

	/**
	 * @param searchStringEscape
	 *            the searchStringEscape to set
	 */
	public void setSearchStringEscape(String searchStringEscape) {
		this.searchStringEscape = searchStringEscape;
	}

	/**
	 * @return the sqlKeywords
	 */
	public String getSqlKeywords() {
		return sqlKeywords;
	}

	/**
	 * @param sqlKeywords
	 *            the sqlKeywords to set
	 */
	public void setSqlKeywords(String sqlKeywords) {
		this.sqlKeywords = sqlKeywords;
	}

	/**
	 * @return the stringFunctions
	 */
	public String getStringFunctions() {
		return stringFunctions;
	}

	/**
	 * @param stringFunctions
	 *            the stringFunctions to set
	 */
	public void setStringFunctions(String stringFunctions) {
		this.stringFunctions = stringFunctions;
	}

	/**
	 * @return the systemFunctions
	 */
	public String getSystemFunctions() {
		return systemFunctions;
	}

	/**
	 * @param systemFunctions
	 *            the systemFunctions to set
	 */
	public void setSystemFunctions(String systemFunctions) {
		this.systemFunctions = systemFunctions;
	}

	/**
	 * @return the timeDateFunctions
	 */
	public String getTimeDateFunctions() {
		return timeDateFunctions;
	}

	/**
	 * @param timeDateFunctions
	 *            the timeDateFunctions to set
	 */
	public void setTimeDateFunctions(String timeDateFunctions) {
		this.timeDateFunctions = timeDateFunctions;
	}

	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL
	 *            the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * @return the isCatalogAtStart
	 */
	public boolean isCatalogAtStart() {
		return isCatalogAtStart;
	}

	/**
	 * @param isCatalogAtStart
	 *            the isCatalogAtStart to set
	 */
	public void setCatalogAtStart(boolean isCatalogAtStart) {
		this.isCatalogAtStart = isCatalogAtStart;
	}

	/**
	 * @return the isReadOnly
	 */
	public boolean isReadOnly() {
		return isReadOnly;
	}

	/**
	 * @param isReadOnly
	 *            the isReadOnly to set
	 */
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	/**
	 * @return the nullPlusNonNullIsNull
	 */
	public boolean isNullPlusNonNullIsNull() {
		return nullPlusNonNullIsNull;
	}

	/**
	 * @param nullPlusNonNullIsNull
	 *            the nullPlusNonNullIsNull to set
	 */
	public void setNullPlusNonNullIsNull(boolean nullPlusNonNullIsNull) {
		this.nullPlusNonNullIsNull = nullPlusNonNullIsNull;
	}

	/**
	 * @return the nullsAreSortedAtEnd
	 */
	public boolean isNullsAreSortedAtEnd() {
		return nullsAreSortedAtEnd;
	}

	/**
	 * @param nullsAreSortedAtEnd
	 *            the nullsAreSortedAtEnd to set
	 */
	public void setNullsAreSortedAtEnd(boolean nullsAreSortedAtEnd) {
		this.nullsAreSortedAtEnd = nullsAreSortedAtEnd;
	}

	/**
	 * @return the nullsAreSortedAtStart
	 */
	public boolean isNullsAreSortedAtStart() {
		return nullsAreSortedAtStart;
	}

	/**
	 * @param nullsAreSortedAtStart
	 *            the nullsAreSortedAtStart to set
	 */
	public void setNullsAreSortedAtStart(boolean nullsAreSortedAtStart) {
		this.nullsAreSortedAtStart = nullsAreSortedAtStart;
	}

	/**
	 * @return the nullsAreSortedHigh
	 */
	public boolean isNullsAreSortedHigh() {
		return nullsAreSortedHigh;
	}

	/**
	 * @param nullsAreSortedHigh
	 *            the nullsAreSortedHigh to set
	 */
	public void setNullsAreSortedHigh(boolean nullsAreSortedHigh) {
		this.nullsAreSortedHigh = nullsAreSortedHigh;
	}

	/**
	 * @return the nullsAreSortedLow
	 */
	public boolean isNullsAreSortedLow() {
		return nullsAreSortedLow;
	}

	/**
	 * @param nullsAreSortedLow
	 *            the nullsAreSortedLow to set
	 */
	public void setNullsAreSortedLow(boolean nullsAreSortedLow) {
		this.nullsAreSortedLow = nullsAreSortedLow;
	}

	/**
	 * @return the storesLowerCaseIdentifiers
	 */
	public boolean isStoresLowerCaseIdentifiers() {
		return storesLowerCaseIdentifiers;
	}

	/**
	 * @param storesLowerCaseIdentifiers
	 *            the storesLowerCaseIdentifiers to set
	 */
	public void setStoresLowerCaseIdentifiers(boolean storesLowerCaseIdentifiers) {
		this.storesLowerCaseIdentifiers = storesLowerCaseIdentifiers;
	}

	/**
	 * @return the storesLowerCaseQuotedIdentifiers
	 */
	public boolean isStoresLowerCaseQuotedIdentifiers() {
		return storesLowerCaseQuotedIdentifiers;
	}

	/**
	 * @param storesLowerCaseQuotedIdentifiers
	 *            the storesLowerCaseQuotedIdentifiers to set
	 */
	public void setStoresLowerCaseQuotedIdentifiers(
			boolean storesLowerCaseQuotedIdentifiers) {
		this.storesLowerCaseQuotedIdentifiers = storesLowerCaseQuotedIdentifiers;
	}

	/**
	 * @return the storesMixedCaseIdentifiers
	 */
	public boolean isStoresMixedCaseIdentifiers() {
		return storesMixedCaseIdentifiers;
	}

	/**
	 * @param storesMixedCaseIdentifiers
	 *            the storesMixedCaseIdentifiers to set
	 */
	public void setStoresMixedCaseIdentifiers(boolean storesMixedCaseIdentifiers) {
		this.storesMixedCaseIdentifiers = storesMixedCaseIdentifiers;
	}

	/**
	 * @return the storesMixedCaseQuotedIdentifiers
	 */
	public boolean isStoresMixedCaseQuotedIdentifiers() {
		return storesMixedCaseQuotedIdentifiers;
	}

	/**
	 * @param storesMixedCaseQuotedIdentifiers
	 *            the storesMixedCaseQuotedIdentifiers to set
	 */
	public void setStoresMixedCaseQuotedIdentifiers(
			boolean storesMixedCaseQuotedIdentifiers) {
		this.storesMixedCaseQuotedIdentifiers = storesMixedCaseQuotedIdentifiers;
	}

	/**
	 * @return the storesUpperCaseIdentifiers
	 */
	public boolean isStoresUpperCaseIdentifiers() {
		return storesUpperCaseIdentifiers;
	}

	/**
	 * @param storesUpperCaseIdentifiers
	 *            the storesUpperCaseIdentifiers to set
	 */
	public void setStoresUpperCaseIdentifiers(boolean storesUpperCaseIdentifiers) {
		this.storesUpperCaseIdentifiers = storesUpperCaseIdentifiers;
	}

	/**
	 * @return the supportsAlterTableWithAddColumn
	 */
	public boolean isSupportsAlterTableWithAddColumn() {
		return supportsAlterTableWithAddColumn;
	}

	/**
	 * @param supportsAlterTableWithAddColumn
	 *            the supportsAlterTableWithAddColumn to set
	 */
	public void setSupportsAlterTableWithAddColumn(
			boolean supportsAlterTableWithAddColumn) {
		this.supportsAlterTableWithAddColumn = supportsAlterTableWithAddColumn;
	}

	/**
	 * @return the supportsAlterTableWithDropColumn
	 */
	public boolean isSupportsAlterTableWithDropColumn() {
		return supportsAlterTableWithDropColumn;
	}

	/**
	 * @param supportsAlterTableWithDropColumn
	 *            the supportsAlterTableWithDropColumn to set
	 */
	public void setSupportsAlterTableWithDropColumn(
			boolean supportsAlterTableWithDropColumn) {
		this.supportsAlterTableWithDropColumn = supportsAlterTableWithDropColumn;
	}

	/**
	 * @return the supportsANSI92EntryLevelSQL
	 */
	public boolean isSupportsANSI92EntryLevelSQL() {
		return supportsANSI92EntryLevelSQL;
	}

	/**
	 * @param supportsANSI92EntryLevelSQL
	 *            the supportsANSI92EntryLevelSQL to set
	 */
	public void setSupportsANSI92EntryLevelSQL(
			boolean supportsANSI92EntryLevelSQL) {
		this.supportsANSI92EntryLevelSQL = supportsANSI92EntryLevelSQL;
	}

	/**
	 * @return the supportsANSI92FullSQL
	 */
	public boolean isSupportsANSI92FullSQL() {
		return supportsANSI92FullSQL;
	}

	/**
	 * @param supportsANSI92FullSQL
	 *            the supportsANSI92FullSQL to set
	 */
	public void setSupportsANSI92FullSQL(boolean supportsANSI92FullSQL) {
		this.supportsANSI92FullSQL = supportsANSI92FullSQL;
	}

	/**
	 * @return the supportsANSI92IntermediateSQL
	 */
	public boolean isSupportsANSI92IntermediateSQL() {
		return supportsANSI92IntermediateSQL;
	}

	/**
	 * @param supportsANSI92IntermediateSQL
	 *            the supportsANSI92IntermediateSQL to set
	 */
	public void setSupportsANSI92IntermediateSQL(
			boolean supportsANSI92IntermediateSQL) {
		this.supportsANSI92IntermediateSQL = supportsANSI92IntermediateSQL;
	}

	/**
	 * @return the supportsBatchUpdates
	 */
	public boolean isSupportsBatchUpdates() {
		return supportsBatchUpdates;
	}

	/**
	 * @param supportsBatchUpdates
	 *            the supportsBatchUpdates to set
	 */
	public void setSupportsBatchUpdates(boolean supportsBatchUpdates) {
		this.supportsBatchUpdates = supportsBatchUpdates;
	}

	/**
	 * @return the supportsCatalogsInDataManipulation
	 */
	public boolean isSupportsCatalogsInDataManipulation() {
		return supportsCatalogsInDataManipulation;
	}

	/**
	 * @param supportsCatalogsInDataManipulation
	 *            the supportsCatalogsInDataManipulation to set
	 */
	public void setSupportsCatalogsInDataManipulation(
			boolean supportsCatalogsInDataManipulation) {
		this.supportsCatalogsInDataManipulation = supportsCatalogsInDataManipulation;
	}

	/**
	 * @return the supportsCatalogsInIndexDefinitions
	 */
	public boolean isSupportsCatalogsInIndexDefinitions() {
		return supportsCatalogsInIndexDefinitions;
	}

	/**
	 * @param supportsCatalogsInIndexDefinitions
	 *            the supportsCatalogsInIndexDefinitions to set
	 */
	public void setSupportsCatalogsInIndexDefinitions(
			boolean supportsCatalogsInIndexDefinitions) {
		this.supportsCatalogsInIndexDefinitions = supportsCatalogsInIndexDefinitions;
	}

	/**
	 * @return the supportsCatalogsInPrivilegeDefinitions
	 */
	public boolean isSupportsCatalogsInPrivilegeDefinitions() {
		return supportsCatalogsInPrivilegeDefinitions;
	}

	/**
	 * @param supportsCatalogsInPrivilegeDefinitions
	 *            the supportsCatalogsInPrivilegeDefinitions to set
	 */
	public void setSupportsCatalogsInPrivilegeDefinitions(
			boolean supportsCatalogsInPrivilegeDefinitions) {
		this.supportsCatalogsInPrivilegeDefinitions = supportsCatalogsInPrivilegeDefinitions;
	}

	/**
	 * @return the supportsCatalogsInProcedureCalls
	 */
	public boolean isSupportsCatalogsInProcedureCalls() {
		return supportsCatalogsInProcedureCalls;
	}

	/**
	 * @param supportsCatalogsInProcedureCalls
	 *            the supportsCatalogsInProcedureCalls to set
	 */
	public void setSupportsCatalogsInProcedureCalls(
			boolean supportsCatalogsInProcedureCalls) {
		this.supportsCatalogsInProcedureCalls = supportsCatalogsInProcedureCalls;
	}

	/**
	 * @return the supportsCatalogsInTableDefinitions
	 */
	public boolean isSupportsCatalogsInTableDefinitions() {
		return supportsCatalogsInTableDefinitions;
	}

	/**
	 * @param supportsCatalogsInTableDefinitions
	 *            the supportsCatalogsInTableDefinitions to set
	 */
	public void setSupportsCatalogsInTableDefinitions(
			boolean supportsCatalogsInTableDefinitions) {
		this.supportsCatalogsInTableDefinitions = supportsCatalogsInTableDefinitions;
	}

	/**
	 * @return the supportsColumnAliasing
	 */
	public boolean isSupportsColumnAliasing() {
		return supportsColumnAliasing;
	}

	/**
	 * @param supportsColumnAliasing
	 *            the supportsColumnAliasing to set
	 */
	public void setSupportsColumnAliasing(boolean supportsColumnAliasing) {
		this.supportsColumnAliasing = supportsColumnAliasing;
	}

	/**
	 * @return the supportsConvert
	 */
	public boolean isSupportsConvert() {
		return supportsConvert;
	}

	/**
	 * @param supportsConvert
	 *            the supportsConvert to set
	 */
	public void setSupportsConvert(boolean supportsConvert) {
		this.supportsConvert = supportsConvert;
	}

	/**
	 * @return the supportsCoreSQLGrammar
	 */
	public boolean isSupportsCoreSQLGrammar() {
		return supportsCoreSQLGrammar;
	}

	/**
	 * @param supportsCoreSQLGrammar
	 *            the supportsCoreSQLGrammar to set
	 */
	public void setSupportsCoreSQLGrammar(boolean supportsCoreSQLGrammar) {
		this.supportsCoreSQLGrammar = supportsCoreSQLGrammar;
	}

	/**
	 * @return the supportsCorrelatedSubqueries
	 */
	public boolean isSupportsCorrelatedSubqueries() {
		return supportsCorrelatedSubqueries;
	}

	/**
	 * @param supportsCorrelatedSubqueries
	 *            the supportsCorrelatedSubqueries to set
	 */
	public void setSupportsCorrelatedSubqueries(
			boolean supportsCorrelatedSubqueries) {
		this.supportsCorrelatedSubqueries = supportsCorrelatedSubqueries;
	}

	/**
	 * @return the supportsDataDefinitionAndDataManipulationTransactions
	 */
	public boolean isSupportsDataDefinitionAndDataManipulationTransactions() {
		return supportsDataDefinitionAndDataManipulationTransactions;
	}

	/**
	 * @param supportsDataDefinitionAndDataManipulationTransactions
	 *            the supportsDataDefinitionAndDataManipulationTransactions to
	 *            set
	 */
	public void setSupportsDataDefinitionAndDataManipulationTransactions(
			boolean supportsDataDefinitionAndDataManipulationTransactions) {
		this.supportsDataDefinitionAndDataManipulationTransactions = supportsDataDefinitionAndDataManipulationTransactions;
	}

	/**
	 * @return the supportsDataManipulationTransactionsOnly
	 */
	public boolean isSupportsDataManipulationTransactionsOnly() {
		return supportsDataManipulationTransactionsOnly;
	}

	/**
	 * @param supportsDataManipulationTransactionsOnly
	 *            the supportsDataManipulationTransactionsOnly to set
	 */
	public void setSupportsDataManipulationTransactionsOnly(
			boolean supportsDataManipulationTransactionsOnly) {
		this.supportsDataManipulationTransactionsOnly = supportsDataManipulationTransactionsOnly;
	}

	/**
	 * @return the supportsDifferentTableCorrelationNames
	 */
	public boolean isSupportsDifferentTableCorrelationNames() {
		return supportsDifferentTableCorrelationNames;
	}

	/**
	 * @param supportsDifferentTableCorrelationNames
	 *            the supportsDifferentTableCorrelationNames to set
	 */
	public void setSupportsDifferentTableCorrelationNames(
			boolean supportsDifferentTableCorrelationNames) {
		this.supportsDifferentTableCorrelationNames = supportsDifferentTableCorrelationNames;
	}

	/**
	 * @return the supportsExpressionsInOrderBy
	 */
	public boolean isSupportsExpressionsInOrderBy() {
		return supportsExpressionsInOrderBy;
	}

	/**
	 * @param supportsExpressionsInOrderBy
	 *            the supportsExpressionsInOrderBy to set
	 */
	public void setSupportsExpressionsInOrderBy(
			boolean supportsExpressionsInOrderBy) {
		this.supportsExpressionsInOrderBy = supportsExpressionsInOrderBy;
	}

	/**
	 * @return the supportsExtendedSQLGrammar
	 */
	public boolean isSupportsExtendedSQLGrammar() {
		return supportsExtendedSQLGrammar;
	}

	/**
	 * @param supportsExtendedSQLGrammar
	 *            the supportsExtendedSQLGrammar to set
	 */
	public void setSupportsExtendedSQLGrammar(boolean supportsExtendedSQLGrammar) {
		this.supportsExtendedSQLGrammar = supportsExtendedSQLGrammar;
	}

	/**
	 * @return the supportsFullOuterJoins
	 */
	public boolean isSupportsFullOuterJoins() {
		return supportsFullOuterJoins;
	}

	/**
	 * @param supportsFullOuterJoins
	 *            the supportsFullOuterJoins to set
	 */
	public void setSupportsFullOuterJoins(boolean supportsFullOuterJoins) {
		this.supportsFullOuterJoins = supportsFullOuterJoins;
	}

	/**
	 * @return the supportsGetGeneratedKeys
	 */
	public boolean isSupportsGetGeneratedKeys() {
		return supportsGetGeneratedKeys;
	}

	/**
	 * @param supportsGetGeneratedKeys
	 *            the supportsGetGeneratedKeys to set
	 */
	public void setSupportsGetGeneratedKeys(boolean supportsGetGeneratedKeys) {
		this.supportsGetGeneratedKeys = supportsGetGeneratedKeys;
	}

	/**
	 * @return the supportsGroupBy
	 */
	public boolean isSupportsGroupBy() {
		return supportsGroupBy;
	}

	/**
	 * @param supportsGroupBy
	 *            the supportsGroupBy to set
	 */
	public void setSupportsGroupBy(boolean supportsGroupBy) {
		this.supportsGroupBy = supportsGroupBy;
	}

	/**
	 * @return the supportsGroupByBeyondSelect
	 */
	public boolean isSupportsGroupByBeyondSelect() {
		return supportsGroupByBeyondSelect;
	}

	/**
	 * @param supportsGroupByBeyondSelect
	 *            the supportsGroupByBeyondSelect to set
	 */
	public void setSupportsGroupByBeyondSelect(
			boolean supportsGroupByBeyondSelect) {
		this.supportsGroupByBeyondSelect = supportsGroupByBeyondSelect;
	}

	/**
	 * @return the supportsGroupByUnrelated
	 */
	public boolean isSupportsGroupByUnrelated() {
		return supportsGroupByUnrelated;
	}

	/**
	 * @param supportsGroupByUnrelated
	 *            the supportsGroupByUnrelated to set
	 */
	public void setSupportsGroupByUnrelated(boolean supportsGroupByUnrelated) {
		this.supportsGroupByUnrelated = supportsGroupByUnrelated;
	}

	/**
	 * @return the supportsIntegrityEnhancementFacility
	 */
	public boolean isSupportsIntegrityEnhancementFacility() {
		return supportsIntegrityEnhancementFacility;
	}

	/**
	 * @param supportsIntegrityEnhancementFacility
	 *            the supportsIntegrityEnhancementFacility to set
	 */
	public void setSupportsIntegrityEnhancementFacility(
			boolean supportsIntegrityEnhancementFacility) {
		this.supportsIntegrityEnhancementFacility = supportsIntegrityEnhancementFacility;
	}

	/**
	 * @return the supportsLikeEscapeClause
	 */
	public boolean isSupportsLikeEscapeClause() {
		return supportsLikeEscapeClause;
	}

	/**
	 * @param supportsLikeEscapeClause
	 *            the supportsLikeEscapeClause to set
	 */
	public void setSupportsLikeEscapeClause(boolean supportsLikeEscapeClause) {
		this.supportsLikeEscapeClause = supportsLikeEscapeClause;
	}

	/**
	 * @return the supportsLimitedOuterJoins
	 */
	public boolean isSupportsLimitedOuterJoins() {
		return supportsLimitedOuterJoins;
	}

	/**
	 * @param supportsLimitedOuterJoins
	 *            the supportsLimitedOuterJoins to set
	 */
	public void setSupportsLimitedOuterJoins(boolean supportsLimitedOuterJoins) {
		this.supportsLimitedOuterJoins = supportsLimitedOuterJoins;
	}

	/**
	 * @return the supportsMinimumSQLGrammar
	 */
	public boolean isSupportsMinimumSQLGrammar() {
		return supportsMinimumSQLGrammar;
	}

	/**
	 * @param supportsMinimumSQLGrammar
	 *            the supportsMinimumSQLGrammar to set
	 */
	public void setSupportsMinimumSQLGrammar(boolean supportsMinimumSQLGrammar) {
		this.supportsMinimumSQLGrammar = supportsMinimumSQLGrammar;
	}

	/**
	 * @return the supportsMixedCaseIdentifiers
	 */
	public boolean isSupportsMixedCaseIdentifiers() {
		return supportsMixedCaseIdentifiers;
	}

	/**
	 * @param supportsMixedCaseIdentifiers
	 *            the supportsMixedCaseIdentifiers to set
	 */
	public void setSupportsMixedCaseIdentifiers(
			boolean supportsMixedCaseIdentifiers) {
		this.supportsMixedCaseIdentifiers = supportsMixedCaseIdentifiers;
	}

	/**
	 * @return the supportsMixedCaseQuotedIdentifiers
	 */
	public boolean isSupportsMixedCaseQuotedIdentifiers() {
		return supportsMixedCaseQuotedIdentifiers;
	}

	/**
	 * @param supportsMixedCaseQuotedIdentifiers
	 *            the supportsMixedCaseQuotedIdentifiers to set
	 */
	public void setSupportsMixedCaseQuotedIdentifiers(
			boolean supportsMixedCaseQuotedIdentifiers) {
		this.supportsMixedCaseQuotedIdentifiers = supportsMixedCaseQuotedIdentifiers;
	}

	/**
	 * @return the supportsMultipleOpenResults
	 */
	public boolean isSupportsMultipleOpenResults() {
		return supportsMultipleOpenResults;
	}

	/**
	 * @param supportsMultipleOpenResults
	 *            the supportsMultipleOpenResults to set
	 */
	public void setSupportsMultipleOpenResults(
			boolean supportsMultipleOpenResults) {
		this.supportsMultipleOpenResults = supportsMultipleOpenResults;
	}

	/**
	 * @return the supportsMultipleResultSets
	 */
	public boolean isSupportsMultipleResultSets() {
		return supportsMultipleResultSets;
	}

	/**
	 * @param supportsMultipleResultSets
	 *            the supportsMultipleResultSets to set
	 */
	public void setSupportsMultipleResultSets(boolean supportsMultipleResultSets) {
		this.supportsMultipleResultSets = supportsMultipleResultSets;
	}

	/**
	 * @return the supportsMultipleTransactions
	 */
	public boolean isSupportsMultipleTransactions() {
		return supportsMultipleTransactions;
	}

	/**
	 * @param supportsMultipleTransactions
	 *            the supportsMultipleTransactions to set
	 */
	public void setSupportsMultipleTransactions(
			boolean supportsMultipleTransactions) {
		this.supportsMultipleTransactions = supportsMultipleTransactions;
	}

	/**
	 * @return the supportsNamedParameters
	 */
	public boolean isSupportsNamedParameters() {
		return supportsNamedParameters;
	}

	/**
	 * @param supportsNamedParameters
	 *            the supportsNamedParameters to set
	 */
	public void setSupportsNamedParameters(boolean supportsNamedParameters) {
		this.supportsNamedParameters = supportsNamedParameters;
	}

	/**
	 * @return the supportsNonNullableColumns
	 */
	public boolean isSupportsNonNullableColumns() {
		return supportsNonNullableColumns;
	}

	/**
	 * @param supportsNonNullableColumns
	 *            the supportsNonNullableColumns to set
	 */
	public void setSupportsNonNullableColumns(boolean supportsNonNullableColumns) {
		this.supportsNonNullableColumns = supportsNonNullableColumns;
	}

	/**
	 * @return the supportsOpenCursorsAcrossCommit
	 */
	public boolean isSupportsOpenCursorsAcrossCommit() {
		return supportsOpenCursorsAcrossCommit;
	}

	/**
	 * @param supportsOpenCursorsAcrossCommit
	 *            the supportsOpenCursorsAcrossCommit to set
	 */
	public void setSupportsOpenCursorsAcrossCommit(
			boolean supportsOpenCursorsAcrossCommit) {
		this.supportsOpenCursorsAcrossCommit = supportsOpenCursorsAcrossCommit;
	}

	/**
	 * @return the supportsOpenCursorsAcrossRollback
	 */
	public boolean isSupportsOpenCursorsAcrossRollback() {
		return supportsOpenCursorsAcrossRollback;
	}

	/**
	 * @param supportsOpenCursorsAcrossRollback
	 *            the supportsOpenCursorsAcrossRollback to set
	 */
	public void setSupportsOpenCursorsAcrossRollback(
			boolean supportsOpenCursorsAcrossRollback) {
		this.supportsOpenCursorsAcrossRollback = supportsOpenCursorsAcrossRollback;
	}

	/**
	 * @return the supportsOpenStatementsAcrossCommit
	 */
	public boolean isSupportsOpenStatementsAcrossCommit() {
		return supportsOpenStatementsAcrossCommit;
	}

	/**
	 * @param supportsOpenStatementsAcrossCommit
	 *            the supportsOpenStatementsAcrossCommit to set
	 */
	public void setSupportsOpenStatementsAcrossCommit(
			boolean supportsOpenStatementsAcrossCommit) {
		this.supportsOpenStatementsAcrossCommit = supportsOpenStatementsAcrossCommit;
	}

	/**
	 * @return the supportsOpenStatementsAcrossRollback
	 */
	public boolean isSupportsOpenStatementsAcrossRollback() {
		return supportsOpenStatementsAcrossRollback;
	}

	/**
	 * @param supportsOpenStatementsAcrossRollback
	 *            the supportsOpenStatementsAcrossRollback to set
	 */
	public void setSupportsOpenStatementsAcrossRollback(
			boolean supportsOpenStatementsAcrossRollback) {
		this.supportsOpenStatementsAcrossRollback = supportsOpenStatementsAcrossRollback;
	}

	/**
	 * @return the supportsOrderByUnrelated
	 */
	public boolean isSupportsOrderByUnrelated() {
		return supportsOrderByUnrelated;
	}

	/**
	 * @param supportsOrderByUnrelated
	 *            the supportsOrderByUnrelated to set
	 */
	public void setSupportsOrderByUnrelated(boolean supportsOrderByUnrelated) {
		this.supportsOrderByUnrelated = supportsOrderByUnrelated;
	}

	/**
	 * @return the supportsOuterJoins
	 */
	public boolean isSupportsOuterJoins() {
		return supportsOuterJoins;
	}

	/**
	 * @param supportsOuterJoins
	 *            the supportsOuterJoins to set
	 */
	public void setSupportsOuterJoins(boolean supportsOuterJoins) {
		this.supportsOuterJoins = supportsOuterJoins;
	}

	/**
	 * @return the supportsPositionedDelete
	 */
	public boolean isSupportsPositionedDelete() {
		return supportsPositionedDelete;
	}

	/**
	 * @param supportsPositionedDelete
	 *            the supportsPositionedDelete to set
	 */
	public void setSupportsPositionedDelete(boolean supportsPositionedDelete) {
		this.supportsPositionedDelete = supportsPositionedDelete;
	}

	/**
	 * @return the supportsPositionedUpdate
	 */
	public boolean isSupportsPositionedUpdate() {
		return supportsPositionedUpdate;
	}

	/**
	 * @param supportsPositionedUpdate
	 *            the supportsPositionedUpdate to set
	 */
	public void setSupportsPositionedUpdate(boolean supportsPositionedUpdate) {
		this.supportsPositionedUpdate = supportsPositionedUpdate;
	}

	/**
	 * @return the supportsSavepoints
	 */
	public boolean isSupportsSavepoints() {
		return supportsSavepoints;
	}

	/**
	 * @param supportsSavepoints
	 *            the supportsSavepoints to set
	 */
	public void setSupportsSavepoints(boolean supportsSavepoints) {
		this.supportsSavepoints = supportsSavepoints;
	}

	/**
	 * @return the supportsSchemasInDataManipulation
	 */
	public boolean isSupportsSchemasInDataManipulation() {
		return supportsSchemasInDataManipulation;
	}

	/**
	 * @param supportsSchemasInDataManipulation
	 *            the supportsSchemasInDataManipulation to set
	 */
	public void setSupportsSchemasInDataManipulation(
			boolean supportsSchemasInDataManipulation) {
		this.supportsSchemasInDataManipulation = supportsSchemasInDataManipulation;
	}

	/**
	 * @return the supportsSchemasInIndexDefinitions
	 */
	public boolean isSupportsSchemasInIndexDefinitions() {
		return supportsSchemasInIndexDefinitions;
	}

	/**
	 * @param supportsSchemasInIndexDefinitions
	 *            the supportsSchemasInIndexDefinitions to set
	 */
	public void setSupportsSchemasInIndexDefinitions(
			boolean supportsSchemasInIndexDefinitions) {
		this.supportsSchemasInIndexDefinitions = supportsSchemasInIndexDefinitions;
	}

	/**
	 * @return the supportsSchemasInPrivilegeDefinitions
	 */
	public boolean isSupportsSchemasInPrivilegeDefinitions() {
		return supportsSchemasInPrivilegeDefinitions;
	}

	/**
	 * @param supportsSchemasInPrivilegeDefinitions
	 *            the supportsSchemasInPrivilegeDefinitions to set
	 */
	public void setSupportsSchemasInPrivilegeDefinitions(
			boolean supportsSchemasInPrivilegeDefinitions) {
		this.supportsSchemasInPrivilegeDefinitions = supportsSchemasInPrivilegeDefinitions;
	}

	/**
	 * @return the supportsSchemasInProcedureCalls
	 */
	public boolean isSupportsSchemasInProcedureCalls() {
		return supportsSchemasInProcedureCalls;
	}

	/**
	 * @param supportsSchemasInProcedureCalls
	 *            the supportsSchemasInProcedureCalls to set
	 */
	public void setSupportsSchemasInProcedureCalls(
			boolean supportsSchemasInProcedureCalls) {
		this.supportsSchemasInProcedureCalls = supportsSchemasInProcedureCalls;
	}

	/**
	 * @return the supportsSchemasInTableDefinitions
	 */
	public boolean isSupportsSchemasInTableDefinitions() {
		return supportsSchemasInTableDefinitions;
	}

	/**
	 * @param supportsSchemasInTableDefinitions
	 *            the supportsSchemasInTableDefinitions to set
	 */
	public void setSupportsSchemasInTableDefinitions(
			boolean supportsSchemasInTableDefinitions) {
		this.supportsSchemasInTableDefinitions = supportsSchemasInTableDefinitions;
	}

	/**
	 * @return the supportsSelectForUpdate
	 */
	public boolean isSupportsSelectForUpdate() {
		return supportsSelectForUpdate;
	}

	/**
	 * @param supportsSelectForUpdate
	 *            the supportsSelectForUpdate to set
	 */
	public void setSupportsSelectForUpdate(boolean supportsSelectForUpdate) {
		this.supportsSelectForUpdate = supportsSelectForUpdate;
	}

	/**
	 * @return the supportsStatementPooling
	 */
	public boolean isSupportsStatementPooling() {
		return supportsStatementPooling;
	}

	/**
	 * @param supportsStatementPooling
	 *            the supportsStatementPooling to set
	 */
	public void setSupportsStatementPooling(boolean supportsStatementPooling) {
		this.supportsStatementPooling = supportsStatementPooling;
	}

	/**
	 * @return the supportsStoredProcedures
	 */
	public boolean isSupportsStoredProcedures() {
		return supportsStoredProcedures;
	}

	/**
	 * @param supportsStoredProcedures
	 *            the supportsStoredProcedures to set
	 */
	public void setSupportsStoredProcedures(boolean supportsStoredProcedures) {
		this.supportsStoredProcedures = supportsStoredProcedures;
	}

	/**
	 * @return the supportsSubqueriesInComparisons
	 */
	public boolean isSupportsSubqueriesInComparisons() {
		return supportsSubqueriesInComparisons;
	}

	/**
	 * @param supportsSubqueriesInComparisons
	 *            the supportsSubqueriesInComparisons to set
	 */
	public void setSupportsSubqueriesInComparisons(
			boolean supportsSubqueriesInComparisons) {
		this.supportsSubqueriesInComparisons = supportsSubqueriesInComparisons;
	}

	/**
	 * @return the supportsSubqueriesInExists
	 */
	public boolean isSupportsSubqueriesInExists() {
		return supportsSubqueriesInExists;
	}

	/**
	 * @param supportsSubqueriesInExists
	 *            the supportsSubqueriesInExists to set
	 */
	public void setSupportsSubqueriesInExists(boolean supportsSubqueriesInExists) {
		this.supportsSubqueriesInExists = supportsSubqueriesInExists;
	}

	/**
	 * @return the supportsSubqueriesInIns
	 */
	public boolean isSupportsSubqueriesInIns() {
		return supportsSubqueriesInIns;
	}

	/**
	 * @param supportsSubqueriesInIns
	 *            the supportsSubqueriesInIns to set
	 */
	public void setSupportsSubqueriesInIns(boolean supportsSubqueriesInIns) {
		this.supportsSubqueriesInIns = supportsSubqueriesInIns;
	}

	/**
	 * @return the supportsSubqueriesInQuantifieds
	 */
	public boolean isSupportsSubqueriesInQuantifieds() {
		return supportsSubqueriesInQuantifieds;
	}

	/**
	 * @param supportsSubqueriesInQuantifieds
	 *            the supportsSubqueriesInQuantifieds to set
	 */
	public void setSupportsSubqueriesInQuantifieds(
			boolean supportsSubqueriesInQuantifieds) {
		this.supportsSubqueriesInQuantifieds = supportsSubqueriesInQuantifieds;
	}

	/**
	 * @return the supportsTableCorrelationNames
	 */
	public boolean isSupportsTableCorrelationNames() {
		return supportsTableCorrelationNames;
	}

	/**
	 * @param supportsTableCorrelationNames
	 *            the supportsTableCorrelationNames to set
	 */
	public void setSupportsTableCorrelationNames(
			boolean supportsTableCorrelationNames) {
		this.supportsTableCorrelationNames = supportsTableCorrelationNames;
	}

	/**
	 * @return the supportsTransactions
	 */
	public boolean isSupportsTransactions() {
		return supportsTransactions;
	}

	/**
	 * @param supportsTransactions
	 *            the supportsTransactions to set
	 */
	public void setSupportsTransactions(boolean supportsTransactions) {
		this.supportsTransactions = supportsTransactions;
	}

	/**
	 * @return the supportsUnion
	 */
	public boolean isSupportsUnion() {
		return supportsUnion;
	}

	/**
	 * @param supportsUnion
	 *            the supportsUnion to set
	 */
	public void setSupportsUnion(boolean supportsUnion) {
		this.supportsUnion = supportsUnion;
	}

	/**
	 * @return the supportsUnionAll
	 */
	public boolean isSupportsUnionAll() {
		return supportsUnionAll;
	}

	/**
	 * @param supportsUnionAll
	 *            the supportsUnionAll to set
	 */
	public void setSupportsUnionAll(boolean supportsUnionAll) {
		this.supportsUnionAll = supportsUnionAll;
	}

	/**
	 * @return the usesLocalFilePerTable
	 */
	public boolean isUsesLocalFilePerTable() {
		return usesLocalFilePerTable;
	}

	/**
	 * @param usesLocalFilePerTable
	 *            the usesLocalFilePerTable to set
	 */
	public void setUsesLocalFilePerTable(boolean usesLocalFilePerTable) {
		this.usesLocalFilePerTable = usesLocalFilePerTable;
	}

	/**
	 * @return the usesLocalFiles
	 */
	public boolean isUsesLocalFiles() {
		return usesLocalFiles;
	}

	/**
	 * @param usesLocalFiles
	 *            the usesLocalFiles to set
	 */
	public void setUsesLocalFiles(boolean usesLocalFiles) {
		this.usesLocalFiles = usesLocalFiles;
	}

}
