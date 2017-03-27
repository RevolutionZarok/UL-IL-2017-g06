%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(dtCrisisID,is,[AdtValue],Result):- 
% msd01
  msrVar(ptBoolean,TheResult),
(
  (  msrNav([AdtValue],
        [value,length,[],gt,[[ptInteger,0]]],
        [[ptBoolean,true]]),
     msrNav([AdtValue],
        [value,length,[],leq,[[ptInteger,10]]],
        [[ptBoolean,true]])
  )
  -> (TheResult = [ptBoolean,true])
  ; (TheResult = [ptBoolean,false])
),
TheResult = Result
.
/*
| ?- X = [dtCrisisID,[],[[dtString,[[value,[ptString,'0123456789']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtCrisisID,[],[[dtString,[[value,[ptString,'0123456789']]],[]]]],
Result = [ptBoolean,true] ? 
yes

| ?- X = [dtCrisisID,[],[[dtString,[[value,[ptString,'0123456789a']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtCrisisID,[],[[dtString,[[value,[ptString,'0123456789a']]],[]]]],
Result = [ptBoolean,false] ? 
yes
*/
