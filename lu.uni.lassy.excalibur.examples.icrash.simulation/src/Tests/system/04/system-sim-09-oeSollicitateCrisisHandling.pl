%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,4,9]).
%-----------------------------------------------
msrTest([[system,sim,4,9],
         [[target,oeSollicitateCrisisHandling],
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
msrVar(actActivator,TheActor),

%% Input Parameters Declaration
%% N.A.

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactActivator],
       [TheActor]),

%% Input Parameters Specification
%% N.A.

%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeSollicitateCrisisHandling,
              [
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
Comments =  'The sollicitation for handling current crisis or alerts ! '
 . 