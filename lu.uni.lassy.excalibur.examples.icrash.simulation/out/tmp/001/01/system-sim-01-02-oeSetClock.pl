%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
%:- multifile systemTestCaseStep/3.
:- multifile msrTest/1.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%-----------------------------------------------
:-msrTestAddStep([system,sim,1,2]).
%-----------------------------------------------
msrTest([[system,sim,1,2],
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
%%       [msmAtPre,rnactActivator],
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
       [[ptInteger,24]]),

msrNav([AcurrentClock],
       [time,hour,value],
       [[ptInteger,15]]),
msrNav([AcurrentClock],
       [time,minute,value],
       [[ptInteger,20]]),
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
Comments =  'The first clock update ! '
 . 