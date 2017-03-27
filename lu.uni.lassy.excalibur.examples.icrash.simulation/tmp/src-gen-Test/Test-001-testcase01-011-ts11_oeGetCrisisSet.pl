%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/11.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,11]).
%-----------------------------------------------
msrTest([[system, sim, 1,11],
[[target, oeGetCrisisSet],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 11

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(etCrisisStatus, AetCrisisStatus),
msrVar(ctCrisis, ActCrisis),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp2),
msrNav(CollTemp2, msrAny, msrTrue, [TheActor]),
msrNav([AetCrisisStatus],[],[[etCrisisStatus, pending]]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeGetCrisisSet, [AetCrisisStatus]],
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
Parameters = [['TheActor' ,TheActor],['AetCrisisStatus' ,AetCrisisStatus],['ActCrisis' ,ActCrisis]],
OutputParameters = [],
Comments = ''
.
