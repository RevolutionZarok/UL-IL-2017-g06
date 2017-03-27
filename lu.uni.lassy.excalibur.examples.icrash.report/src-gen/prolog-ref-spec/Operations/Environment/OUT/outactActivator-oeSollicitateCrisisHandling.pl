%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------

msrop(outactActivator,
      oeSollicitateCrisisHandling,
      [preProtocol,Self
       ],
      []):-
/* Pre Protocol:*/
  msrVar(ctState,TheSystem),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
  
  msrVarCol(ctCrisis,_,ColctCrisisToHandle),
  
/* PreP01 */
 msrNav([TheSystem],
        [vpStarted],
        [[ptBoolean,true]]),
 
/* PreP02 */
 msrNav([TheSystem],
        [rnctCrisis,msrSelect,
         handlingDelayPassed,[]
        ],
        ColctCrisisToHandle),
 
 msrNav(ColctCrisisToHandle,
        [msrSize,geq,[[ptInteger,1]]],
        [[ptBoolean,true]])
.

msrop(outactActivator,
      oeSollicitateCrisisHandling,
      [preFunctional,Self
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
true.

msrop(outactActivator,
      oeSollicitateCrisisHandling,
      [post,Self
       ],
       []):-

 msrVar(ctState,TheSystem),
 msrVar(dtComment,AMessageForCrisisHandlers),
 msrVar(dtDateAndTime, TheClock),
 msrVarCol(ctCrisis,_,ColctCrisisToAllocateIfPossible),
 
/* Post Functional:*/
 msrNav([Self],[rnActor,rnSystem],[TheSystem]),
 
 /* PostF01 */
 msrNav([TheSystem],
        [rnctCrisis,msrSelect,
         maxHandlingDelayPassed,[]
        ],
        ColctCrisisToAllocateIfPossible),

msrNav(ColctCrisisToAllocateIfPossible,
       [msrForAll,isAllocatedIfPossible,[]],
       [[ptBoolean,true]]),

 /* PostF02 */
 msrNav([TheSystem],
        [rnctCrisis,msrSelect,
         handlingDelayPassed,[]
        ],
        ColctCrisisToHandle),
  
  msrNav(ColctCrisisToHandle,
         [msrColSubtract,[ColctCrisisToAllocateIfPossible]
         ],
         ColctCrisisToRemind),
  
  (msrNav(ColctCrisisToRemind,
          [msrSize,geq,[[ptInteger,1]]],
          [[ptBoolean,true]])
   -> (msrNav([AMessageForCrisisHandlers],
              [value],
              [[ptString,'There are alerts pending since more than the defined delay. Please REACT !']]),

       msrNav([TheSystem],
              [rnactAdministrator,rnInterfaceIN,
               ieMessage,[AMessageForCrisisHandlers]
              ],
              [[ptBoolean,true]]),

       msrNav([TheSystem],
              [rnactCoordinator,msrForAll,rnInterfaceIN,
               ieMessage,[AMessageForCrisisHandlers]
              ],
              [[ptBoolean,true]])
      )
  ; true
  ),
  
/* Post Protocol:*/
/* PostP01 */
 msrNav([TheSystem],
        [clock],
        [TheClock]),

  msrNav([TheSystem],
         [msmAtPost,vpLastReminder],
         [TheClock])
 .