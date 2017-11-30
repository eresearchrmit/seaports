# Climate Smart Seaports

v1.0

## 1. Introduction

The Climate Smart Seaports Tool enables interested users to begin the process of a climate risk assessment. It assists them to identify current and historical climate trends and variability, as well as future climate projections under a variety of scenarios. Population and trade data is included, and users can add port-specific information to round out their analysis.

This tool is a web application that provides a portal for Seaports authorities to gather data related from various sources and put them together into reports. This is achieved by guiding the user through a risk assessment driven workflow.

For more information, please refer to the full user documentation, available at:  
[https://github.com/eresearchrmit/seaports-pacific/blob/master/doc/User Documentation - Climate Smart Seaports - v1.0.pdf](https://github.com/eresearchrmit/seaports/blob/master/doc/User%20Documentation%20-%20Climate%20Smart%20Seaports%20-%20v1.0.pdf)


## 2. Getting Started

Installation guide available at:  
[https://github.com/eresearchrmit/seaports-pacific/blob/master/doc/Installation Guide - AP35.docx](https://github.com/eresearchrmit/seaports/blob/master/doc/Installation%20Guide%20-%20AP35.docx)

Developer Documentation available at:  
[https://github.com/eresearchrmit/seaports-pacific/blob/master/doc/Developer Documentation - AP35.docx](https://github.com/eresearchrmit/seaports/blob/master/doc/Developer%20Documentation%20-%20AP35.docx)

User Documentation PDF available at:  
[https://github.com/eresearchrmit/seaports-pacific/blob/master/doc/climate-smart-seaports-guidelines.pdf](https://github.com/eresearchrmit/seaports/blob/master/doc/climate-smart-seaports-guidelines.pdf)

### a. Get the database ready

Make sure you have MySQL installed and running.

Create the "seaports" and "seaports\_test" databases and fill them with the data. "Ready-to-use" SQL dump files are available under: *src/main/java/database/seaports_dump.sql* and *src/main/java/database/seaports_test_dump.sql*

You can also run the "main" methods in the Java files under "src/main/java/database" to programatically load the data in the databases (make sure you edit the files "hibernate.cfg.xml" and "hibernate-test.cfg.xml" to add your databases credentials before using these Java loading files).

### b. Build the application

The application uses Maven ([http://maven.apache.org/](http://maven.apache.org/)) to manage its build.  
Make sure Maven is installed and working before building the application. 

You can build the application by invoking the command `mvn install` in your terminal, from the root of the project (where the file *pom.xml* is located).

### c. Deploy the application

The application uses [Maven](http://maven.apache.org/) and [Jetty](http://www.eclipse.org/jetty/) to deploy the application.  
Make sure they are installed before deploying the application.

You can deploy the application by invoking the command `mvn -Djetty.port=8080 jetty:run` in your terminal, from the root of the project (where the file *pom.xml* is located).

## 3. License

This software is distributed under the 3-Clause BSD license.  
Please see the file [LICENSE.txt](https://github.com/eresearchrmit/seaports/blob/master/LICENSE.txt) for more details.

## 4. Dependencies

**Please consult the file "LICENSE.txt" for details about the license of each 
dependency.**

The application uses the following dependencies:

#### JAVA LIBRARIES:
- Spring: [http://www.springsource.org/](http://www.springsource.org/)
- Spring Security: [http://www.springsource.org/spring-security](http://www.springsource.org/spring-security)
- Hibernate: [http://www.hibernate.org/](http://www.hibernate.org/)
- Apache Tiles: [http://tiles.apache.org/](http://tiles.apache.org/)
- Apache POI: [http://poi.apache.org/](http://poi.apache.org/)
- RIF-CS Java API: [http://ands.org.au/resource/downloads.html](http://ands.org.au/resource/downloads.html)

#### JAVASCRIPT LIBRARIES:
- jQuery: [http://jquery.com](http://jquery.com)
- jQuery plugin - Zebra_Tooltip: [http://stefangabos.ro/jquery/zebra-tooltips](http://stefangabos.ro/jquery/zebra-tooltips)
- jQuery plugin - maphighlight: [https://github.com/kemayo/maphilight](https://github.com/kemayo/maphilight)
- jQuery plugin - dataTables: [http://www.datatables.net/](http://www.datatables.net/)
- Highcharts: [http://www.highcharts.com/](http://www.highcharts.com/)
- Tiny MCE: [http://www.tinymce.com/](http://www.tinymce.com/)

