%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,3,15]).
%-----------------------------------------------
msrTest([[system,sim,3,15],
         [[target,oeLogout],
          [context,Context],
          [inputParameters,InputParameters],
          [outputParameters,OutputParameters],
          [comments,Comments],
          TestResult]
        ]):-
%%-----------------------------------------------
(
 (
% Step 1
%% Context Declaration
msrVar(ctState,TheSystem),
msrVar(actAdministrator,TheActor),

%% Input Parameters Declaration
%% N.A.

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactAdministrator,msrAny,msrTrue],
       [TheActor]),

%% Input Parameters Specification
%% N.A.

%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeLogout,[
                       ]],
  [Result]
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
Context = [['TheSystem',TheSystem],
           ['TheActor',TheActor]
          ],
InputParameters = [
                  ],
OutputParameters = [],
Comments =  'The Administrator logout. '
 . 