%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% dtPhoneNumber

% msd01
msrop(dtGPSLocation,is,[AdtValue],Result):-
msrVar(ptBoolean,TheResult),
(
   (
         msrNav([AdtValue],
                [latitude,is,[]],
                [[ptBoolean,true]]),
         msrNav([AdtValue],
                [longitude,is,[]],
                [[ptBoolean,true]])
   )
  -> TheResult = [ptBoolean,true]
  ; TheResult = [ptBoolean,false]
),
    
  Result = TheResult
.
