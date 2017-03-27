%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,3]).
%-----------------------------------------------
msrTest([[system, sim, 1,3],
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
% Step 3

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actAdministrator, TheActor),
msrVar(dtLogin, AdtLogin),
msrVar(dtPassword, AdtPassword),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),

msrNav([AdtLogin],[value,eq,[[ptString,'icrashadmin']]],[[ptBoolean,true]]),
msrNav([AdtPassword],[value,eq,[[ptString,'7WXC1359']]],[[ptBoolean,true]]),

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
