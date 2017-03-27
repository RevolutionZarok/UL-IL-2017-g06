%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/16.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,16]).
%-----------------------------------------------
msrTest([[system, sim, 1,16],
[[target, oeSetClock],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 16

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actActivator, TheActor),
msrVar(dtDateAndTime, ACurrentClock),

%% Constraints

theSystem(TheSystem),

msrNav([ACurrentClock],[date, year, value],[[ptInteger,2017]]),
msrNav([ACurrentClock],[date, month, value],[[ptInteger,11]]),
msrNav([ACurrentClock],[date, day, value],[[ptInteger,26]]),
msrNav([ACurrentClock],[time, hour, value],[[ptInteger,12]]),
msrNav([ACurrentClock],[time, minute, value],[[ptInteger,45]]),
msrNav([ACurrentClock],[time, second, value],[[ptInteger,0]]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeSetClock, [ACurrentClock]],
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
Parameters = [['TheActor' ,TheActor],['ACurrentClock' ,ACurrentClock]],
OutputParameters = [],
Comments = ''
.
