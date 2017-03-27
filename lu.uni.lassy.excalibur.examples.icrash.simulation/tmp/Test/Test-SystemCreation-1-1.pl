%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/1.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-------------------------------------------
:-msrTestAddStep([system,sim,1,1]).
%-------------------------------------------
msrTest([[system, sim, 1,1],
[[target, oeCreateSystemAndEnvironment],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%%-------------------------------------------
(
 (
% Step 1

%% Context Declaration

%% Variable Declaration
%% N.A.

%% Actor Declaration
%% N.A.

%% Input Parameter Declaration
msrVar(ptInteger, AqtyComCompanies),

%% Output Parameter Declaration
%% N.A.

%% Context Specification

%% Variable Specification
%% N.A.

%% Actor Specification
%% N.A.

%% Input Parameter Specification
msrNav([AqtyComCompanies],[],[[ptInteger,4]]),

%% Output Parameter Specification
%% N.A.

%% Test Specification
Target =  launchCreateSystemAndEnvironment, 	
		ParameterList = 
	[ [AqtyComCompanies],
	  Result
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
Context = [ ],
InputParameters = [['AqtyComCompanies' ,AqtyComCompanies]],
OutputParameters = [],
Comments = ''
.
