%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctCrisis,init,[Self,
                    Aid,
                    Atype,
                    Astatus,
                    Alocation,
                    Ainstant,
                    Acomment],
      Result):-

/* Post F01 */
(
msrVar(ctCrisis,Self),

msrNav([Self],[id],[Aid]),
msrNav([Self],[type],[Atype]),
msrNav([Self],[status],[Astatus]),
msrNav([Self],[location],[Alocation]),
msrNav([Self],[instant],[Ainstant]),
msrNav([Self],[comment],[Acomment]),

/* Post F02 */
 msrNav([Self],[msrIsNew],[Self])
)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.
