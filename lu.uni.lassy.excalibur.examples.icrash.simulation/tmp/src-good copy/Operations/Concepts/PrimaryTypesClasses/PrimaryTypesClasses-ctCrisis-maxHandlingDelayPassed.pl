%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctCrisis,maxHandlingDelayPassed,[Self],
      Result):-

/* Post F01 */
(
  msrVar(ctState,TheSystem),
  msrVar(dtInteger,CurrentClockSecondsQty),
  msrVar(dtInteger,CrisisInstantSecondsQty),
  msrVar(dtSecond,MaxCrisisReminderPeriod),
    
  msrNav([Self],[rnSystem],[TheSystem]),

  msrNav([Self],
         [status],
         [[etCrisisStatus,pending]]),
  
  msrNav([TheSystem],
        [clock,toSecondsQty,[]],
        [CurrentClockSecondsQty]),
 
  msrNav([Self],
        [instant,toSecondsQty,[]],
        [CrisisInstantSecondsQty]),
 
  msrNav([TheSystem],
        [maxCrisisReminderPeriod],
        [MaxCrisisReminderPeriod]),
  
  msrNav([CurrentClockSecondsQty],
        [sub,[CrisisInstantSecondsQty],
         gt, [MaxCrisisReminderPeriod]
         ],
        [[ptBoolean,true]])

)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.
