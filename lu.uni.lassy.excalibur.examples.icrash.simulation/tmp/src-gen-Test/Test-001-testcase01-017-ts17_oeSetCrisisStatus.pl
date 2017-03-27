%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/17.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,17]).
%-----------------------------------------------
msrTest([[system, sim, 1,17],
[[target, oeSetCrisisStatus],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 17

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(dtCrisisID, AdtCrisisID),
msrVar(etCrisisStatus, AetCrisisStatus),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp5),
msrNav(CollTemp5, msrAny, msrTrue, [TheActor]),
msrNav([AdtCrisisID],[value],[[ptString,'1']]),
msrNav([AetCrisisStatus],[],[[etCrisisStatus, solved]]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeSetCrisisStatus, [AdtCrisisID,AetCrisisStatus]],
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
Parameters = [['TheActor' ,TheActor],['AdtCrisisID' ,AdtCrisisID],['AetCrisisStatus' ,AetCrisisStatus],['AMessage' ,AMessage]],
OutputParameters = [],
Comments = ''
.
