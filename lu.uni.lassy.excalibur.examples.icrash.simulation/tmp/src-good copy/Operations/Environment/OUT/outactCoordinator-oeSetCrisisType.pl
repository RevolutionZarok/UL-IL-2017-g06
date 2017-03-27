%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeSetCrisisType,
      [preProtocol,Self,
       AdtCrisisID,
       AetCrisisType
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
      oeSetCrisisType,
      [preFunctional,Self,
       AdtCrisisID,
       AetCrisisType
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
      oeSetCrisisType,
      [post,Self,
       AdtCrisisID,
       AetCrisisType
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  
  msrVar(ctCrisis,TheCrisis),
  msrVar(dtCrisisID,AdtCrisisID),
  msrVar(etCrisisType,AetCrisisType),
  
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PostF01 */
 msrNav([TheSystem],
        [rnctCrisis,
         msrSelect,
         id,eq,[AdtCrisisID]],
        [TheCrisis]),
 
  msrNav([TheCrisis],
         [msmAtPost,type],
         [AetCrisisType]),
  
  msrNav([TheActor],
         [rnInterfaceIN,
          ieMessage,[[ptString,'The crisis type has been updated  !']]
         ],
         [[ptBoolean,true]]),

/* Post Protocol:*/
/* PostP01 */
 true
 .