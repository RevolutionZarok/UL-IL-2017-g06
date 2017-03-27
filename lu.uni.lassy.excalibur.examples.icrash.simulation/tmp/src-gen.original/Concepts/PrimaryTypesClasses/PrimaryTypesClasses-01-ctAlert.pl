% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- ctAlert --------------------------------
% msd01
:- newSystemClass(ctAlert,[[id,dtAlertID],[status,etAlertStatus],[location,dtGPSLocation],[instant,dtDateAndTime],[comment,dtComment]],[rnctAlert,['0','*']]).


% msd02
:- newOperation(ctAlert,init,[[ctAlert,dtAlertID,etAlertStatus,dtGPSLocation,dtDateAndTime,dtComment],[ptBoolean]]).

:- newOperation(ctAlert,isSentToCoordinator,[[ctAlert,actCoordinator],[ptBoolean]]).

