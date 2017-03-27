%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/9.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,9]).
%-----------------------------------------------
msrTest([[system, sim, 1,9],
[[target, oeSollicitateCrisisHandling],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 9

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actActivator, TheActor),
msrVar(actAdministrator, TheAdministrator),
msrVar(actCoordinator, TheCoordinator),
msrVarCol(actCoordinator, _ , AColactCoordinator),
msrVar(dtComment, AMessageForCrisisHandlers),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),


%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeSollicitateCrisisHandling, []],
	  [Result]
	],
	!,
GoalGet=..[Target | ParametersList],

%% Oracle specification
OracleGet=..[true]
)
->
%% Test Interpretation
((GoalGet,!)
-> ((OracleGet,!)
	-> TestResult = [success]
	; TestResult = [failedAtOracle])
; TestResult = [failedAtGoal]
)
; TestResult = [failedAtTestDeclarationOrSpecification]
),
%% Test Outcome
Context = [['TheSystem', TheSystem]],
Parameters = [['TheActor' ,TheActor],['TheAdministrator' ,TheAdministrator],['TheCoordinator' ,TheCoordinator],['AColactCoordinator' ,AColactCoordinator],['AMessageForCrisisHandlers' ,AMessageForCrisisHandlers],['AMessage' ,AMessage]],
OutputParameters = [],
Comments = ''
.
