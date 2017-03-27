%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,1]).
%-----------------------------------------------
msrTest([[system,sim,1,1],
         [[target,oeCreateSystemAndEnvironment],
          [context,Context],
          [inputParameters,InputParameters],
          [outputParameters,OutputParameters],
          [comments,Comments],
          TestResult]
        ]):-
%%-----------------------------------------------
(
 (
% Step 0

%% Context Declaration
%% N.A.

%% Input Parameters Declaration
msrVar(ptInteger,AqtyComCompanies),

%% Output Parameters Declaration
%% N.A.

%% Context Specification
%% N.A.

%% Input Parameters Specification
AqtyComCompanies = [ptInteger,4],

%% Output Parameters Specification
%% N.A.

%% Test Specification
Target = launchCreateSystemAndEnvironment,
ParametersList = 
[ [AqtyComCompanies],
  Result
], !,
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
Context = [],
InputParameters = ['AqtyComCompanies',AqtyComCompanies],
OutputParameters = [],
Comments = 'System launch ! '
.
