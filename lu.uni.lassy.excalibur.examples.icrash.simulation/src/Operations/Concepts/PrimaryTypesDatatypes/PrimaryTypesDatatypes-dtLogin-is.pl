%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% dtComment

%msd01
msrop(dtLogin,is,[AdtValue],Result):- 
 msrVar(ptBoolean,TheResult),
 msrVar(ptInteger,MaxLength),
(
   (
        (
         MaxLength = [ptInteger,20],
         msrNav([AdtValue],
                [value,length,[],leq,[MaxLength]],
                [[ptBoolean,true]])
        )
          -> TheResult = [ptBoolean,true]
          ; TheResult = [ptBoolean,false]
   )
),
  Result = TheResult
.
/*
| ?- X = [dtLogin,[],[[dtString,[[value,[ptString,'01234567']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtLogin,[],[[dtString,[[value,[ptString,'01234567']]],[]]]],
Result = [ptBoolean,true] ? 
yes

| ?- X = [dtLogin,[],[[dtString,[[value,[ptString,'01234567a']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtLogin,[],[[dtString,[[value,[ptString,'01234567a']]],[]]]],
Result = [ptBoolean,false] ? 
yes
*/