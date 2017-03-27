:- multifile msrop/4.
:-write(writeFROMFILE).
msrop(actMsrCreator,init,[ThectType],Result):-
( 
msrop(actMsrCreator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
msrop(actCoordinator,init,[ThectType],Result):-
( 
msrop(actCoordinator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
msrop(actComCompany,init,[ThectType],Result):-
( 
msrop(actComCompany,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
msrop(actAuthenticated,init,[ThectType],Result):-
( 
msrop(actAuthenticated,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
msrop(actAdministrator,init,[ThectType],Result):-
( 
msrop(actAdministrator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
msrop(actActivator,init,[ThectType],Result):-
( 
msrop(actActivator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(inactMsrCreator,init,[[inactMsrCreator],[ptBoolean]]).
msrop(inactMsrCreator,init,[ThectType],Result):-
( 
msrop(inactMsrCreator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(inactCoordinator,init,[[inactCoordinator],[ptBoolean]]).
msrop(inactCoordinator,init,[ThectType],Result):-
( 
msrop(inactCoordinator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(inactComCompany,init,[[inactComCompany],[ptBoolean]]).
msrop(inactComCompany,init,[ThectType],Result):-
( 
msrop(inactComCompany,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(inactAuthenticated,init,[[inactAuthenticated],[ptBoolean]]).
msrop(inactAuthenticated,init,[ThectType],Result):-
( 
msrop(inactAuthenticated,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(inactAdministrator,init,[[inactAdministrator],[ptBoolean]]).
msrop(inactAdministrator,init,[ThectType],Result):-
( 
msrop(inactAdministrator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(inactActivator,init,[[inactActivator],[ptBoolean]]).
msrop(inactActivator,init,[ThectType],Result):-
( 
msrop(inactActivator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(outactMsrCreator,init,[[outactMsrCreator],[ptBoolean]]).
msrop(outactMsrCreator,init,[ThectType],Result):-
( 
msrop(outactMsrCreator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(outactCoordinator,init,[[outactCoordinator],[ptBoolean]]).
msrop(outactCoordinator,init,[ThectType],Result):-
( 
msrop(outactCoordinator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(outactComCompany,init,[[outactComCompany],[ptBoolean]]).
msrop(outactComCompany,init,[ThectType],Result):-
( 
msrop(outactComCompany,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(outactAuthenticated,init,[[outactAuthenticated],[ptBoolean]]).
msrop(outactAuthenticated,init,[ThectType],Result):-
( 
msrop(outactAuthenticated,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(outactAdministrator,init,[[outactAdministrator],[ptBoolean]]).
msrop(outactAdministrator,init,[ThectType],Result):-
( 
msrop(outactAdministrator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
:-newOperation(outactActivator,init,[[outactActivator],[ptBoolean]]).
msrop(outactActivator,init,[ThectType],Result):-
( 
msrop(outactActivator,new,[free],ThectType),!,
msmop(msrIsNew,ThectType,[])
)
-> Result = [ptBoolean,true] 
 ; Result = [ptBoolean,false] 
 . 
