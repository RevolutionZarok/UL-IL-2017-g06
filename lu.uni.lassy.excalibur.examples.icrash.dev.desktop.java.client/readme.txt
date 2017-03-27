#-------------------------------------------------------------------------------
# Copyright (c) 2014-2016 University of Luxembourg.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
# 
# Contributors:
#     Alfredo Capozucca - initial API and implementation
#	  Thomas Mortimer - Updated with new main location and the Run client.launch
#-------------------------------------------------------------------------------

iCrash Java Distributed Development 
===================================

What Is This?
-------------
This Java project contains the CLIENT side implementation of the iCrash case study
specified according to the Messir methodology and using the Excalibur tool.



Prerequisites
--------------------------
1. Install the iCrash Java SERVER side implementation.
2. Launch the iCrash server.



How To Install The Project
--------------------------
1. Import the CLIENT iCrash Java Development project into your workspace. 
2. Build the project (if option Project -> Build Automatically has not been selected) 




How To Execute The Project?
--------------------------
This execution mode gives the user a Control Simulation Panel from where he can monitor the system and its environment.
The GUI also allows the user to behave as any of the system-related actors such that he can perform any of the 
respective actor-related operations. Thus, the user may decide on the fly the scenario to be simulate.
To launch the GUI mode you should:

1.Open the class Main contained in the package 
lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.creator

2. Right-click on the class and then select Run as -> Java Application

OR

1. In the source folder, right click on RunClient.launch
2. Select Run As> run client





Admin credentials?
--------------------------
There exists a built-in user called "admin". Its credentials are:
user name: icrashadmin
password: 7WXC1359
 
This user allows to create the other system users who are known as "Coordinators".


 
