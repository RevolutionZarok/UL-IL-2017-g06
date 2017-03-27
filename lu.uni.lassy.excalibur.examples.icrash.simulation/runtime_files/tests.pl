
nico:-
(
        (findall(A,(msrop(A,create,X,Y1),
                    isTypeOf(A,Y1)),
                 L),
        length(L,N),
        allTypesDefinition(AllTypesList),
        findall(B,(member([_C,[B,_D]],AllTypesList)),
                L2),
        length(L2,P),

        intersection(L,L2,INT),
        symdiff(L,L2,DIF),
        
        samsort(L,SL),samsort(L2,SL2),samsort(INT,SINT),samsort(DIF,SDIF),
        write(SL),write('  \n '),write(SL2),write('  \n '),

        write(SINT),write('  \n '),write(SDIF),write('  \n '),
        
        IntegratedGoal=..[=,N,P],
        IntegratedGoal
        )
->      TestResult = true  
;       TestResult = false
).

