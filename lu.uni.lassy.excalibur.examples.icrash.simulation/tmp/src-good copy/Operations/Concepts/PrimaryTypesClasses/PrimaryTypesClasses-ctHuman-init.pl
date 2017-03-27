%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctHuman,init,[Self,
                    Aid,
                    Akind],
      Result):-

/* Post F01 */
(
msrVar(ctHuman,Self),

msrNav([Self],[id],[Aid]),
msrNav([Self],[kind],[Akind]),

/* Post F02 */
 msrNav([Self],[msrIsNew],[Self])
)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.
