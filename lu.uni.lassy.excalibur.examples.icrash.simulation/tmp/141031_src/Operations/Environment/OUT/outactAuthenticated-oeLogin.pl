%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactAuthenticated,
      oeLogin,
      [preProtocol,Self,
       AdtLogin,
       AdtPassword
       ],
      []):-
/* Pre Protocol:*/
  msrVar(ctState,TheSystem),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
/* PreP01 */
 msrNav([TheSystem],
        [vpStarted],
        [[ptBoolean,true]]),
 
 msrNav([Self],
        [rnActor,rnctAuthenticated,vpIsLogged],
        [[ptBoolean,false]])
 .

msrop(outactAuthenticated,
      oeLogin,
      [preFunctional,Self,
       AdtLogin,
       AdtPassword
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
true
.

msrop(outactAuthenticated,
      oeLogin,
      [post,Self,
       AdtLogin,
       AdtPassword
       ],
       []):-

 msrVar(ctState,TheSystem),
 msrVar(actAuthenticated,TheactAuthenticated),
 
 msrVar(ptString,AptStringMessageForTheactAuthenticated),
 msrVar(ptString,AptStringMessageForTheactAdministrator),
 
/* Post Functional:*/

 msrNav([Self],[rnActor],[TheactAuthenticated]),
 msrNav([Self],[rnActor,rnSystem],[TheSystem]),

/* PostF01 */

  ( (msrNav([TheactAuthenticated], 
            [rnctAuthenticated,pwd],
            [AdtPassword]),
     msrNav([TheactAuthenticated], 
            [rnctAuthenticated,login],
            [AdtLogin])
    )
  -> ( msrNav([AptStringMessageForTheactAuthenticated], 
              [eq,[[ptString,'You are logged ! Welcome ...']]],
              [[ptBoolean,true]]),
       msrNav([TheactAuthenticated],
              [rnInterfaceIN,
               ieMessage,[AptStringMessageForTheactAuthenticated]],
              [[ptBoolean,true]])
     )
  ; ( msrNav([AptStringMessageForTheactAuthenticated], 
              [eq,[[ptString,'Wrong identification information ! Please try again ...']]],
              [[ptBoolean,true]]),
       msrNav([TheactAuthenticated],
              [rnInterfaceIN,
               ieMessage,[AptStringMessageForTheactAuthenticated]],
              [[ptBoolean,true]]),
       
       msrNav([AptStringMessageForTheactAdministrator], 
              [eq,[[ptString,'Intrusion tentative !']]],
              [[ptBoolean,true]]),
       msrNav([TheSystem],
              [rnactAdministrator,rnInterfaceIN,
               ieMessage,[AptStringMessageForTheactAdministrator]],
              [[ptBoolean,true]])
       )
  ),

 /* Post Protocol:*/
/* PostP01 */
  ( (msrNav([TheactAuthenticated], 
            [rnctAuthenticated,pwd],
            [AdtPassword]),
     msrNav([TheactAuthenticated], 
            [rnctAuthenticated,login],
            [AdtLogin])
    )
  -> (msrNav([TheactAuthenticated], 
             [rnctAuthenticated,msmAtPost,vpIsLogged],
             [[ptBoolean,true]])
       )
  ; true
  )
 .