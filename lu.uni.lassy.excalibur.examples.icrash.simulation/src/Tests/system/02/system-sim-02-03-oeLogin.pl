%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,2,3]).
%-----------------------------------------------
msrTest([[system,sim,2,3],
         [[target,oeLogin],
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
msrVar(dtLogin,AdtLogin),
msrVar(dtPassword,AdtPassword),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactAdministrator,msrAny,msrTrue],
       [TheActor]),

%% Input Parameters Specification
  msrNav([AdtLogin],
         [value,eq,[[ptString,'icrashadmin']]],
         [[ptBoolean,true]]),
  
  msrNav([AdtPassword],
         [value,eq,[[ptString,'7WXC1359']]],
         [[ptBoolean,true]]),

%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeLogin,[
                       AdtLogin,
                       AdtPassword
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
                   ['AdtLogin',AdtLogin],
                   ['AdtPassword',AdtPassword]
                  ],
OutputParameters = [],
Comments =  'The Administrator login. '
 . 