% ------------------------------------------
%%%%%% Actor
% ------------------------------------------

:- newActor(actActivator,rnactActivator,[]).


:- newOperation(actActivator,init,[[actActivator],[ptBoolean]]).


% Output Interface
:- newInterface(actActivator, ctOutputInterface, outactActivator).
% Events
:- newEvents(outputEvents, [outactActivator,oeSollicitateCrisisHandling,[[],[ptBoolean]]]).
:- newEvents(outputEvents, [outactActivator,oeSetClock,[[dtDateAndTime],[ptBoolean]]]).

% Input Interface
:- newInterface(actActivator, ctInputInterface, inactActivator).
% Events
	

% ------------------------------------------	
