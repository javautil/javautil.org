DB_URL=jdbc:h2:testdata/dblogging
USER_NAME=dblogging
PASSWORD=toad
#DB_URL=jdbc:h2:tcp://localhost/testdata/dblogging
h2 script -url $DB_URL -user $USER_NAME -password $PASSWORD -script src/main/ddl/h2/Install-h2.sql

