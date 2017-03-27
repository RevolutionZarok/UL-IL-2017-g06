%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% dtComment

%msd01
msrop(dtPassword,is,[AdtValue],Result):- 
 msrVar(ptBoolean,TheResult),
 msrVar(ptInteger,MinLength),
(
   (
        (
         MinLength = [ptInteger,6],
         msrNav([AdtValue],
                [value,length,[],geq,[MinLength]],
                [[ptBoolean,true]])
        )
          -> TheResult = [ptBoolean,true]
          ; TheResult = [ptBoolean,false]
   )
),
  Result = TheResult
.
/*
| ?- X = [dtPassword,[],[[dtString,[[value,[ptString,'012345']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtPassword,[],[[dtString,[[value,[ptString,'012345']]],[]]]],
Result = [ptBoolean,true] ? 
yes


| ?- X = [dtPassword,[],[[dtString,[[value,[ptString,'01234']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtPassword,[],[[dtString,[[value,[ptString,'01234']]],[]]]],
Result = [ptBoolean,false] ? 
yes
*/