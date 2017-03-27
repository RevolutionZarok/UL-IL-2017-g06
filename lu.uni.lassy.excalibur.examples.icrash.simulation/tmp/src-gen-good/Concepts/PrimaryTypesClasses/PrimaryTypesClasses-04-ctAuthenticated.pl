% ------------------------------------------
%%%%%% Primary Types - Class types
% ---- ctAuthenticated --------------------------------
% msd01
:- newSystemClass(ctAuthenticated,[[login,dtLogin],[pwd,dtPassword],[vpIsLogged,ptBoolean]],[rnctAuthenticated,['0','*']]).


% msd02
:- newOperation(ctAuthenticated,init,[[ctAuthenticated,dtLogin,dtPassword],[ptBoolean]]).


