% ------------------------------------------
%%%%%% Actor
% ------------------------------------------

:- newActor(actCoordinator,rnactCoordinator,[]).

:- inherit(actCoordinator,[actAuthenticated]).

:- newOperation(actCoordinator,init,[[actCoordinator],[ptBoolean]]).


% Output Interface
:- newInterface(actCoordinator, ctOutputInterface, outactCoordinator).
% Events
:- newEvents(outputEvents, [outactCoordinator,oeCloseAlert,[[dtAlertID],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeCloseCrisis,[[dtCrisisID],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeGetAlertsSet,[[etAlertStatus],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeGetCrisisSet,[[etCrisisStatus],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeSetCrisisHandler,[[dtCrisisID],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeReportOnCrisis,[[dtCrisisID,dtComment],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeSetCrisisStatus,[[dtCrisisID,etCrisisStatus],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeSetCrisisType,[[dtCrisisID,etCrisisType],[ptBoolean]]]).
:- newEvents(outputEvents, [outactCoordinator,oeValidateAlert,[[dtAlertID],[ptBoolean]]]).

% Input Interface
:- newInterface(actCoordinator, ctInputInterface, inactCoordinator).
% Events
:- newEvents(inputEvents, [inactCoordinator,ieSendAnAlert,[[ctAlert],[ptBoolean]]]).
:- newEvents(inputEvents, [inactCoordinator,ieSendACrisis,[[ctCrisis],[ptBoolean]]]).
	

% ------------------------------------------	
