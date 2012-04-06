create sequence ut_area_resp_nbr_seq;

create table ut_area_resp
(
        ut_area_resp_nbr number(9),
        func_area_cd     varchar2(8) not null,
        func_short_descr varchar2(60) not null,
        func_long_descr  clob
);


alter table ut_area_resp add constraint ut_area_resp_pk primary key (ut_area_resp_nbr);

alter table ut_area_resp add constraint ut_area_resp_uk unique (func_area_cd);

comment on  table ut_area_resp is 
'Classification of business areas of functional responsibility.
The primary key should be populated from sequence ut_area_resp_nbr_seq ';


comment on column ut_area_resp.ut_area_resp_nbr is 
'Surrogate primary key';

comment on column ut_area_resp.func_area_cd is 
'Description of functional area for display on reports and screens';

comment on column ut_area_resp.func_short_descr is 
'Short description of the business area of responsibility';

comment on column ut_area_resp.func_long_descr is 
'Long description of the business area of responsibility';
