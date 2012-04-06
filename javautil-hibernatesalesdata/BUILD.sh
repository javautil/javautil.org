USER=sales
PASSWORD=tutorial
URL=jdbc:h2:database/sales

echodo() {
	echo $*
	$*
}

create-database-schema() {
	echodo	h2 script -user $USER -password $PASSWORD -url $URL -script src/main/resources/ddl/h2/sales_schema.sql
}

create-database-schema
