%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctState,init,[Self,
                    AnextValueForAlertID,
                    AnextValueForCrisisID,
                    Aclock,
                    AcrisisReminderPeriod,
                    AmaxCrisisReminderPeriod,
                    AvpLastReminder,
                    AvpStarted],
      Result):-

 /* Post F01 */
(
 msrVar(ctState,Self),
 
 msrNav([Self],[nextValueForAlertID],[AnextValueForAlertID]),
 msrNav([Self],[nextValueForCrisisID],[AnextValueForCrisisID]),
 msrNav([Self],[clock],[Aclock]),
 msrNav([Self],[crisisReminderPeriod],[AcrisisReminderPeriod]),
 msrNav([Self],[maxCrisisReminderPeriod],[AmaxCrisisReminderPeriod]),
 msrNav([Self],[vpLastReminder],[AvpLastReminder]),
 msrNav([Self],[vpStarted],[AvpStarted]),
    
 msrNav([Self],[msrIsNew],[Self])
)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.


