#-------------------------------------------------------------------------------
# Copyright (c) 2014-2016 University of Luxembourg.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
# 
# Contributors:
#     Alfredo Capozucca - initial API and implementation
#	  Thomas Mortimer - Updated with new LaunchServer.launch file
#-------------------------------------------------------------------------------

iCrash Java Distributed Development 
===================================

What Is This?
-------------
This Java project contains the SERVER side implementation of the iCrash case study
specified according to the Messir methodology and using the Excalibur tool.



Prerequisites
--------------------------
1. Install the DATABASE iCrash Java project as indicated in its readme file.


How To Install The Project
--------------------------
1. Import the SERVER iCrash Java Development project into your workspace. 
2. Build the project (if option Project -> Build Automatically has not been selected) 





How To Execute The SERVER Project?
----------------------------------
A) Launching the server
The package lu.uni.lassy.excalibur.examples.icrash.dev.java.main
contains the LaunchServer class, which is in charge of starting the iCrash server.
Once, the server has been started, it's ready to receive requests coming from the client side application. 

To execute this you should:

1. Open the class LaunchServer contained in the package 
lu.uni.lassy.excalibur.examples.icrash.dev.java.main

2. Right-click on the class and then select Run as -> Java Application

OR

1. In the source folder, right click on LaunchServerWithoutArgument.launch
2. Select Run As> LaunchServer

IMPORTANT REMARK!!!!!: if you use LaunchServerWithArgument.launch to start the server, your database 
will be full erased.


B) Checking whether the server is up
The package lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases
contains a simple test case meant to test whether the server is ready to receive requests.

To execute this test case you should:

1. Open the class TestCase_server_alive contained in the the package 
lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases

2. Right-click on the class and then select Run as -> Java Application
