%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/17.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,17]).
%-------------------------------------------
msrTest([[system, sim, 1,17],
[[target, oeSetCrisisStatus],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 17

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),

%% Actor Declaration
msrVar(actCoordinator, TheActor),

%% Input Parameter Declaration
msrVar(dtCrisisID, AdtCrisisID),
msrVar(etCrisisStatus, AetCrisisStatus),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp4),
msrNav(CollTemp4, msrAny, msrTrue, [TheActor]),

%% Input Parameter Specification
msrNav([AdtCrisisID],[value],[[ptString,'1']]),
msrNav([AetCrisisStatus],[],[[etCrisisStatus, solved]]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeSetCrisisStatus, [AdtCrisisID,AetCrisisStatus]],
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
InputParameters = [['AdtCrisisID' ,AdtCrisisID],['AetCrisisStatus' ,AetCrisisStatus]],
OutputParameters = [],
Comments = ''
.
