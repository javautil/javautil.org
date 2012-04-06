
    alter table CUSTOMER 
        drop constraint FK52C76FDE4DEAED2C;

    alter table SALE 
        drop constraint FK26B847113F4349;

    alter table SALE 
        drop constraint FK26B84765E2CEEB;

    drop table CUSTOMER if exists;

    drop table CUSTOMER_SALE_PRODUCT if exists;

    drop table GTT_NUMBER if exists;

    drop table PRODUCT if exists;

    drop table PRODUCT_ETL if exists;

    drop table SALE if exists;

    drop table SALESPERSON if exists;

    drop sequence CUSTOMER_SEQ;

    drop sequence GTT_NUMBER_SEQ;

    drop sequence PRODUCT_SEQ;

    drop sequence SALESPERSON_SEQ;

    drop sequence SALE_SEQ;

    create table CUSTOMER (
        CUSTOMER_ID integer not null,
        INSIDE_SALESPERSON_ID integer not null,
        CUSTOMER_STATUS varchar(1),
        NAME varchar(30),
        ADDR_1 varchar(30),
        ADDR_2 varchar(30),
        CITY varchar(25),
        STATE varchar(2),
        ZIP_CD varchar(10),
        OUTSIDE_SALESPERSON_ID integer,
        primary key (CUSTOMER_ID)
    );

    create table CUSTOMER_SALE_PRODUCT (
        NAME varchar(30) not null,
        ADDR_1 varchar(30) not null,
        ADDR_2 varchar(30) not null,
        CITY varchar(25) not null,
        STATE varchar(2) not null,
        ZIP_CD varchar(10) not null,
        UPC10 varchar(10) not null,
        PRODUCT_STATUS varchar(1) not null,
        PRODUCT_DESCR varchar(50) not null,
        NARRATIVE varchar(255) not null,
        INSIDE_SALESPERSON_ID integer not null,
        INSIDE_REP__DISPLAY_NAME varchar(40) not null,
        INSIDE_REP_FIRST_NAME varchar(16) not null,
        INSIDE_REP_LAST_NAME varchar(20) not null,
        SALE_ID bigint not null,
        SHIP_DT date not null,
        QTY numeric not null,
        PRODUCT_ID integer not null,
        CUSTOMER_ID integer not null,
        primary key (NAME, ADDR_1, ADDR_2, CITY, STATE, ZIP_CD, UPC10, PRODUCT_STATUS, PRODUCT_DESCR, NARRATIVE, INSIDE_SALESPERSON_ID, INSIDE_REP__DISPLAY_NAME, INSIDE_REP_FIRST_NAME, INSIDE_REP_LAST_NAME, SALE_ID, SHIP_DT, QTY, PRODUCT_ID, CUSTOMER_ID)
    );

    create table GTT_NUMBER (
        NBR integer not null,
        primary key (NBR)
    );

    create table PRODUCT (
        PRODUCT_ID integer not null,
        UPC10 varchar(10) not null unique,
        PRODUCT_STATUS varchar(1) not null,
        DESCR varchar(50),
        NARRATIVE varchar(255),
        primary key (PRODUCT_ID)
    );

    create table PRODUCT_ETL (
        PRODUCT_ETL_ID integer not null,
        UPC10 varchar(10) not null,
        PRODUCT_STATUS varchar(1) not null,
        DESCR varchar(50) not null,
        NARRATIVE varchar(255) not null,
        primary key (PRODUCT_ETL_ID, UPC10, PRODUCT_STATUS, DESCR, NARRATIVE)
    );

    create table SALE (
        SALE_ID bigint not null,
        PRODUCT_ID integer not null,
        CUSTOMER_ID integer not null,
        SHIP_DT date not null,
        QTY numeric not null,
        primary key (SALE_ID)
    );

    create table SALESPERSON (
        SALESPERSON_ID integer not null,
        DISPLAY_NAME varchar(40),
        FIRST_NAME varchar(16),
        LAST_NAME varchar(20),
        primary key (SALESPERSON_ID)
    );

    alter table CUSTOMER 
        add constraint FK52C76FDE4DEAED2C 
        foreign key (INSIDE_SALESPERSON_ID) 
        references SALESPERSON;

    alter table SALE 
        add constraint FK26B847113F4349 
        foreign key (PRODUCT_ID) 
        references PRODUCT;

    alter table SALE 
        add constraint FK26B84765E2CEEB 
        foreign key (CUSTOMER_ID) 
        references CUSTOMER;

    create sequence CUSTOMER_SEQ;

    create sequence GTT_NUMBER_SEQ;

    create sequence PRODUCT_SEQ;

    create sequence SALESPERSON_SEQ;

    create sequence SALE_SEQ;
