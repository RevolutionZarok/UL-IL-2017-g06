% File paths for the System under Development

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrFilePath/2.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/* Files */
:-dynamic msrFilePath/2.
:-retractall(msrFilePath(_,_)).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-----------------------------------------------
%for Messim reference Project 
%-----------------------------------------------

msrFilePath(
     messimReferenceProjectBaseFolderPath,
% CHANGE ONLY THE FOLLOWING LINE (leave the quotes)
     '/Volumes/Mac_data/Win_data/data/NG/dvlpt/eclipse/ul.dev/lu.uni.lassy.git.excalibur.standard-libraries/lu.uni.lassy.excalibur.standard.simulation.libraries'
           ).

msrFilePath(
     standardLibrariesSimulationProjectPath,
% CHANGE ONLY THE FOLLOWING LINE (leave the quotes)
     '/Volumes/Mac_data/Win_data/data/NG/dvlpt/eclipse/ul.dev/lu.uni.lassy.git.excalibur.standard-libraries/lu.uni.lassy.excalibur.standard.specification.libraries.simulation'
           ).

msrFilePath(
     messimMakeFilePath,
% CHANGE ONLY THE FOLLOWING LINE (leave the quotes)
     '/Volumes/Mac_data/Win_data/data/NG/dvlpt/eclipse/ul.dev/lu.uni.lassy.git.excalibur.standard-libraries/lu.uni.lassy.excalibur.standard.simulation.libraries/Make/make.pl'
           ).

%-----------------------------------------------
%for Project Base Directories
%-----------------------------------------------

msrFilePath(
     projectIdentifier,
% CHANGE ONLY THE FOLLOWING LINE (leave the quotes)
'lu.uni.lassy.excalibur.examples.icrash'
           ).

msrFilePath(
     projectBaseFolderPath,
% CHANGE ONLY THE FOLLOWING LINE (leave the quotes)
'/Volumes/Mac_data/Win_data/data/NG/dvlpt/eclipse/ul.dev/lu.uni.lassy.git.messir.casestudies.icrash.spec/lu.uni.lassy.excalibur.examples.icrash.simulation'
           ).

msrFilePath(
     latexProjectFolder,
% CHANGE ONLY THE FOLLOWING LINE (leave the quotes)
'/Volumes/Mac_data/Win_data/data/NG/dvlpt/eclipse/ul.dev/lu.uni.lassy.git.messir.casestudies.icrash.spec/lu.uni.lassy.excalibur.examples.icrash.simulation/latex'
           ).

