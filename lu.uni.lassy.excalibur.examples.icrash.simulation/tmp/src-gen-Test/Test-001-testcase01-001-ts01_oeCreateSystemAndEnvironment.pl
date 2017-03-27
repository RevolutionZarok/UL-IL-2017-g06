%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/1.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,1]).
%-----------------------------------------------
msrTest([[system, sim, 1,1],
[[target, oeCreateSystemAndEnvironment],
 [context, Context],
 [inputParameters, InputParameters],
 [outputParameters, OutputParameters],
 [comments, Comments],
 TestResult]
]):-
%-----------------------------------------------
(
 (
% Step 1

%% Variables Declaration
msrVar(ctState, TheSystem),
msrVar(actMsrCreator, Creator),
msrVar(ptInteger, AqtyComCompanies),

%% Constraints

theSystem(TheSystem),

msrNav([AqtyComCompanies],[],[[ptInteger,4]]),

%% Test Specification
Target =  launchCreateSystemAndEnvironment, 	
		ParametersList = 
	[ [AqtyComCompanies],
	  Result
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
Parameters = [['Creator' ,Creator],['AqtyComCompanies' ,AqtyComCompanies]],
OutputParameters = [],
Comments = ''
.
