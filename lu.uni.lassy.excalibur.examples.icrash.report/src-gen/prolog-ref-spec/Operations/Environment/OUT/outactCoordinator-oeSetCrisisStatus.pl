%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeSetCrisisStatus,
      [preProtocol,Self,
       AdtCrisisID,
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
      oeSetCrisisStatus,
      [preFunctional,Self,
       AdtCrisisID,
       AetCrisisStatus
       ],
      []):-
/* Pre Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),

  msrVar(dtCrisisID,AdtCrisisID),

  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PreF01 */
 msrNav([TheSystem],
        [rnctCrisis,
         msrSelect,
         id,eq,[AdtCrisisID]
        ],
        ColCrisis),

  msrNav(ColCrisis,
         [msrSize,eq,[[ptInteger,1]]],
         [[ptBoolean,true]])
  .

msrop(outactCoordinator,
      oeSetCrisisStatus,
      [post,Self,
       AdtCrisisID,
       AetCrisisStatus
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  
  msrVar(ctCrisis,TheCrisis),
  msrVar(dtCrisisID,AdtCrisisID),
  msrVar(etCrisisStatus,AetCrisisStatus),
  
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PostF01 */
 msrNav([TheSystem],
        [rnctCrisis,
         msrSelect,
         id,eq,[AdtCrisisID]],
        [TheCrisis]),
 
  msrNav([TheCrisis],
         [msmAtPost,status],
         [AetCrisisStatus]),
  
  msrNav([TheActor],
         [rnInterfaceIN,
          ieMessage,[[ptString,'The crisis status has been updated !']]
         ],
         [[ptBoolean,true]]),

/* Post Protocol:*/
/* PostP01 */
 true
 .