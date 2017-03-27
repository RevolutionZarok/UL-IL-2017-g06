%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% msd01
msrop(dtLatitude,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
  (  msrNav([AdtValue],
        [value,geq,[[ptReal,-90.0]]],
        [[ptBoolean,true]]),
     msrNav([AdtValue],
        [value,leq,[[ptReal,+90.0]]],
        [[ptBoolean,true]])
  )
  -> (TheResult = [ptBoolean,true])
  ; (TheResult = [ptBoolean,false])
),
Result = TheResult
.
