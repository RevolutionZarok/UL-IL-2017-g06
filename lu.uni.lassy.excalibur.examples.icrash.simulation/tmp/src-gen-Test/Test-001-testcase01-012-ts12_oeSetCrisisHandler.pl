%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/12.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,12]).
%-----------------------------------------------
msrTest([[system, sim, 1,12],
[[target, oeSetCrisisHandler],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 12

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(actComCompany, TheComCompany),
msrVar(actCoordinator, TheCoordinator),
msrVar(dtCrisisID, AdtCrisisID),
msrVar(ptString, AMessage),
msrVar(dtPhoneNumber, AdtPhoneNumber),
msrVar(dtSMS, AdtSMS),
msrVar(ctAlert, ActAlert),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp3),
msrNav(CollTemp3, msrAny, msrTrue, [TheActor]),
msrNav([AdtCrisisID],[value],[[ptString,'1']]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeSetCrisisHandler, [AdtCrisisID]],
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
Parameters = [['TheActor' ,TheActor],['TheComCompany' ,TheComCompany],['TheCoordinator' ,TheCoordinator],['AdtCrisisID' ,AdtCrisisID],['AMessage' ,AMessage],['AdtPhoneNumber' ,AdtPhoneNumber],['AdtSMS' ,AdtSMS],['ActAlert' ,ActAlert]],
OutputParameters = [],
Comments = ''
.
