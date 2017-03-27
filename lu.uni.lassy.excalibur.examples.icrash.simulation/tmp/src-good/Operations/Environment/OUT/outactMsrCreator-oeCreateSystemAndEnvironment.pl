%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*
/***************************************
MSRCreatorActor
***************************************/

/*** createSystemAndEnvironment ***/

msrop(outactMsrCreator,
      oeCreateSystemAndEnvironment,
      [preFunctional,_Self,_AqtyComCompanies],
      []):-
 true.

msrop(outactMsrCreator,
      oeCreateSystemAndEnvironment,
      [preProtocol,_Self,_AqtyComCompanies],
      []):-
 true.

msrop(outactMsrCreator,
      oeCreateSystemAndEnvironment,
      [post,_Self,AqtyComCompanies],
      []):-

  msrVar(ctState,TheSystem),
  msrVar(actMsrCreator,AactMsrCreator),
  msrVar(actAdministrator,AactAdministrator),

  msrVar(dtInteger, AnextValueForAlertID), 
  msrVar(dtInteger, AnextValueForCrisisID), 
  msrVar(dtDateAndTime, Aclock),
  msrVar(dtSecond, AcrisisReminderPeriod),
  msrVar(dtSecond, AmaxCrisisReminderPeriod),
  msrVar(ptBoolean, AvpStarted),

  /* PostF01 -- MUST ALWAYS BE MADE FIRST -- */ 
  msrNav([AnextValueForAlertID],
         [value,eq,[[ptInteger,1]]],
         [[ptBoolean,true]]),
  
  msrNav([AnextValueForCrisisID],
         [value,eq,[[ptInteger,1]]],
         [[ptBoolean,true]]),
  
  msrNav([Aclock],
         [isNow,[]],
         [[ptBoolean,true]]),
  
  msrNav([AcrisisReminderPeriod],
         [value,eq,[[ptInteger,300]]],
         [[ptBoolean,true]]),

  msrNav([AmaxCrisisReminderPeriod],
         [value,eq,[[ptInteger,1200]]],
         [[ptBoolean,true]]),
  
  msrNav([AvpStarted],
         [],
         [[ptBoolean,true]]),

  msrNav([TheSystem],
         [init,[AnextValueForAlertID,
                AnextValueForCrisisID,
                Aclock,
                AcrisisReminderPeriod,
                AmaxCrisisReminderPeriod,
                Aclock,
                AvpStarted]
                ],
         [[ptBoolean,true]]),
  
/* PostF02*/ 
  msrNav([AactMsrCreator],
         [init,[]],
         [[ptBoolean,true]]),
 
 /* PostF03 */ 
  msrVarCol(actComCompany,AqtyComCompanies,AactComCompanyCol),

  msrNav(AactComCompanyCol,
         [msrForAll,init,[]],
         [[ptBoolean,true]]),
          
 /* PostF04*/ 
  msrNav([AactAdministrator],
         [init,[]],
         [[ptBoolean,true]]),
  
  /* PostF05*/ 
  msrVar(actActivator,AactActivator),
  msrNav([AactActivator],
         [init,[]],
         [[ptBoolean,true]]),

/* PostF06 */ 
  msrVar(ctAdministrator,ActAdministrator),
  msrVar(dtLogin,AdtLogin),
  msrVar(dtPassword,AdtPassword),
  
  msrNav([AdtLogin],
         [value,eq,[[ptString,'icrashadmin']]],
         [[ptBoolean,true]]),
  
  msrNav([AdtPassword],
         [value,eq,[[ptString,'7WXC1359']]],
         [[ptBoolean,true]]),
  
  msrNav([ActAdministrator],
         [init,[AdtLogin,AdtPassword]],
         [[ptBoolean,true]]),

 /* PostF07*/ 
  msrNav([ActAdministrator],
         [msmAtPost,rnactAuthenticated],
         [AactAdministrator]),
  
/* Post Protocol:*/
/* PostP01 */
true
.

