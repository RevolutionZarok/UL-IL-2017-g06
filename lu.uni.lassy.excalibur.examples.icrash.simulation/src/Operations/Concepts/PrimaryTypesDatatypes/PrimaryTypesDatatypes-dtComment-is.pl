%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% dtComment

%msd01
msrop(dtComment,is,[AdtValue],Result):- 
 msrVar(ptBoolean,TheResult),
 msrVar(ptInteger,MaxLength),
(
   (
        (
         MaxLength = [ptInteger,160],
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
| ?- X = [dtComment,[],[[dtString,[[value,[ptString,'I broke my leg ! Please help ...']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtComment,[],[[dtString,[[value,[ptString,'I broke my leg ! Please help ...']]],[]]]],
Result = [ptBoolean,true] ? 
yes

| ?- X = [dtComment,[],[[dtString,[[value,[ptString,'I broke my leg when I was running with my dog to go to the skate park because my friends called me on my mobile phone and told me that a skate star was doing triple back flips.']]],[]]]],
msrNav([X],[is,[]],[Result]).
X = [dtComment,[],[[dtString,[[value,[ptString,'I broke my leg when I was running with my dog to go to the skate park because my friends called me on my mobile phone and told me that a skate star was doing triple back flips.']]],[]]]],
Result = [ptBoolean,false] ? 
yes
*/