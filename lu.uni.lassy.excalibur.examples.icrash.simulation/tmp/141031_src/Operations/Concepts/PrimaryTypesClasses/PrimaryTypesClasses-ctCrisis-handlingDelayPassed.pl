%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctCrisis,handlingDelayPassed,[Self],
      Result):-

/* Post F01 */
(
  msrVar(ctState,TheSystem),
  msrVar(dtInteger,CurrentClockSecondsQty),
  msrVar(dtInteger,LastReminderSecondsQty),
  msrVar(dtSecond,CrisisReminderPeriod),
    
  msrNav([Self],[rnSystem],[TheSystem]),

  msrNav([Self],
         [status],
         [[etCrisisStatus,pending]]),
      
  msrNav([TheSystem],
        [clock,toSecondsQty,[]],
        [CurrentClockSecondsQty]),

  msrNav([TheSystem],
        [vpLastReminder,toSecondsQty,[]],
        [LastReminderSecondsQty]),

  msrNav([TheSystem],
        [crisisReminderPeriod],
        [CrisisReminderPeriod]),
  
  msrNav([CurrentClockSecondsQty],
        [sub,[LastReminderSecondsQty],
         gt, [CrisisReminderPeriod]
         ],
        [[ptBoolean,true]])

)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.
