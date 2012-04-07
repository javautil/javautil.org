set echo on
spool bank
create table account (
	account_nbr number(18),
	account_type varchar2(3)
);

alter table account add constraint account_type_ck 
check (account_type in ('DDA','SAV'));

alter table account add constraint account_pk  primary key (account_nbr);

create table dda_account
(
	dda_account_id varchar2(18),
	account_nbr number(18),
	name   varchar2(30),
	addr_1 varchar2(30),
	addr_2 varchar2(30),
	city   varchar2(30),
	state  varchar2(2),
	zip    varchar2(9)	
);

alter table dda_account add constraint dda_account_pk
primary key (dda_account_id);

alter table dda_account add constraint da_a_fk
foreign key (account_nbr) references account(account_nbr);

create table indiv 
(
	indiv_nbr number(18) not null,
	first_name varchar2(20) not null,
	last_name  varchar2(20) not null,
	ssn        varchar2(10)
);

alter table indiv add constraint indiv_pk primary key (indiv_nbr);

create table account_indiv
(
	account_indiv_nbr number(18),
	account_nbr number(18),
	indiv_nbr number(18)
);	

alter table account_indiv add constraint account_indiv_pk 
primary key (account_indiv_nbr);

alter table account_indiv add constraint ai_i_fk 
foreign key (indiv_nbr) references indiv(indiv_nbr);

alter table account_indiv add constraint ai_a_fk
foreign key (account_nbr) references account(account_nbr);
		
