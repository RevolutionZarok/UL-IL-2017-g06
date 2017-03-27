%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% etAlertStatus

% msd01
msrop(etAlertStatus,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
   (
          member(AdtValue,[pending, valid, invalid])
   )
  -> TheResult = [ptBoolean,true]
  ; TheResult = [ptBoolean,false]
),
  Result = TheResult
.
