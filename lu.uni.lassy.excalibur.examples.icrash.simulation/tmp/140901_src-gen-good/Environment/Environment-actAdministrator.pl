% ------------------------------------------
%%%%%% Actor
% ------------------------------------------

:- newActor(actAdministrator,rnactAdministrator,[]).

:- inherit(actAdministrator,[actAuthenticated]).

:- newOperation(actAdministrator,init,[[actAdministrator],[ptBoolean]]).


% Output Interface
:- newInterface(actAdministrator, ctOutputInterface, outactAdministrator).
% Events
:- newEvents(outputEvents, [outactAdministrator,oeAddCoordinator,[[dtCoordinatorID,dtLogin,dtPassword],[ptBoolean]]]).
:- newEvents(outputEvents, [outactAdministrator,oeDeleteCoordinator,[[dtCoordinatorID],[ptBoolean]]]).

% Input Interface
:- newInterface(actAdministrator, ctInputInterface, inactAdministrator).
% Events
:- newEvents(inputEvents, [inactAdministrator,ieCoordinatorAdded,[[],[ptBoolean]]]).
:- newEvents(inputEvents, [inactAdministrator,ieCoordinatorDeleted,[[],[ptBoolean]]]).
	

% ------------------------------------------	
