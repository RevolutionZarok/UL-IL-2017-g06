%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/4.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,4]).
%-----------------------------------------------
msrTest([[system, sim, 1,4],
[[target, oeAddCoordinator],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 4

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actAdministrator, TheActor),
msrVar(dtCoordinatorID, AdtCoordinatorID),
msrVar(dtLogin, AdtLogin),
msrVar(dtPassword, AdtPassword),

%% Constraints

theSystem(TheSystem),

msrNav([AdtCoordinatorID],[value,eq,[[ptString,'1']]],[[ptBoolean,true]]),
msrNav([AdtLogin],[value,eq,[[ptString,'steve']]],[[ptBoolean,true]]),
msrNav([AdtPassword],[value,eq,[[ptString,'pwdMessirExcalibur2017']]],[[ptBoolean,true]]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeAddCoordinator, [AdtCoordinatorID,AdtLogin,AdtPassword]],
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
Parameters = [['TheActor' ,TheActor],['AdtCoordinatorID' ,AdtCoordinatorID],['AdtLogin' ,AdtLogin],['AdtPassword' ,AdtPassword]],
OutputParameters = [],
Comments = ''
.
