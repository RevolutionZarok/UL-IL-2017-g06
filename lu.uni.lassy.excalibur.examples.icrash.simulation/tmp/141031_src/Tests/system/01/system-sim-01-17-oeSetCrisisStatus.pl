%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,17]).
%-----------------------------------------------
msrTest([[system,sim,1,17],
         [[target,oeSetCrisisStatus],
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
msrVar(etCrisisStatus,AetCrisisStatus),

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
  msrNav([AetCrisisStatus],
         [],
         [[etCrisisStatus,solved]]),
  
%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
   oeSetCrisisStatus,[
                        AdtCrisisID,
                        AetCrisisStatus
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
                   ['AdtCrisisID',AdtCrisisID],
                   ['AetCrisisStatus',AetCrisisStatus]
                  ],
OutputParameters = [],
Comments =  'The coordinator declares crisis one as solved. '
 . 