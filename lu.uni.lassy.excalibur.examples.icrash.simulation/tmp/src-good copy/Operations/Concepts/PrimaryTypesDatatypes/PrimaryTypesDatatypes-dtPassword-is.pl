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