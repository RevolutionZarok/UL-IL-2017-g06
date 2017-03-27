% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- ctHuman --------------------------------
% msd01
:- newSystemClass(ctHuman,[[id,dtPhoneNumber],[kind,etHumanKind]],[rnctHuman,['0','*']]).


% msd02
:- newOperation(ctHuman,init,[[ctHuman,dtPhoneNumber,etHumanKind],[ptBoolean]]).


