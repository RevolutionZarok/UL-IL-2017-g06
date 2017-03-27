%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% dtPhoneNumber

% msd01
msrop(dtPhoneNumber,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
  (  msrNav([AdtValue],
        [value,length,[],gt,[[ptInteger,4]]],
        [[ptBoolean,true]]),
     msrNav([AdtValue],
        [value,length,[],leq,[[ptInteger,30]]],
        [[ptBoolean,true]])
  )

  -> TheResult = [ptBoolean,true]
  ; TheResult = [ptBoolean,false]
),
  Result = TheResult
.
