# extjs6-practicum
Working with the ExtJS 6 framework

CONFIGURATION FILE
The configuration is set by passing a JVM argument:
	-Dconfig=<config-file.properties>

The configuration file required by the web application should have these properties:
# Database
mysql.driver=com.mysql.jdbc.Driver
mysql.url=jdbc:mysql://localhost:3306/extjs_practicum
mysql.username=<username>
mysql.password=<password>
# Hibernate
hibernate.format_sql=true
hibernate.show_sql=true
hibernate.generate_statistics=true
hibernate.dialect=org.hibernate.dialect.MySQLDialect
