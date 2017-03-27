%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% etCrisisStatus

% msd01
msrop(etCrisisStatus,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
   (
          member(AdtValue,[pending, handled, solved, closed])
   )
  -> TheResult = [ptBoolean,true]
  ; TheResult = [ptBoolean,false]
),
  Result = TheResult
.
