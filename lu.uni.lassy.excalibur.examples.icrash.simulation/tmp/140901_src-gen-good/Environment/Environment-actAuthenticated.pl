% ------------------------------------------
%%%%%% Actor
% ------------------------------------------

:- newActor(actAuthenticated,rnactAuthenticated,[]).


:- newOperation(actAuthenticated,init,[[actAuthenticated],[ptBoolean]]).


% Output Interface
:- newInterface(actAuthenticated, ctOutputInterface, outactAuthenticated).
% Events
:- newEvents(outputEvents, [outactAuthenticated,oeLogin,[[dtLogin,dtPassword],[ptBoolean]]]).
:- newEvents(outputEvents, [outactAuthenticated,oeLogout,[[],[ptBoolean]]]).

% Input Interface
:- newInterface(actAuthenticated, ctInputInterface, inactAuthenticated).
% Events
:- newEvents(inputEvents, [inactAuthenticated,ieMessage,[[ptString],[ptBoolean]]]).
	

% ------------------------------------------	
