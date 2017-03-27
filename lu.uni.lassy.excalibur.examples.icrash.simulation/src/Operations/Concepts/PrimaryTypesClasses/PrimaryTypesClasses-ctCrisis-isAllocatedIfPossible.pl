%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctCrisis,isAllocatedIfPossible,[Self],
      Result):-
(
  msrVar(ctState,TheSystem),
  msrNav([Self],[rnSystem],[TheSystem]),

  msrVar(actCoordinator,TheCoordinatorActor),
  msrVar(ctCoordinator,TheCoordinator),
  msrVar(ptString,TheMessage),
  msrVar(ptString,TheCrisisIDptString),
    
  (   
  /* Post F01 */
  msrNav([Self],
         [maxHandlingDelayPassed,[]],
         [[ptBoolean,true]]),
  
  ( msrNav([TheSystem],
           [rnactCoordinator,msrIsEmpty],
           [[ptBoolean,false]])
    -> ( 
          /* Post F02 */
          msrNav([TheSystem],
                [rnactCoordinator,msrAny,msrTrue],
                [TheCoordinatorActor]),

          msrNav([TheCoordinatorActor],
                 [rnctCoordinator],
                 [TheCoordinator]),

          msrNav([Self],
                 [msmAtPost,rnHandler],
                 [TheCoordinator]),

           msrNav([Self],
                  [msmAtPost,status],
                  [[etCrisisStatus,handled]]),
           
           msrNav([Self],
                  [id,value],
                  [TheCrisisIDptString]),
           
          msrNav([[ptString,'You are now considered as handling the crisis having ID: ']],
                 [ptStringConcat,[TheCrisisIDptString]],
                 [TheMessage]),
          
          msrNav([TheCoordinatorActor],
                 [rnInterfaceIN,
                  ieMessage,[TheMessage]
                 ],
                 [[ptBoolean,true]])
       )
  ; (  /* Post F03 */
       msrNav([TheSystem],
             [rnactAdministrator,msrForAll,rnInterfaceIN,
              ieMessage,[[ptString,'Please add new coordinators to handle pending crisis !']]],
             [[ptBoolean,true]])
    )
  )
  )
)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.
