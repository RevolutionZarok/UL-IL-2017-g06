%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% etHumanKind

% msd01
msrop(etHumanKind,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
   (
          member(AdtValue,[witness,victim,anonymous])
   )
  -> TheResult = [ptBoolean,true]
  ; TheResult = [ptBoolean,false]
),
  Result = TheResult
.
