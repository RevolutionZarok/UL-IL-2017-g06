%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(dtAlertID,is,[AdtValue],Result):- 
% msd01
msrVar(ptBoolean,TheResult),
(
  (  msrNav([AdtValue],
        [value,length,[],gt,[[ptInteger,0]]],
        [[ptBoolean,true]]),
     msrNav([AdtValue],
        [value,length,[],leq,[[ptInteger,20]]],
        [[ptBoolean,true]])
  )
  -> (TheResult = [ptBoolean,true])
  ; (TheResult = [ptBoolean,false])
),
TheResult = Result
.

/*
| ?- X = [dtAlertID,[],[[dtString,[[value,[ptString,'0123456789']]],[]]]],
msrNav([X],[is,[]],[Result]).

X = [dtAlertID,[],[[dtString,[[value,[ptString,'0123456789']]],[]]]],
Result = [ptBoolean,true] ? 

yes

| ?- X = [dtAlertID,[],[[dtString,[[value,[ptString,'012345678901234567890123456789']]],[]]]],
msrNav([X],[is,[]],[Result]).

X = [dtAlertID,[],[[dtString,[[value,[ptString,'012345678901234567890123456789']]],[]]]],
Result = [ptBoolean,false] ? 

yes
*/