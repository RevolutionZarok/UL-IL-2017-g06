%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,3,14]).
%-----------------------------------------------
msrTest([[system,sim,3,14],
         [[target,oeDeleteCoordinator],
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

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactAdministrator,msrAny,msrTrue],
       [TheActor]),

%% Input Parameters Specification
  msrNav([AdtCoordinatorID],
         [value,eq,[[ptString,'1']]],
         [[ptBoolean,true]]),
  
%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeDeleteCoordinator,[
                       AdtCoordinatorID
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
                   ['AdtCoordinatorID',AdtCoordinatorID]
                  ],
OutputParameters = [],
Comments =  'The deletion of the coordinator by the administrator. '
 . 