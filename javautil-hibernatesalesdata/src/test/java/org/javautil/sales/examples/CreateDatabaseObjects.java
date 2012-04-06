/**
 * 
 */
package org.javautil.sales.examples;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.h2.jdbc.JdbcSQLException;

/**
 * 
 */
public class CreateDatabaseObjects {

	private final Logger logger = Logger.getLogger(getClass());

	String dropProductEtl = "drop table product_etl ";

	String dropProduct = "drop table product  ";

	String dropProductSequence = "drop sequence product_seq ";

	String dropCustomerSequence = "drop sequence customer_seq ";

	String dropCustomer = "drop table customer ";

	String dropSale = "drop table sale ";

	String dropSaleSequence = "drop sequence sale_seq ";

	String dropSalesPerson = "drop table salesperson ";

	String dropSalesPersonSequence = "drop sequence salesperson_seq ";

	String dropCustomerSaleProduct = "drop view customer_sale_product ";

	String dropGtt = "drop table gtt_number ";

	String createProductEtl = "create table product_etl " + "( "
			+ "	product_etl_id    number(9) not null, "
			+ "	upc10          varchar2(10), "
			+ "	product_status varchar2(1), "
			+ "	descr          varchar2(50), " + "	narrative      clob " + ")";

	String createProduct = "create table product  " + "( "
			+ "	product_id    number(9) not null, "
			+ "	upc10          varchar2(10) not null, "
			+ "	product_status varchar2(1) not null, "
			+ "	descr          varchar2(50), " + "	narrative      clob " + ") ";

	String createProductComment1 = "comment on table product is  "
			+ "'Product Master ";

	String createProductComment2 = "comment on column product.upc10 is '10 digit Universal Product Code, the American equivalent of the EAN' ";

	String createProductComment3 = "comment on column product.status is  "
			+ "'The status of the item.  " + " A - Active " + " S - Setup "
			+ " I - Inactive  ";

	String createProductComment4 = "comment on column product.upc10 is  "
			+ "' Why is this varchar2 if it is a number?' ";

	String createProductConstraint1 = "alter table product add constraint product_pk  "
			+ "primary key (product_id) ";

	String createProductConstraint2 = "alter table product add constraint product_uq unique (upc10) ";

	String createProductSequence = "create sequence product_seq cache 1000 ";

	String createCustomerSequence = "create sequence customer_seq cache  100 ";

	String createCustomer = "create table customer " + "( "
			+ "	customer_id number(9) not null, "
			+ "	customer_status varchar2(1), " + "	name         varchar2(30), "
			+ "	addr_1       varchar2(30), " + "	addr_2       varchar2(30), "
			+ "	city         varchar2(25), " + "	state        varchar2(2), "
			+ "	zip_cd       varchar2(10), "
			+ "	outside_salesperson_id number(9), "
			+ "	inside_salesperson_id  number(9)  not null " + ") ";

	String createCustomerConstraint1 = "alter table customer add  constraint customer_pk primary key "
			+ "(customer_id)  ";

	String createCustomerConstraint2 = "alter table customer add constraint c_s_fk foreign key "
			+ "(inside_salesperson_id) references salesperson(salesperson_id)  ";

	String createCustomerIndex1 = "create bitmap index customer_ak1 on customer(outside_salesperson_id) ";

	String createCustomerIndex2 = "create bitmap index customer_ak2 on customer(inside_salesperson_id) ";

	String createSale = "create table sale " + "( "
			+ "	sale_id    number(18) not null, "
			+ "	ship_dt     date not null, "
			+ "	qty         number(13,5) not null, "
			+ "	product_id number(9) not null,	 "
			+ "	customer_id number(9) not null " + ") ";

	String createSaleSequence = "create sequence sale_seq cache 1000 ";

	String createSaleConstraint1 = "alter table sale add constraint sales_pk primary key (sale_id) ";

	String createSaleConstraint2 = "alter table sale  "
			+ "add constraint s_c_fk  " + "foreign key (customer_id)  "
			+ "references customer(customer_id) ";

	String createSaleConstraint3 = "alter table sale  "
			+ "add constraint s_p_fk  " + "foreign key (product_id)  "
			+ "references product(product_id) ";

