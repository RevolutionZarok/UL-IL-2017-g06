%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% etCrisisType

% msd01
msrop(etCrisisType,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
   (
          member(AdtValue,[small, medium, huge])
   )
  -> TheResult = [ptBoolean,true]
  ; TheResult = [ptBoolean,false]
),
  Result = TheResult
.
