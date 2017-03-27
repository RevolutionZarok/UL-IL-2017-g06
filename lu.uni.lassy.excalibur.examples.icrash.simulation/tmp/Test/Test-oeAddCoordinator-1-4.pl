%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/4.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,4]).
%-------------------------------------------
msrTest([[system, sim, 1,4],
[[target, oeAddCoordinator],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 4

%% Context Declaration

%% Variable Declaration
msrVar(ctState, TheSystem),
%%msrVar(col, ThedtCoordinatorIDs),  %% needs to be changed How do u declare a collection?

%% Actor Declaration
msrVar(actAdministrator, TheActor),

%% Input Parameter Declaration
msrVar(dtCoordinatorID, AdtCoordinatorID),
msrVar(dtLogin, AdtLogin),
msrVar(dtPassword, AdtPassword),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
theSystem(TheSystem),

%% Actor Specification
msrNav([TheSystem],[rnactAdministrator,msrAny,msrTrue],[TheActor]),

%% Input Parameter Specification
msrNav([AdtCoordinatorID],[value],[[ptString,'1']]),
msrNav([AdtLogin],[value],[[ptString,'steve']]),
msrNav([AdtPassword],[value],[[ptString,'pwdMessirExcalibur2017']]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  msrNav,
	ParameterList = 
	[ [TheActor],
	[rnInterfaceOUT, oeAddCoordinator, [AdtCoordinatorID,AdtLogin,AdtPassword]],
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
Context = [['TheSystem', TheSystem],['TheActor',TheActor] ,['ThedtCoordinatorIDs',ThedtCoordinatorIDs]],
InputParameters = [['AdtCoordinatorID' ,AdtCoordinatorID],['AdtLogin' ,AdtLogin],['AdtPassword' ,AdtPassword]],
OutputParameters = [],
Comments = ''
.
