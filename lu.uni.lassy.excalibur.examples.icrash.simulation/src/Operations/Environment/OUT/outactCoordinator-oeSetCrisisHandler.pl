%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeSetCrisisHandler,
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

  msrNav([TheActor],
        [rnctAuthenticated,vpIsLogged],
        [[ptBoolean,true]])
.

msrop(outactCoordinator,
      oeSetCrisisHandler,
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
      oeSetCrisisHandler,
      [post,Self,
       AdtCrisisID
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  msrVar(ctCoordinator,TheCoordinator),
  msrVar(ctCoordinator,TheCurrentHandler),
  
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
         [[etCrisisStatus,handled]]),
  
  msrNav([TheActor],
         [rnctCoordinator],
         [TheCoordinator]),
  msrNav([TheCrisis],
         [msmAtPost,rnHandler],
         [TheCoordinator]),
  
  msrNav([TheActor],
         [rnInterfaceIN,
          ieMessage,[[ptString,'You are now considered as handling the crisis !']]
         ],
         [[ptBoolean,true]]),

  /* PostF02 */
  msrNav([TheCrisis],
         [rnAlerts,msrForAll,isSentToCoordinator,[TheActor]],
         [[ptBoolean,true]]),
  
  /* PostF03 */
  ( msrNav([TheCrisis],
           [rnHandler,msrSize,eq,[[ptInteger,1]]],
           [[ptBoolean,true]])
    -> (msrNav([TheCrisis],
               [rnHandler],
               [TheCurrentHandler]),
        msrNav([TheCurrentHandler],
               [rnactCoordinator,rnInterfaceIN,
                ieMessage,[[ptString,'One of the crisis you were handling is now handled by one of your colleagues!']]
               ],
               [[ptBoolean,true]])
       )
   ; true
  ),

    /* PostF04 */
  msrNav([TheCrisis],
         [rnAlerts,rnSignaler,msrForAll,isAcknowledged,[]],
         [[ptBoolean,true]]),
  
 /* Post Protocol:*/
/* PostP01 */
 true
 .