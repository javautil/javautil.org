
  CREATE TABLE UT_SCHEMA_GRP
   (
	UT_SCHEMA_GRP_NBR NUMBER(9),
	SCHEMA_GRP_NM VARCHAR2(16),
	SCHEMA_GRP_DESCR VARCHAR2(30),
	 CONSTRAINT UT_SCHEMA_GRP_PK PRIMARY KEY (UT_SCHEMA_GRP_NBR),
	 CONSTRAINT UT_SCHEMA_GRP_UK UNIQUE (SCHEMA_GRP_NM)
   );


