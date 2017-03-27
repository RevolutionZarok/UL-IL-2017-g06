% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- ctCrisis --------------------------------
% msd01
:- newSystemClass(ctCrisis,[[id,dtCrisisID],[type,etCrisisType],[status,etCrisisStatus],[location,dtGPSLocation],[instant,dtDateAndTime],[comment,dtComment]],[rnctCrisis,['0','*']]).


% msd02
:- newOperation(ctCrisis,init,[[ctCrisis,dtCrisisID,etCrisisType,etCrisisStatus,dtGPSLocation,dtDateAndTime,dtComment],[ptBoolean]]).

:- newOperation(ctCrisis,handlingDelayPassed,[[ctCrisis],[ptBoolean]]).

:- newOperation(ctCrisis,maxHandlingDelayPassed,[[ctCrisis],[ptBoolean]]).

:- newOperation(ctCrisis,isSentToCoordinator,[[ctCrisis,actCoordinator],[ptBoolean]]).

:- newOperation(ctCrisis,isAllocatedIfPossible,[[ctCrisis],[ptBoolean]]).


