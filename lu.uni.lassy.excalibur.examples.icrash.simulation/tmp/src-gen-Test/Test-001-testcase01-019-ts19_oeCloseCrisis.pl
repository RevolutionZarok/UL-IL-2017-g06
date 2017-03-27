%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/19.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,19]).
%-----------------------------------------------
msrTest([[system, sim, 1,19],
[[target, oeCloseCrisis],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 19

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(dtCrisisID, AdtCrisisID),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp7),
msrNav(CollTemp7, msrAny, msrTrue, [TheActor]),
msrNav([AdtCrisisID],[value],[[ptString,'1']]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeCloseCrisis, [AdtCrisisID]],
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
Parameters = [['TheActor' ,TheActor],['AdtCrisisID' ,AdtCrisisID],['AMessage' ,AMessage]],
OutputParameters = [],
Comments = ''
.
