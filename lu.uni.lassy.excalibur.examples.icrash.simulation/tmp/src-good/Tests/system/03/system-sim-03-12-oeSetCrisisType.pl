%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,3,12]).
%-----------------------------------------------
msrTest([[system,sim,3,12],
         [[target,oeSetCrisisType],
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
msrVar(etCrisisType,AetCrisisType),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [msmAtPre,rnactCoordinator,
        msrSelect,
        rnctCoordinator,login,value,
        eq,[[ptString,'steve']]],
       [TheActor]),

%% Input Parameters Specification
  msrNav([AdtCrisisID],
         [value],
         [[ptString,'1']]),
  msrNav([AetCrisisType],
         [],
         [[etCrisisType,small]]),
  
%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
   oeSetCrisisType,[
                        AdtCrisisID,
                        AetCrisisType
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
                   ['AetCrisisType',AetCrisisType]
                  ],
OutputParameters = [],
Comments =  'The coordinator declares crisis one as small. '
 . 