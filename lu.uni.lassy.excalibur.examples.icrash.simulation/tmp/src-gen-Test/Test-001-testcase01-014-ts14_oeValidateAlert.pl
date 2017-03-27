%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/14.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,14]).
%-----------------------------------------------
msrTest([[system, sim, 1,14],
[[target, oeValidateAlert],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 14

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(dtAlertID, AdtAlertID),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp4),
msrNav(CollTemp4, msrAny, msrTrue, [TheActor]),
msrNav([AdtAlertID],[value],[[ptString,'1']]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeValidateAlert, [AdtAlertID]],
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
Parameters = [['TheActor' ,TheActor],['AdtAlertID' ,AdtAlertID],['AMessage' ,AMessage]],
OutputParameters = [],
Comments = ''
.
