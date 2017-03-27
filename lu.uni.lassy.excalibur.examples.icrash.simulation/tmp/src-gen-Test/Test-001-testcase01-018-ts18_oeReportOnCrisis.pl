%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/18.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,18]).
%-----------------------------------------------
msrTest([[system, sim, 1,18],
[[target, oeReportOnCrisis],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 18

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actCoordinator, TheActor),
msrVar(dtCrisisID, AdtCrisisID),
msrVar(dtComment, AdtComment),
msrVar(ptString, AMessage),

%% Constraints

theSystem(TheSystem),

msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp6),
msrNav(CollTemp6, msrAny, msrTrue, [TheActor]),
msrNav([AdtCrisisID],[value],[[ptString,'1']]),
msrNav([AdtComment],[value],[[ptString,'3 victims sent to hospital, 2 cars evacuated and 4 rescue unit mobilized']]),

%% Test Specification
Target =  msrNav,
	ParametersList = 
	[ [TheActor],
	  [rnInterfaceOUT, oeReportOnCrisis, [AdtCrisisID,AdtComment]],
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
Parameters = [['TheActor' ,TheActor],['AdtCrisisID' ,AdtCrisisID],['AdtComment' ,AdtComment],['AMessage' ,AMessage]],
OutputParameters = [],
Comments = ''
.
