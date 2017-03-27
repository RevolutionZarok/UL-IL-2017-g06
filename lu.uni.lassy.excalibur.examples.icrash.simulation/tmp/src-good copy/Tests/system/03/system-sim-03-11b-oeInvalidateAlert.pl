%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,3,11]).
%-----------------------------------------------
msrTest([[system,sim,3,11],
         [[target,oeInvalidateAlert],
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
msrVar(dtAlertID,AdtAlertID),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactCoordinator,
        msrSelect,
        rnctCoordinator,login,value,
        eq,[[ptString,'steve']]],
       [TheActor]),

%% Input Parameters Specification
  msrNav([AdtAlertID],
         [value],
         [[ptString,'2']]),
  
%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
   oeInvalidateAlert,[
                    AdtAlertID
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
                   ['AdtAlertID',AdtAlertID]
                  ],
OutputParameters = [],
Comments =  'The coordinator closes fake alert two. '
 . 