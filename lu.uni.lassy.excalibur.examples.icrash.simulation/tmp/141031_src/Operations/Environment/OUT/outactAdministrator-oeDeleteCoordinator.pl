%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactAdministrator,
      oeDeleteCoordinator,
      [preProtocol,Self,
       AdtCoordinatorID
       ],
      []):-
/* Pre Protocol:*/
  msrVar(ctState,TheSystem),
  msrVar(actAdministrator,TheActor),
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

msrop(outactAdministrator,
      oeDeleteCoordinator,
      [preFunctional,Self,
       AdtCoordinatorID
       ],
      []):-
/* Pre Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actAdministrator,TheActor),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
  
/* PreF01 */
  msrNav([TheSystem],
         [rnctCoordinator,
          msrSelect,id,eq,[AdtCoordinatorID]],
         ColctCoordinators),

  msrNav(ColctCoordinators,
         [msrSize,eq,[[ptInteger,1]]],
         [[ptBoolean,true]]).

msrop(outactAdministrator,
      oeDeleteCoordinator,
      [post,Self,
       AdtCoordinatorID
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actAdministrator,TheActor),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),

/* PostF01 */
  msrNav([TheSystem],
        [rnctCoordinator,
         msrSelect,id,eq,[AdtCoordinatorID]],
        [ThectCoordinator]),
  
  msrNav([ThectCoordinator],
         [rnactCoordinator,msrForAll,msrIsKilled],
         [[ptBoolean,true]]),
  
 msrNav([ThectCoordinator],
         [msrIsKilled],
         [[ptBoolean,true]]),
  
  /* PostF02 */
  msrNav([TheActor],
         [rnInterfaceIN,
          ieCoordinatorDeleted,[]
         ],
         [[ptBoolean,true]]),
  
 /* Post Protocol:*/
/* PostP01 */
 true
 .