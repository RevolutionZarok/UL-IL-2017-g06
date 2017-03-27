% ------------------------------------------
%%%% Primary Types - Enumerations
% ------------------------------------------
%msd01
:- newType(enumerationTypes,[etCrisisStatus,[pending,handled,solved]]).

:- newOperation(etCrisisStatus,is,[[etCrisisStatus],[ptBoolean]]).

%msd02
:- newType(enumerationTypes,[etAlertStatus,[pending,valid,invalid]]).

:- newOperation(etAlertStatus,is,[[etAlertStatus],[ptBoolean]]).

%msd03
:- newType(enumerationTypes,[etCrisisType,[small,medium,huge]]).

:- newOperation(etCrisisType,is,[[etCrisisType],[ptBoolean]]).

%msd04
:- newType(enumerationTypes,[etHumanKind,[witness,victim,anonymous]]).

:- newOperation(etHumanKind,is,[[etHumanKind],[ptBoolean]]).

