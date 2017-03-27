%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,3,08]).
%-----------------------------------------------
msrTest([[system,sim,3,08],
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
msrVar(actCoordinator,TheActor),

%% Input Parameters Declaration
msrVar(dtLogin,AdtLogin),
msrVar(dtPassword,AdtPassword),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [msmAtPre,rnactCoordinator,
        msrSelect,
        rnctCoordinator,login,value,
        eq,[[ptString,'steve']]],
       [TheActor]),

%% Input Parameters Specification
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
Comments =  'The coordinator steve logs in. '
 . 