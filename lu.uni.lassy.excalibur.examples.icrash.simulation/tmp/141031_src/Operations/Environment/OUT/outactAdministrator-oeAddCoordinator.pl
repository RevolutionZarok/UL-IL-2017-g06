%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactAdministrator,
      oeAddCoordinator,
      [preProtocol,Self,
       AdtCoordinatorID,
       AdtLogin,
       AdtPassword
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

/* PreP02 */
 msrNav([TheActor],
        [rnctAuthenticated,vpIsLogged],
        [[ptBoolean,true]])

  .

msrop(outactAdministrator,
      oeAddCoordinator,
      [preFunctional,Self,
       AdtCoordinatorID,
       AdtLogin,
       AdtPassword
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
         [msrIsEmpty],
         [[ptBoolean,true]])
  .

msrop(outactAdministrator,
      oeAddCoordinator,
      [post,Self,
       AdtCoordinatorID,
       AdtLogin,
       AdtPassword
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actAdministrator,TheActor),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
  msrVar(actCoordinator,TheactCoordinator),
  msrVar(ctCoordinator,ThectCoordinator),
  
/* PostF01 */
  msrNav([TheactCoordinator],
         [init,[]],
         [[ptBoolean,true]]),

/* PostF02 */
  msrNav([ThectCoordinator],
         [init,[AdtCoordinatorID,AdtLogin,AdtPassword]],
         [[ptBoolean,true]]),

/* PostF03 */
  msrNav([TheactCoordinator],
         [msmAtPost,rnctCoordinator],
         [ThectCoordinator]),
  
/* PostF04 */  
   msrNav([ThectCoordinator],
          [msmAtPost,rnactAuthenticated],
          [TheactCoordinator]),
   
/* PostF05 */  
   msrNav([TheActor],
          [rnInterfaceIN,
           ieCoordinatorAdded,[]
          ],
          [[ptBoolean,true]]),
   
 /* Post Protocol:*/
/* PostP01 */
 true
 .