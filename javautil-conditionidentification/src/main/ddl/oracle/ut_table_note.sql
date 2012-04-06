
  CREATE TABLE UT_TABLE_NOTE
   (	TABLE_ID VARCHAR2(8) NOT NULL ENABLE,
	MULT_FLG VARCHAR2(1) NOT NULL ENABLE,
	MOD_FLG VARCHAR2(1) NOT NULL ENABLE,
	DEL_FLG VARCHAR2(1) NOT NULL ENABLE,
	ENTRY_DT_INPUT_FLG VARCHAR2(1) NOT NULL ENABLE,
	ENTRY_USER_INPUT_FLG VARCHAR2(1) NOT NULL ENABLE,
	ENTRY_USER NUMBER(9,0) NOT NULL ENABLE,
	ENTRY_TM DATE NOT NULL ENABLE,
	MOD_USER NUMBER(9,0),
	MOD_TM DATE,
	 CONSTRAINT UT_TABLE_NOTE_PK PRIMARY KEY (TABLE_ID) ENABLE,
	 CONSTRAINT UT_TABLE_NOTE_CHK1 CHECK (MULT_FLG IN ('Y','N')) ENABLE,
	 CONSTRAINT UT_TABLE_NOTE_CHK2 CHECK (MOD_FLG IN ('Y','N')) ENABLE,
	 CONSTRAINT UT_TABLE_NOTE_CHK3 CHECK (DEL_FLG IN ('Y','N')) ENABLE,
	 CONSTRAINT UT_TABLE_NOTE_CHK4 CHECK (ENTRY_DT_INPUT_FLG IN ('Y','N')) ENABLE,
	 CONSTRAINT UT_TABLE_NOTE_CHK5 CHECK (ENTRY_USER_INPUT_FLG IN ('Y','N')) ENABLE,
	 CONSTRAINT UTN_UT_FK FOREIGN KEY (TABLE_ID)
	  REFERENCES UT_TABLE (TABLE_ID) ENABLE
   );


