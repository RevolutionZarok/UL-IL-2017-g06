%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/19.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,19]).
%-------------------------------------------
msrTest([[system, sim, 1,19],
[[target, oeCloseCrisis],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 19

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),

%% Actor Declaration
msrVar(actCoordinator, TheActor),

%% Input Parameter Declaration
msrVar(dtCrisisID, AdtCrisisID),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp6),
msrNav(CollTemp6, msrAny, msrTrue, [TheActor]),

%% Input Parameter Specification
msrNav([AdtCrisisID],[value],[[ptString,'1']]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeCloseCrisis, [AdtCrisisID]],
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
InputParameters = [['AdtCrisisID' ,AdtCrisisID]],
OutputParameters = [],
Comments = ''
.
