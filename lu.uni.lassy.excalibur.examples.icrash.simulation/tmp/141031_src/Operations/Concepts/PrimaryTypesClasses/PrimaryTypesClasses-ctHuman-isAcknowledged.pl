%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
/*  DISCONTIGUOUS PREDICATES  */
:- multifile msrop/4.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

msrop(ctHuman,isAcknowledged,[Self],Result):-

/* Post F01 */
(msrVar(dtPhoneNumber,AdtPhoneNumber),
 msrVar(dtSMS,AdtSMS),
 
  msrNav([Self],
         [id,eq,[AdtPhoneNumber]],
         [[ptBoolean,true]]),
  msrNav([AdtSMS],
         [value,eq,[[ptString,'The handling of your alert by our services is in progress !']]],
         [[ptBoolean,true]]),
  msrNav([Self],
         [rnactComCompany,rnInterfaceIN,ieSmsSend,[AdtPhoneNumber,AdtSMS]],
         [[ptBoolean,true]])
)
-> Result = [ptBoolean,true]
; Result = [ptBoolean,false]
.

