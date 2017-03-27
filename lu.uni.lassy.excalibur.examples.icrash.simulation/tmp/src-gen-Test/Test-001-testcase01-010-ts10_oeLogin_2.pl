%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/10.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,10]).
%-----------------------------------------------
msrTest([[system, sim, 1,10],
[[target, oeLogin],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 10

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(dtLogin, AdtLogin),
msrVar(dtPassword, AdtPassword),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp1),
msrNav(CollTemp1, msrAny, msrTrue, [TheActor]),
msrNav([AdtLogin],[value,eq,[[ptString,'steve']]],[[ptBoolean,true]]),
msrNav([AdtPassword],[value,eq,[[ptString,'pwdMessirExcalibur2017']]],[[ptBoolean,true]]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeLogin, [AdtLogin,AdtPassword]],
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
Parameters = [['TheActor' ,TheActor],['AdtLogin' ,AdtLogin],['AdtPassword' ,AdtPassword],['AMessage' ,AMessage]],
OutputParameters = [],
Comments = ''
.
