% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- ctCoordinator --------------------------------
% msd01
:- newSystemClass(ctCoordinator,[[id,dtCoordinatorID]],[rnctCoordinator,['0','*']]).

:- inherit(ctCoordinator,[ctAuthenticated]).

% msd02
:- newOperation(ctCoordinator,init,[[ctCoordinator,dtCoordinatorID,dtLogin,dtPassword],[ptBoolean]]).


