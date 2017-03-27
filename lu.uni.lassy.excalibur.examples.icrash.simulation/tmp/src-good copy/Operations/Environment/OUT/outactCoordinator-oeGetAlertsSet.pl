%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeGetAlertsSet,
      [preProtocol,Self,
       AetAlertStatus
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
      oeGetAlertsSet,
      [preFunctional,Self,
       AetAlertStatus
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
true
  .

msrop(outactCoordinator,
      oeGetAlertsSet,
      [post,Self,
       AetAlertStatus
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PostF01 */
 msrNav([TheSystem],
        [rnctAlert,
         msrSelect,
         status,etEq,[AetAlertStatus]],
        ColAlertSet),
 
  msrNav(ColAlertSet,
         [msrForAll,isSentToCoordinator,[TheActor]],
         [[ptBoolean,true]]),

 /* Post Protocol:*/
/* PostP01 */
 true
 .