% ------------------------------------------
%%%%%% Actor
% ------------------------------------------

:- newActor(actMsrCreator,rnactMsrCreator,[]).



% Output Interface
:- newInterface(actMsrCreator, ctOutputInterface, outactMsrCreator).
% Events
:- newEvents(outputEvents, [outactMsrCreator,oeCreateSystemAndEnvironment,[[ptInteger],[ptBoolean]]]).

% Input Interface
:- newInterface(actMsrCreator, ctInputInterface, inactMsrCreator).
% Events
	

% ------------------------------------------	
