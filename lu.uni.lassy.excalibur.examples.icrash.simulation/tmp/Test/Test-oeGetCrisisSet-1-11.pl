%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/11.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,11]).
%-------------------------------------------
msrTest([[system, sim, 1,11],
[[target, oeGetCrisisSet],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 11

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),

%% Actor Declaration
msrVar(actCoordinator, TheActor),

%% Input Parameter Declaration
msrVar(etCrisisStatus, AetCrisisStatus),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp1),
msrNav(CollTemp1, msrAny, msrTrue, [TheActor]),

%% Input Parameter Specification
msrNav([AetCrisisStatus],[],[[etCrisisStatus, pending]]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeGetCrisisSet, [AetCrisisStatus]],
	[Result]
	], !,
GoalGet=..[Target | ParameterList],

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
Context = [['TheSystem', TheSystem],['TheActor',TheActor] ],
InputParameters = [['AetCrisisStatus' ,AetCrisisStatus]],
OutputParameters = [],
Comments = ''
.
