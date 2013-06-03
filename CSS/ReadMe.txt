Climate Smart Seaports v1.0
---------------------------


1. Introduction
***************

The Climate Smart Seaports Tool enables interested users to begin the process 
of a climate risk assessment. It assists them to identify current and 
historical climate trends and variability, as well as future climate 
projections under a variety of scenarios. Population and trade data is 
included, and users can add port-specific information to round out their 
analysis.

This tool is a web application that provides a portal for Seaports authorities 
to gather data related from various sources and put them together into reports.
This is achieved by guiding the user through a risk assessment driven workflow.

For more information, please refer to the full user documentation, available at
http://


2. Quick start guide
********************

For more information and details about the deployment of this project please 
refer to the developer installation guide, available at:
http://

For more information and details about getting started with development of 
this project, please refer to the developer documentation, available at:
http://

a. Get the database ready
-------------------------
Make sure you have MySQL installed and running.

Create the "seaports" and "seaports_test" databases and fill them with the 
data. "Ready-to-use" SQL dump files are available under:
src/main/java/database/seaports_dump.sql
src/main/java/database/seaports_test_dump.sql

You can also run the "main" methods in the Java files under 
"src/main/java/database" to programatically load the data in the databases
(make sure you edit the files "hibernate.cfg.xml" and "hibernate-test.cfg.xml" 
to add your databases credentials before using these Java loading files).


b. Build the application
------------------------
The tool uses Maven (http://maven.apache.org/) to manage its build. Make sure 
Maven is installed and working before building the application. You can build 
the application by invoking the following command in your terminal, from 
the root of the project (where the file "pom.xml" is located):
"mvn install"


c. Deploy the application
-------------------------
The project uses Maven (http://maven.apache.org/) and Jetty 
(http://www.eclipse.org/jetty/) to deploy the application. Make sure they are 
installed before deploying the application. You can deploy the application by 
invoking the following command in your terminal, from the root of the project 
(where the file "pom.xml" is located):
"mvn -Djetty.port=8080 jetty:run"


3. Dependencies
***************

The application uses the following dependencies:

JAVA LIBRARIES
Spring: http://www.springsource.org/	
Spring Security: http://www.springsource.org/spring-security
Hibernate: http://www.hibernate.org/
Apache Tiles: http://tiles.apache.org/
Apache POI: http://poi.apache.org/
RIF-CS Java API: http://ands.org.au/resource/downloads.html

JAVASCRIPT LIBRARIES:
jQuery: http://jquery.com
jQuery plugin - Zebra_Tooltip: http://stefangabos.ro/jquery/zebra-tooltips
jQuery plugin - maphighlight: https://github.com/kemayo/maphilight
jQuery plugin - dataTables: http://www.datatables.net/	
Highcharts: http://www.highcharts.com/
Tiny MCE: http://www.tinymce.com/

Please consult the file "license.txt" for details about the license of each 
dependency.