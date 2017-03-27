%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,12]).
%-----------------------------------------------
msrTest([[system,sim,1,12],
         [[target,oeSetCrisisHandler],
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
msrVar(dtCrisisID,AdtCrisisID),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactCoordinator,
        msrSelect,
        rnctCoordinator,login,value,
        eq,[[ptString,'steve']]],
       [TheActor]),

%% Input Parameters Specification
  msrNav([AdtCrisisID],
         [value],
         [[ptString,'1']]),
  
%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
   oeSetCrisisHandler,[
                   AdtCrisisID
                  ]
  ],
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
                   ['AdtCrisisID',AdtCrisisID]
                  ],
OutputParameters = [],
Comments =  'The coordinator declares himself a the crisis handler. '
 . 