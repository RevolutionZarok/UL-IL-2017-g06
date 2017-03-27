%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(dtCoordinatorID,is,[AdtValue],Result):- 
% msd01
  msrVar(ptBoolean,TheResult),
(
  (  msrNav([AdtValue],
        [value,length,[],gt,[[ptInteger,0]]],
        [[ptBoolean,true]]),
     msrNav([AdtValue],
        [value,length,[],leq,[[ptInteger,5]]],
        [[ptBoolean,true]])
  )
  -> (TheResult = [ptBoolean,true])
  ; (TheResult = [ptBoolean,false])
),
TheResult = Result
.

