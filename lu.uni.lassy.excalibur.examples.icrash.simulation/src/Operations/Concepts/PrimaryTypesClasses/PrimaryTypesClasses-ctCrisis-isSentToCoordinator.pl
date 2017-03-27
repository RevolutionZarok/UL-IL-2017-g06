%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctCrisis,isSentToCoordinator,[Self,AactCoordinator],
      Result):-

/* Post F01 */
(
  msrNav([AactCoordinator],
         [rnInterfaceIN,ieSendACrisis,[Self]],
         [[ptBoolean,true]])
)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.
