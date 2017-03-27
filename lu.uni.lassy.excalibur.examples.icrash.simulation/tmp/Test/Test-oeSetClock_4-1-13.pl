%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/13.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,13]).
%-------------------------------------------
msrTest([[system, sim, 1,13],
[[target, oeSetClock],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 13

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),

%% Actor Declaration
msrVar(actActivator, TheActor),

%% Input Parameter Declaration
msrVar(dtDateAndTime, ACurrentClock),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactActivator,msrAny,msrTrue],[TheActor]),

%% Input Parameter Specification
msrNav([ACurrentClock],[date, year, value],[[ptInteger,2017]]),
msrNav([ACurrentClock],[date, month, value],[[ptInteger,11]]),
msrNav([ACurrentClock],[date, day, value],[[ptInteger,26]]),
msrNav([ACurrentClock],[time, hour, value],[[ptInteger,10]]),
msrNav([ACurrentClock],[time, minute, value],[[ptInteger,45]]),
msrNav([ACurrentClock],[time, second, value],[[ptInteger,0]]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeSetClock, [ACurrentClock]],
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
InputParameters = [['ACurrentClock' ,ACurrentClock]],
OutputParameters = [],
Comments = ''
.
