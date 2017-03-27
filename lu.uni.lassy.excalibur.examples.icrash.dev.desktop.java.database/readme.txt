#-------------------------------------------------------------------------------
# Copyright (c) 2014-2016 University of Luxembourg.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
# 
# Contributors:
#     Alfredo Capozucca - initial API and implementation
#-------------------------------------------------------------------------------

iCrash Java Distributed Development 
===================================

What Is This?
-------------
This Java project contains the DATABASE layer that allows the iCrash case study's data to be
stored in a MySQL database.


Prerequisites
--------------------------
1. Install MySql Community Server v5.5.x (or higher)
1.a Download from http://dev.mysql.com/downloads/ and install it.

2. Open a terminal and execute the following commands:
2.1 cd  [PATH_TO_THE_ICRASH_DATABASE_JAVA_PROJECT]/scripts
2.2 mysql -u root -p  < icrash_db_create.sql

Note: to be sure that mysql service is running and it's on your PATH.



How To Install The Project
--------------------------
1. Import the COMMONS iCrash Java Development project into your workspace. 
2. Import the DATABASE iCrash Java Development project into your workspace. 
3. Build the project (if option Project -> Build Automatically has not been selected) 




What's next?
----------------------------------
The project DATABASE contains several simple examples meant to allow the user to test whether 
the database engine and the Java projects have been properly set up. Such test cases are placed
into the package:

lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases


To execute each of these tests you should:

1. Open the class respective to the test of interest contained in the the package 
lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases

2. Right-click on the class and then select Run as -> Java Application

Below, the test cases that have been implemented:
1. test if the database has been properly set-up: class TestCase_db_alive
2. test insert/select/delete SQL statements on the Alerts table of the icrash DB.  
3. test insert/select/delete SQL statements on the Crises table of the icrash DB.
4. test insert/select/delete SQL statements on the Humans table of the icrash DB.




 
