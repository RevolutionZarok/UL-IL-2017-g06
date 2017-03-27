%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%-------------------------------------------------------
nico(A):-
  trace,
  write('here'),
  write('\n').

msrop(outactComCompany,
      oeAlert,
      [preProtocol,Self,
       AetHumanKind,
       AdtDate,
       AdtTime,
       AdtPhoneNumber,
       AdtGPSLocation,
       AdtComment
       ],
      []):-
/* Pre Protocol:*/
  msrVar(ctState,TheSystem),
  msrNav([Self],[rnActor,rnSystem],[TheSystem]),
/* PreP01 */
 msrNav([TheSystem],
        [vpStarted],
        [[ptBoolean,true]])
 .

msrop(outactComCompany,
      oeAlert,
      [preFunctional,Self,
       AetHumanKind,
       AdtDate,
       AdtTime,
       AdtPhoneNumber,
       AdtGPSLocation,
       AdtComment
       ],
      []):-
/* Pre Functional:*/
/* PreF01 */
  msrVar(ctState,TheSystem),
  msrNav([Self],
         [msmAtPre,rnActor,rnSystem],
         [TheSystem]),
  
  ( msrNav([TheSystem],[clock,date,gt,[AdtDate]],[[ptBoolean,true]])
   ; (msrNav([TheSystem],[clock,date,eq,[AdtDate]],[[ptBoolean,true]])
      , msrNav([TheSystem],[clock,time,gt,[AdtTime]],[[ptBoolean,true]])
     )
  )
.

msrop(outactComCompany,
      oeAlert,
      [post,Self,
       AetHumanKind,
       AdtDate,
       AdtTime,
       AdtPhoneNumber,
       AdtGPSLocation,
       AdtComment
       ],
       []):-

 msrVar(ctState,TheSystem),
 msrVar(ctHuman,ActHuman),
 msrVar(actComCompany,TheactComCompany),
 msrVar(ctAlert,ActAlert), 
 msrVar(dtDateAndTime,AAlertInstant),
 msrVar(etAlertStatus,AetAlertStatus),
% msrVar(ctAlert,ActAlertNearBy),
 msrVar(ctCrisis,ActCrisis),
 msrVar(dtCrisisID,AdtCrisisID),
% msrVar(etCrisisType,AetCrisisType),
 msrVar(etCrisisStatus,AetCrisisStatus),
 msrVar(dtDateAndTime,ACrisisInstant),
 msrVar(dtComment,ACrisisdtComment),
%  msrVar(ptString,AptStringMessage),
 msrVar(dtSMS,AdtSMS),
 msrVar(dtAlertID,AdtAlertID),
 
%  msrVar(ptInteger,TheNextptIntegerValue),
%  msrVar(ptInteger,UpdatedNextptIntegerValue),
%  msrVar(inactComCompany,TheComCompanyIN),
%  msrVar(dtComment,TheCommentStored), 
%  msrVar(dtString,TheCommentStoreddtString), 

/* Post Functional:*/

 msrNav([Self],[rnActor],[TheactComCompany]),
 msrNav([Self],[rnActor,rnSystem],[TheSystem]),

/* PostF01 */
  msrNav([TheSystem], 
         [nextValueForAlertID],
         [PrenextValueForAlertID]),
  msrNav([PrenextValueForAlertID],
         [add,[[dtInteger,[[value,[ptInteger,1]]],[]]]],
         [PostnextValueForAlertID]),
  msrNav([TheSystem], 
         [msmAtPost,nextValueForAlertID],
         [PostnextValueForAlertID]),

  /* PostF02 */
  msrNav([AAlertInstant],[date],[AdtDate]),
  msrNav([AAlertInstant],[time],[AdtTime]),

  msrNav([AetAlertStatus], 
         [],
         [[etAlertStatus,pending]]),
        
  msrNav([TheSystem], 
         [nextValueForAlertID,
          todtString,[],eq,[AdtAlertID]],
         [[ptBoolean,true]]),

  msrNav([ActAlert],
         [init,[AdtAlertID,
                AetAlertStatus,
                AdtGPSLocation,
                AAlertInstant,
                AdtComment]],
         [[ptBoolean,true]]),
      
  /* PostF03 */
  msrNav([TheSystem], 
          [rnctAlert,
           msrSelect,location,isNearTo,[AdtGPSLocation]],
           ColctAlertsNearBy),

  ( (msrNav(ColctAlertsNearBy,
            [msrIsEmpty],
            [[ptBoolean,true]])
     )
  -> (
       msrNav([TheSystem], 
              [nextValueForCrisisID],
              [PrenextValueForCrisisID]),
       msrNav([PrenextValueForCrisisID],
              [add,[[dtInteger,[[value,[ptInteger,1]]],[]]]],
              [PostnextValueForCrisisID]),
       msrNav([TheSystem], 
              [msmAtPost,nextValueForCrisisID],
              [PostnextValueForCrisisID]),
        
      msrNav([TheSystem], 
             [nextValueForCrisisID,
              todtString,[],eq,[AdtCrisisID]],
             [[ptBoolean,true]]),

      msrNav([AdtCrisisType],[],[[etCrisisType,small]]),
      msrNav([AetCrisisStatus],[],[[etCrisisStatus,pending]]),
      msrNav([ACrisisInstant],[],[AAlertInstant]),
      msrNav([ACrisisdtComment],
             [value],
             [[ptString,'no reporting yet defined']]),
      msrNav([ActCrisis],[init,[AdtCrisisID,
                                AdtCrisisType,
                                AetCrisisStatus,
                                AdtGPSLocation,
                                ACrisisInstant,
                                ACrisisdtComment]],
             [[ptBoolean,true]])
      
             
        )
  ; (
      msrNav(ColctAlertsNearBy,
            [rnTheCrisis,msrAny,msrTrue],
            [ActCrisis])
    )
  ),

  /* PostF04 */

  msrNav([ActAlert],
         [msmAtPost,rnTheCrisis],
         [ActCrisis]),

      
/* PostF05 */

 msrNav([TheSystem], 
        [rnctHuman,
         msrSelect,id,eq,[AdtPhoneNumber]],
        HumanCol1),
 
 msrNav(HumanCol1,
        [msrSelect,kind,etEq,[AetHumanKind]],
        HumanCol2),
 
 (msrNav(HumanCol2,[msrIsEmpty],[[ptBoolean,true]])
  -> (msrNav([ActHuman],
             [init,[AdtPhoneNumber,AetHumanKind]],
             [[ptBoolean,true]]),
      msrNav([ActHuman],
             [msmAtPost,rnactComCompany],
             [TheactComCompany])
     )
 ; msrNav(HumanCol2,
          [msrAny],
          [ActHuman])
 ),
 
msrNav([ActHuman],
       [rnSignaled,msrIncluding,[ActAlert]],
       ColAlerts),

msrNav([ActHuman],
       [msmAtPost,rnSignaled],
       ColAlerts),

/* PostF06 */
msrNav([AdtSMS],
       [value],
       [[ptString,'Your alert has been registered. We will handle it and keep you informed']]),
msrNav([TheactComCompany],
       [rnInterfaceIN,
        ieSmsSend,[AdtPhoneNumber,
                   AdtSMS]],[[ptBoolean,true]]),


/*
    
  */

 /* Post Protocol:*/
/* PostP01 */
 true
 .