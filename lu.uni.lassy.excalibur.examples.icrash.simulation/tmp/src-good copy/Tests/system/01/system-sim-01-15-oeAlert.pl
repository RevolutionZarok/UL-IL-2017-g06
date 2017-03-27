%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,15]).
%-----------------------------------------------
msrTest([[system,sim,1,15],
         [[target,oeAlert],
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
msrVar(actComCompany,TheActor),

%% Input Parameters Declaration
msrVar(etHumanKind,AetHumanKind),
msrVar(dtDate,AdtDate),
msrVar(dtTime,AdtTime),
msrVar(dtPhoneNumber,AdtPhoneNumber),
msrVar(dtGPSLocation,AdtGPSLocation),
msrVar(dtComment,AdtComment),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactComCompany,msrAny,msrTrue],
       [TheActor]),

%% Input Parameters Specification
msrNav([AetHumanKind],[],[[etHumanKind,witness]]),
msrNav([AdtDate],
       [year,value],
       [[ptInteger,2017]]),
msrNav([AdtDate],
       [month,value],
       [[ptInteger,11]]),
msrNav([AdtDate],
       [day,value],
       [[ptInteger,26]]),

msrNav([AdtTime],
       [hour,value],
       [[ptInteger,10]]),
msrNav([AdtTime],
       [minute,value],
       [[ptInteger,20]]),
msrNav([AdtTime],
       [second,value],
       [[ptInteger,00]]),

msrNav([AdtPhoneNumber],
       [value],
       [[ptString,'+3524666445000']]),

msrNav([AdtGPSLocation],
       [latitude,value],
       [[ptReal,49.627675]]),
msrNav([AdtGPSLocation],
       [longitude,value],
       [[ptReal,6.159590]]),

msrNav([AdtComment],
       [value],
       [[ptString,'A car crash just happened.']]),

%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeAlert,[
                       AetHumanKind,
                       AdtDate,
                       AdtTime,
                       AdtPhoneNumber,
                       AdtGPSLocation,
                       AdtComment
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
                   ['AetHumanKind',AetHumanKind],
                   ['AdtDate',AdtDate],
                   ['AdtTime',AdtTime],
                   ['AdtPhoneNumber',AdtPhoneNumber],
                   ['AdtGPSLocation',AdtGPSLocation],
                   ['AdtComment',AdtComment]
                  ],
OutputParameters = [],
Comments =  'The witness second Alert ! '
 . 