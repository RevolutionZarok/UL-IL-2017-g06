% ------------------------------------------
%%%%%% Actor
% ------------------------------------------

:- newActor(actComCompany,rnactComCompany,[]).


:- newOperation(actComCompany,init,[[actComCompany],[ptBoolean]]).


% Output Interface
:- newInterface(actComCompany, ctOutputInterface, outactComCompany).
% Events
:- newEvents(outputEvents, [outactComCompany,oeAlert,[[etHumanKind,dtDate,dtTime,dtPhoneNumber,dtGPSLocation,dtComment],[ptBoolean]]]).

% Input Interface
:- newInterface(actComCompany, ctInputInterface, inactComCompany).
% Events
:- newEvents(inputEvents, [inactComCompany,ieSmsSend,[[dtPhoneNumber,dtSMS],[ptBoolean]]]).
	

% ------------------------------------------	
