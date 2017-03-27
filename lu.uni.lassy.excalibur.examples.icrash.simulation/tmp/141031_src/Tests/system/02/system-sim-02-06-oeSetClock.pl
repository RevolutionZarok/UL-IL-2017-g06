%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,2,6]).
%-----------------------------------------------
msrTest([[system,sim,2,6],
         [[target,oeSetClock],
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
msrVar(actActivator,TheActor),

%% Input Parameters Declaration
msrVar(dtDateAndTime,AcurrentClock),

%% Context Specification
theSystem(TheSystem),
msrNav([TheSystem],
       [rnactActivator],
       [TheActor]),

%% Input Parameters Specification
msrNav([AcurrentClock],
       [date,year,value],
       [[ptInteger,2017]]),
msrNav([AcurrentClock],
       [date,month,value],
       [[ptInteger,11]]),
msrNav([AcurrentClock],
       [date,day,value],
       [[ptInteger,26]]),

msrNav([AcurrentClock],
       [time,hour,value],
       [[ptInteger,10]]),
msrNav([AcurrentClock],
       [time,minute,value],
       [[ptInteger,15]]),
msrNav([AcurrentClock],
       [time,second,value],
       [[ptInteger,00]]),

%% Test Specification
Target = msrNav,
ParametersList =
[ [TheActor],
  [rnInterfaceOUT,
              oeSetClock,[
                       AcurrentClock
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
                   ['AcurrentClock',AcurrentClock]
                  ],
OutputParameters = [],
Comments =  'The second clock update ! '
 . 