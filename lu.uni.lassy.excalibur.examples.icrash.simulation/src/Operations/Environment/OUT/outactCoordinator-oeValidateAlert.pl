%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactCoordinator,
      oeValidateAlert,
      [preProtocol,Self,
       AdtAlertID
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
      oeValidateAlert,
      [preFunctional,Self,
       AdtAlertID
       ],
      []):-
/* Pre Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),

  msrVar(dtAlertID,AdtAlertID),
   
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PreF01 */
 msrNav([TheSystem],
        [rnctAlert,
         msrSelect,
         id,eq,[AdtAlertID]
        ],
        ColAlerts),

  msrNav(ColAlerts,
         [msrSize,eq,[[ptInteger,1]]],
         [[ptBoolean,true]])
  .

msrop(outactCoordinator,
      oeValidateAlert,
      [post,Self,
       AdtAlertID
       ],
       []):-

/* Post Functional:*/
  msrVar(ctState,TheSystem),
  msrVar(actCoordinator,TheActor),

  msrVar(ctAlert,TheAlert),
  msrVar(dtAlertID,AdtAlertID),
   
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  msrNav([Self],[rnActor],[TheActor]),
    
/* PostF01 */
 msrNav([TheSystem],
        [rnctAlert,
         msrSelect,
         id,eq,[AdtAlertID]],
        [TheAlert]),
 
  msrNav([TheAlert],
         [msmAtPost,status],
         [[etAlertStatus,valid]]),
  
  msrNav([TheActor],
         [rnInterfaceIN,
          ieMessage,[[ptString,'The Alert is now declared as valid !']]
         ],
         [[ptBoolean,true]]),

 /* Post Protocol:*/
/* PostP01 */
 true
 .