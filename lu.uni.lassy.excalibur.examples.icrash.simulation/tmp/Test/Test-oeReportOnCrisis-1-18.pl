%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/18.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,18]).
%-------------------------------------------
msrTest([[system, sim, 1,18],
[[target, oeReportOnCrisis],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 18

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),

%% Actor Declaration
msrVar(actCoordinator, TheActor),

%% Input Parameter Declaration
msrVar(dtCrisisID, AdtCrisisID),
msrVar(dtComment, AdtComment),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactCoordinator,msrSelect,rnctCoordinator,login,value,eq,[[ptString,'steve']]],CollTemp5),
msrNav(CollTemp5, msrAny, msrTrue, [TheActor]),

%% Input Parameter Specification
msrNav([AdtCrisisID],[value],[[ptString,'1']]),
msrNav([AdtComment],[value],[[ptString,'3 victims sent to hospital, 2 cars evacuated and 4 rescue unit mobilized']]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeReportOnCrisis, [AdtCrisisID,AdtComment]],
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
InputParameters = [['AdtCrisisID' ,AdtCrisisID],['AdtComment' ,AdtComment]],
OutputParameters = [],
Comments = ''
.
