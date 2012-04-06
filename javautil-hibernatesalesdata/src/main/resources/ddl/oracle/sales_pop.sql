set echo on 
declare
	mfr_part  varchar2(5); -- 
	product_part  varchar2(5); --  
	upc varchar2(10);
 	v_descr varchar2(30);
	--     mfr_product_id varchar2(5);  -- last five digits of upc

	function fiveDigitString return varchar2 is
		retval varchar2(5);
                numval number;	        
	begin
		numval := dbms_random.value(0,99999);
		numval := trunc(numval);
		retval := lpad(to_char(numval),5,'0'); return retval; end; 
	
begin
	dbms_random.seed(1);   --  get the same "random numbers" every time
	dbms_random.seed('toad'); -- todo play with this does it override seed1 ?
	for i in 1 .. 20
        loop
	     mfr_part := fiveDigitString;
	     for j in 1 .. 100
	     loop 
		-- intentionally done this way to contrast to java
	        product_part := fiveDigitString;
		upc := mfr_part || product_part;
				v_descr := dbms_random.string('a',dbms_random.value(10,30));
			insert into product(product_id, upc10, product_status, descr) 
			select
				product_seq.nextval,
				mfr_part || product_part,
                                'A',
				dbms_random.string('a',dbms_random.value(10,30))
			 from dual
			 where not exists (select null from product where upc10 = upc);
            end loop;
	end loop;
end;
