%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% dtComment

%msd01
msrop(dtSMS,is,[AdtValue],Result):- 
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