%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/9.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,9]).
%-------------------------------------------
msrTest([[system, sim, 1,9],
[[target, oeSollicitateCrisisHandling],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 9

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),

%% Actor Declaration
msrVar(actActivator, TheActor),

%% Input Parameter Declaration
%% N.A.

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactActivator,msrAny,msrTrue],[TheActor]),

%% Input Parameter Specification
%% N.A.

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeSollicitateCrisisHandling, []],
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
InputParameters = [],
OutputParameters = [],
Comments = ''
.
