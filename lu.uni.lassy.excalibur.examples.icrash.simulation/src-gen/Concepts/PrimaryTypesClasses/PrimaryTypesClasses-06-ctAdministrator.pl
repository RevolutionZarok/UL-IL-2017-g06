% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- ctAdministrator --------------------------------
% msd01
:- newSystemClass(ctAdministrator,[],[rnctAdministrator,['1','1']]).

:- inherit(ctAdministrator,[ctAuthenticated]).

% msd02
:- newOperation(ctAdministrator,init,[[ctAdministrator,dtLogin,dtPassword],[ptBoolean]]).


