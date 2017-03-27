%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% dtPhoneNumber

% msd01
msrop(dtLongitude,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
  (  msrNav([AdtValue],
        [value,geq,[[ptReal,-180.0]]],
        [[ptBoolean,true]]),
     msrNav([AdtValue],
        [value,leq,[[ptReal,+180.0]]],
        [[ptBoolean,true]])
  )
  -> (TheResult = [ptBoolean,true])
  ; (TheResult = [ptBoolean,false])
),
    
  Result = TheResult
.
