%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
msrop(outactActivator,
      oeSetClock,
      [preProtocol,Self,
       AcurrentClock
       ],
      []):-
/* Pre Protocol:*/
/* PreP01 */
 msrVar(ctState,TheSystem),
 msrVar(ptBoolean,AvpStarted),

 msrNav([Self],[rnActor,rnSystem],[TheSystem]), 
 
 msrNav([Self],[rnActor,rnSystem,vpStarted],[AvpStarted]),
 AvpStarted = [ptBoolean,true],
 
 msrNav([TheSystem],
       [clock,lt,[AcurrentClock]],
       [[ptBoolean,true]])
 .

msrop(outactActivator,
      oeSetClock,
      [preFunctional,Self,
       AcurrentClock
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
true.

msrop(outactActivator,
      oeSetClock,
      [post,Self,
       AcurrentClock
       ],
       []):-

 msrVar(ctState,TheSystem),

/* Post Functional:*/

 msrNav([Self],[rnActor,rnSystem],[TheSystem]),

/* PostF01 */
  msrNav([TheSystem], 
         [msmAtPost,clock],
         [AcurrentClock]),
  
 /* Post Protocol:*/
/* PostP01 */
 true
 .