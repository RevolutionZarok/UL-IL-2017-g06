%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,18]).
%-----------------------------------------------
msrTest([[system,sim,1,18],
         [[target,oeReportOnCrisis],
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
msrVar(dtComment,AdtComment),

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
  msrNav([AdtComment],
         [value],
         [[ptString,'3 victims sent to hospital, 2 cars evacuated and 4 rescue unit mobilized']]),
  
%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
   oeReportOnCrisis,[
                        AdtCrisisID,
                        AdtComment
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
                   ['AdtComment',AdtComment]
                  ],
OutputParameters = [],
Comments =  'The coordinator reports on crisis one. '
 . 