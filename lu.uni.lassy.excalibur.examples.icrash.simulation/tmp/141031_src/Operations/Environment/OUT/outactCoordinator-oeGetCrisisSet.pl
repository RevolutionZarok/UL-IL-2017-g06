%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeGetCrisisSet,
      [preProtocol,Self,
       AetCrisisStatus
       ],
      []):-
/* Pre Protocol:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PreP01 */
 msrNav([TheSystem],
        [vpStarted],
        [[ptBoolean,true]]),

  msrNav([TheActor],
        [rnctAuthenticated,vpIsLogged],
        [[ptBoolean,true]])
.

msrop(outactCoordinator,
      oeGetCrisisSet,
      [preFunctional,Self,
       AetCrisisStatus
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
true
  .

msrop(outactCoordinator,
      oeGetCrisisSet,
      [post,Self,
       AetCrisisStatus
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PostF01 */
 msrNav([TheSystem],
        [rnctCrisis,
         msrSelect,
         status,etEq,[AetCrisisStatus]],
        ColCrisisSet),
 
  msrNav(ColCrisisSet,
         [msrForAll,isSentToCoordinator,[TheActor]],
         [[ptBoolean,true]]),

 /* Post Protocol:*/
/* PostP01 */
 true
 .