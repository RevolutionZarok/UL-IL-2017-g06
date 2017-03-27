% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- State --------------------------------
% msd01
:- newStateClass([[nextValueForAlertID,dtInteger],[nextValueForCrisisID,dtInteger],[clock,dtDateAndTime],[crisisReminderPeriod,dtSecond],[initialCrisisReminderPeriod,dtSecond],[maxCrisisReminderPeriod,dtSecond],[vpStarted,ptBoolean]]).


% msd02
:- newOperation(ctState,init,[[ctState,dtInteger,dtInteger,dtDateAndTime,dtSecond,dtSecond,dtSecond,ptBoolean],[ptBoolean]]).


