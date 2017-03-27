%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,4,4]).
%-----------------------------------------------
msrTest([[system,sim,4,4],
         [[target,oeAddCoordinator],
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
msrVar(dtCoordinatorID,AdtCoordinatorID),
msrVar(dtLogin,AdtLogin),
msrVar(dtPassword,AdtPassword),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactAdministrator,msrAny,msrTrue],
       [TheActor]),

%% Input Parameters Specification
  msrNav([AdtCoordinatorID],
         [value,eq,[[ptString,'1']]],
         [[ptBoolean,true]]),
  
  msrNav([AdtLogin],
         [value,eq,[[ptString,'steve']]],
         [[ptBoolean,true]]),
  
  msrNav([AdtPassword],
         [value,eq,[[ptString,'pwdMessirExcalibur2017']]],
         [[ptBoolean,true]]),

%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeAddCoordinator,[
                       AdtCoordinatorID,
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
                   ['AdtCoordinatorID',AdtCoordinatorID],
                   ['AdtLogin',AdtLogin],
                   ['AdtPassword',AdtPassword]
                  ],
OutputParameters = [],
Comments =  'The Administrator login. '
 . 