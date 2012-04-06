
spool sale
set echo on

create table product_etl
(
	product_etl_id    number(9) not null,
	upc10          varchar2(10),
	product_status varchar2(1),
	descr          varchar2(50),
	narrative      clob
);


create table product 
(
	product_id    number(9) not null,
	upc10          varchar2(10) not null,
	product_status varchar2(1) not null,
	descr          varchar2(50),
	narrative      clob
);

comment on table product is 
'Product Master

';

comment on column product.upc10 is '10 digit Universal Product Code, the American equivalent of the EAN';

comment on column product.status is 
'The status of the item. 
 A - Active
 S - Setup
 I - Inactive 

';

alter table product add constraint product_pk 
primary key (product_id);

alter table product add constraint product_uq unique (upc10);

create sequence product_seq cache 1000;
--
--
--

create sequence customer_seq cache  100;

-- todo everything should be nullable until setup
-- having inside_salesperson_id not null allows for a great deal of query optimization
-- therefor this should not be dependent on customer status
-- todo should have a table to hold until actually set up
create table customer
(
	customer_id number(9) not null,
	customer_status varchar2(1),
	name         varchar2(30),
	addr_1       varchar2(30),
	addr_2       varchar2(30),
	city         varchar2(25),
	state        varchar2(2),
	zip_cd       varchar2(10),
	outside_salesperson_id number(9),
	inside_salesperson_id  number(9)  not null
);

-- todo comment every column and what the sequence is
alter table customer add  constraint customer_pk primary key
(customer_id); 


create bitmap index customer_ak1 on customer(outside_salesperson_id);

create bitmap index customer_ak2 on customer(inside_salesperson_id);

comment on column product.upc10 is 
' Why is this varchar2 if it is a number?';

create table sale
(
	sale_id    number(18) not null,
	ship_dt     date not null,
	qty         number(13,5) not null,
        product_id number(9) not null,	
	customer_id number(9) not null
);

create sequence sale_seq cache 1000;

alter table sale add constraint sales_pk primary key (sale_id);

alter table sale 
add constraint s_c_fk 
foreign key (customer_id) 
references customer(customer_id);

alter table sale 
add constraint s_p_fk 
foreign key (product_id) 
references product(product_id);

alter table sale add constraint sale_pk primary key (sale_id);

--
--
--
create table salesperson
(
	salesperson_id number(9),
	display_name   varchar2(40),
	first_name     varchar2(16),
	last_name      varchar2(20)
);

alter table salesperson add constraint salesperson_pk 
primary key (salesperson_id);

create sequence salesperson_seq cache 100;
/*
 alter table customer 
 add constraint c_is_fk 
 foreign key (inside_salesperson_id) 
 references salesperson(salesperson_id);
*/

-- todo restore after figuring out how to get reveng to show reference also

-- alter table customer add constraint c_sp_fk1  foreign key
-- (inside_salesperson_id) references salesperson (salesperson_id);


-- alter table customer add constraint c_sp_fk2  foreign key
-- (outside_salesperson_id) references salesperson (salesperson_id);

create view customer_sale_product
as
select 
	c.name,
	c.addr_1,
	c.addr_2,
	c.city,
	c.state,
	c.zip_cd,
	p.upc10,        
	p.product_status,  
	p.descr         product_descr, 
	p.narrative,     
	sp.salesperson_id inside_salesperson_id, 
	sp.display_name inside_rep__display_name,
	sp.first_name   inside_rep_first_name,
	sp.last_name    inside_rep_last_name,
	s.sale_id,
	s.ship_dt,
	s.qty,
        s.product_id,
	s.customer_id
from 
	customer c,
	product p,
	salesperson sp,
 	sale s 
where   c.inside_salesperson_id = sp.salesperson_id(+) and
	s.product_id = p.product_id and
        c.customer_id = s.customer_id;
       
alter view  customer_sale_product
add constraint customer_sale_product_pk
primary key (sale_id) disable novalidate ;

create global temporary table gtt_number
(
	nbr number
) on commit delete rows;
	       
alter table customer add constraint c_s_fk foreign key
(inside_salesperson_id) references salesperson(salesperson_id); 
