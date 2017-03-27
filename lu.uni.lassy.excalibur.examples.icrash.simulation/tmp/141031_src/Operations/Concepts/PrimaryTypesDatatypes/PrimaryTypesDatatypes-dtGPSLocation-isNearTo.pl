%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% dtGPSLocation

msrop(dtGPSLocation,isNearTo,[Self,AdtValue],Result):-
msrVar(ptBoolean,TheResult),
msrVar(dtReal,EarthRadius),
msrVar(dtReal,MaxDistance),

msrVar(dtLatitude,ComparedLatitude),
msrVar(dtLongitude,ComparedLongitude),

msrVar(dtReal,R1),msrVar(dtReal,R1a),
msrVar(dtReal,R2),msrVar(dtReal,R2a),

(
   (
        (
         % msd01
         msrNav([EarthRadius],[value],[[ptReal,6371]]),
         msrNav([MaxDistance],[value],[[ptReal,100]]),
         
         msrNav([AdtValue],[latitude],[ComparedLatitude]),
         msrNav([AdtValue],[longitude],[ComparedLongitude]),
         
         msrNav([Self],[latitude,sin,[]],[R1a]),
         msrNav([AdtValue],[latitude,sin,[],mul,[R1a]],[R1]),
         
         msrNav([Self],[latitude,cos,[]],[R2a]),
         msrNav([AdtValue],[latitude,cos,[],mul,[R2a]],[R2]),
         
         msrNav([AdtValue],[longitude],[ComparedLongitude]),
         msrNav([Self],[longitude,sub,[ComparedLongitude],cos,[],mul,[R2],
                        add,[R1],
                        acos,[],
                        mul,[EarthRadius],
                        sub,[MaxDistance],
                        value,leq,[[ptReal,0]]],
                [[ptBoolean,true]])
        )
          -> TheResult = [ptBoolean,true]
          ; TheResult = [ptBoolean,false]
   )
),
  Result = TheResult
.
