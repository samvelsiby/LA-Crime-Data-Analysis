
build: SQLServer.class FinalProject.class

SQLServer.class: SQLServer.java
	javac SQLServer.java

FinalProject.class: FinalProject.java
	javac FinalProject.java

run: SQLServer.class
	java -cp .:mssql-jdbc-11.2.0.jre11.jar SQLServer

run-FinalProject: FinalProject.class
	java -cp .:mssql-jdbc-11.2.0.jre18.jar FinalProject

execute: run-FinalProject

clean:
	rm SQLServer.class FinalProject.class