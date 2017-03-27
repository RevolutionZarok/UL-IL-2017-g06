%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeCloseCrisis,
      [preProtocol,Self,
       AdtCrisisID
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

/* PreP02 */
 msrNav([TheActor],
        [rnctAuthenticated,vpIsLogged],
        [[ptBoolean,true]])
.

msrop(outactCoordinator,
      oeCloseCrisis,
      [preFunctional,Self,
       AdtCrisisID
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
      oeCloseCrisis,
      [post,Self,
       AdtCrisisID
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  
  msrVar(ctCrisis,TheCrisis),
  msrVar(dtCrisisID,AdtCrisisID),
  
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
         [[etCrisisStatus,closed]]),
  
/* PostF02 */
  msrNav([TheCrisis],
         [msmAtPost,rnHandler],
         []),

  /* PostF03 */
  msrNav([TheCrisis],
         [rnAlerts,msrForAll,msrIsKilled],
         [[ptBoolean,true]]),
    
/* PostF04 */
  msrNav([TheActor],
         [rnInterfaceIN,
          ieMessage,[[ptString,'The crisis is now closed !']]
         ],
         [[ptBoolean,true]]),

/* Post Protocol:*/
/* PostP01 */
 true
 .