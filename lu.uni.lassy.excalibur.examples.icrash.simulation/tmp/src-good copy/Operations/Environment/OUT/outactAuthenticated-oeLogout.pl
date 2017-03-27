%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactAuthenticated,
      oeLogout,
      [preProtocol,Self
       ],
      []):-
/* Pre Protocol:*/
  msrVar(ctState,TheSystem),
  msrVar(actAuthenticated,TheActor),
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

msrop(outactAuthenticated,
      oeLogout,
      [preFunctional,Self
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
true
.

msrop(outactAuthenticated,
      oeLogout,
      [post,Self
       ],
       []):-

 msrVar(ctState,TheSystem),
 msrVar(actAuthenticated,TheactAuthenticated),

 msrVar(ptString,AptStringMessageForTheactAuthenticated),
 
/* Post Functional:*/
 msrNav([Self],[rnActor],[TheactAuthenticated]),
 msrNav([Self],[rnActor,rnSystem],[TheSystem]),

/* PostF01 */
 msrNav([AptStringMessageForTheactAuthenticated], 
        [eq,[[ptString,'You are logged out ! Good Bye ...']]],
        [[ptBoolean,true]]),
 msrNav([TheactAuthenticated],
        [rnInterfaceIN,
         ieMessage,[AptStringMessageForTheactAuthenticated]],
        [[ptBoolean,true]]),

 /* Post Protocol:*/
/* PostP01 */
msrNav([TheactAuthenticated], 
       [rnctAuthenticated,msmAtPost,vpIsLogged],
       [[ptBoolean,false]])
.