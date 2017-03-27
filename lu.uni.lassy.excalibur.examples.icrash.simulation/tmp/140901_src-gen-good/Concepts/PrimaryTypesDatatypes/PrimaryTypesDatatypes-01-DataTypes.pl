% ------------------------------------------
%%%% Primary Types - Data Types
% ------------------------------------------
%msd01
:- newType(dataTypes,[dtAlertID,[]]).

:- inherit(dtAlertID,[dtString]).

:- newOperation(dtAlertID,is,[[dtAlertID],[ptBoolean]]).

%msd02
:- newType(dataTypes,[dtCrisisID,[]]).

:- inherit(dtCrisisID,[dtString]).

:- newOperation(dtCrisisID,is,[[dtCrisisID],[ptBoolean]]).

%msd03
:- newType(dataTypes,[dtLogin,[]]).

:- inherit(dtLogin,[dtString]).

:- newOperation(dtLogin,is,[[dtLogin],[ptBoolean]]).

%msd04
:- newType(dataTypes,[dtPassword,[]]).

:- inherit(dtPassword,[dtString]).

:- newOperation(dtPassword,is,[[dtPassword],[ptBoolean]]).

%msd05
:- newType(dataTypes,[dtCoordinatorID,[]]).

:- inherit(dtCoordinatorID,[dtString]).

:- newOperation(dtCoordinatorID,is,[[dtCoordinatorID],[ptBoolean]]).

%msd06
:- newType(dataTypes,[dtPhoneNumber,[]]).

:- inherit(dtPhoneNumber,[dtString]).

:- newOperation(dtPhoneNumber,is,[[dtPhoneNumber],[ptBoolean]]).

%msd07
:- newType(dataTypes,[dtComment,[]]).

:- inherit(dtComment,[dtString]).

:- newOperation(dtComment,is,[[dtComment],[ptBoolean]]).

%msd08
:- newType(dataTypes,[dtLatitude,[]]).

:- inherit(dtLatitude,[dtReal]).

:- newOperation(dtLatitude,is,[[dtLatitude],[ptBoolean]]).

%msd09
:- newType(dataTypes,[dtLongitude,[]]).

:- inherit(dtLongitude,[dtReal]).

:- newOperation(dtLongitude,is,[[dtLongitude],[ptBoolean]]).

%msd10
:- newType(dataTypes,[dtGPSLocation,[[latitude,dtLatitude],[longitude,dtLongitude]]]).


:- newOperation(dtGPSLocation,is,[[dtGPSLocation],[ptBoolean]]).

:- newOperation(dtGPSLocation,isNearTo,[[dtGPSLocation,dtGPSLocation],[ptBoolean]]).

