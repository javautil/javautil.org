
insert into UT_ADDR_VALIDATE ( 
	UT_ADDR_VALIDATE_NBR,
	RUN_NBR,
	DATA_SRC_NBR,
	DATA_SRC_PK,
	NAME,
	RAW_ADDR_1,
	RAW_ADDR_2,
	RAW_CITY,
	RAW_STATE_CD,
	RAW_CNTRY_CD,
	RAW_POSTAL_CD    
) select
	ut_addr_validate_seq.nextval,	
	1,
	3,
	4,
	'Mr. Ivy',
        null,
	'6406 Ivy Lane',
        'Greenbelt',
	'MD',
	null,
        null
 from dual;

insert into UT_ADDR_VALIDATE ( 
	UT_ADDR_VALIDATE_NBR,
	RUN_NBR,
	DATA_SRC_NBR,
	DATA_SRC_PK,
	NAME,
	RAW_ADDR_1,
	RAW_ADDR_2,
	RAW_CITY,
	RAW_STATE_CD,
	RAW_CNTRY_CD,
	RAW_POSTAL_CD    
) 
select
	ut_addr_validate_seq.nextval,	
	1,
	3,
	5,
	'Mr. Wild',
        null,
	'8 Wildwood Drive',
	'Old Lyme',
	'CT',
	null,
        '06371'
from dual;

commit;