	String createSalesPerson = "create table salesperson " + "( "
			+ "	salesperson_id number(9) not null, "
			+ "	display_name   varchar2(40), "
			+ "	first_name     varchar2(16), "
			+ "	last_name      varchar2(20) " + ") ";

	String createSalesPersonConstraint1 = "alter table salesperson add constraint salesperson_pk  "
			+ "primary key (salesperson_id) ";

	String createSalesPersonSequence = "create sequence salesperson_seq cache 100 ";

	String createCustomerSaleProduct = "create view customer_sale_product "
			+ "as " + "select  " + "	c.name, " + "	c.addr_1, " + "	c.addr_2, "
			+ "	c.city, " + "	c.state, " + "	c.zip_cd, " + "	p.upc10,         "
			+ "	p.product_status,   " + "	p.descr         product_descr,  "
			+ "	p.narrative,      "
			+ "	sp.salesperson_id inside_salesperson_id,  "
			+ "	sp.display_name inside_rep__display_name, "
			+ "	sp.first_name   inside_rep_first_name, "
			+ "	sp.last_name    inside_rep_last_name, " + "	s.sale_id, "
			+ "	s.ship_dt, " + "	s.qty, " + "	s.product_id, "
			+ "	s.customer_id " + "from  " + "	customer c, " + "	product p, "
			+ "	salesperson sp, " + "	sale s  "
			+ "where   c.inside_salesperson_id = sp.salesperson_id(+) and "
			+ "	s.product_id = p.product_id and "
			+ "	c.customer_id = s.customer_id ";

	String createCustomerSaleProductConstraint1 = "alter view  customer_sale_product "
			+ "add constraint customer_sale_product_pk "
			+ "primary key (sale_id) disable novalidate  ";

	String createGtt = "create global temporary table gtt_number " + "( "
			+ "	nbr number " + ") on commit delete rows ";

	public void dropObjects(final Connection conn) throws SQLException {
		dropObject(conn, dropProductEtl);
		dropObject(conn, dropProduct);
		dropObject(conn, dropProductSequence);
		dropObject(conn, dropCustomerSequence);
		dropObject(conn, dropCustomer);
		dropObject(conn, dropSale);
		dropObject(conn, dropSaleSequence);
		dropObject(conn, dropSalesPerson);
		dropObject(conn, dropSalesPersonSequence);
		dropObject(conn, dropCustomerSaleProduct);
		dropObject(conn, dropGtt);
	}

	public void createObjects(final Connection conn) throws SQLException {
		createObject(conn, createProductEtl);
		createObject(conn, createProduct);
		// createObject(conn, createProductComment1);
		// createObject(conn, createProductComment2);
		// createObject(conn, createProductComment3);
		// createObject(conn, createProductComment4);
		createObject(conn, createProductConstraint1);
		createObject(conn, createProductConstraint2);
		createObject(conn, createProductSequence);
		createObject(conn, createCustomerSequence);
		createObject(conn, createSalesPerson);
		createObject(conn, createSalesPersonConstraint1);
		createObject(conn, createSalesPersonSequence);
		createObject(conn, createCustomer);
		createObject(conn, createCustomerConstraint1);
		createObject(conn, createCustomerConstraint2);
		// createObject(conn, createCustomerIndex1);
		// createObject(conn, createCustomerIndex2);
		createObject(conn, createSale);
		createObject(conn, createSaleSequence);
		createObject(conn, createSaleConstraint1);
		createObject(conn, createSaleConstraint2);
		createObject(conn, createSaleConstraint3);
		createObject(conn, createCustomerSaleProduct);
		// createObject(conn, createCustomerSaleProductConstraint1);
		createObject(conn, createGtt);
	}

	private void createObject(final Connection conn, final String sql)
			throws SQLException {
		final Statement stmt = conn.createStatement();
		stmt.execute(sql);
		stmt.close();
	}

	private void dropObject(final Connection conn, final String sql)
			throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			stmt.execute(sql);
		} catch (final JdbcSQLException j) {
			logger.debug("Unable to execute " + sql);
		}
		stmt.close();
	}

}
