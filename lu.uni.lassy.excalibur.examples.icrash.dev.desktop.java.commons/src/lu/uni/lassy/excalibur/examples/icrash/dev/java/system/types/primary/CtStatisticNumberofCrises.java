package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public class CtStatisticNumberofCrises {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	public DtTime Time;
	
	public DtInteger NumberofSendingCrises;

	public  DtDateAndTime instant;
	
	public CtStatisticNumberofCrises(DtTime aTime, DtInteger aNumberofSendingCrises,  DtDateAndTime ainstant){
		this.Time = aTime;
		this.NumberofSendingCrises = aNumberofSendingCrises;
		this.instant =ainstant;
		//return new PtBoolean(true);
		
	}
	
	public PtBoolean isSendTOAdministrator(ActAdministrator aActAdministrator)throws RemoteException {
		aActAdministrator.iegetStatisticNumberOfCrises(this);
		return new PtBoolean(true);
	}
	@Override 
	public boolean equals(Object obj){
		if(!(obj instanceof CtStatisticNumberofCrises)){
			return false;
		}
		CtStatisticNumberofCrises aCtStatisticNumberofCrises = (CtStatisticNumberofCrises) obj;
		if(!(aCtStatisticNumberofCrises.Time.toString().equals(this.Time.toString())))
			return false;
		if(!(aCtStatisticNumberofCrises.NumberofSendingCrises.value.equals(this.NumberofSendingCrises.value)))
			return false;
		if (!(aCtStatisticNumberofCrises.instant.time.hour.toString().equals(this.instant.time.hour.toString())))
			return false;
		return true;
	}
	
	public DtTime getTime(){
		return Time; 
	}
	public DtInteger getNumberofSendingCrises(){
		return NumberofSendingCrises;
	}
	public DtDateAndTime geTime(){
		return instant;
	}
public void iecalculeStatisticNumberOfCrises(){
		
		try{
			Class.forName("org.postgresql.Driver" );
			Connection con = java.sql.DriverManager.getConnection("","icrashfx","il2_icrash");
			Statement s = con.createStatement();
			String arraytime[] = null;
			
			ResultSet res;
			res = s.executeQuery("SELECT * FROM StatisticNumberofCrises");
			for (int i = 0; res.next() ;i++){
			
				//variable instant colome  index 
				arraytime[i] = res.getString("instant");
				
			}
			System.out.println("NULLLLLL");
			String Result[][];
			int time = 0; 
			int j = 0;
			//use string !! problem to make if (int)
			while(arraytime != null){
				
				switch (arraytime[j]){
				case "00":
					break; 
				default: 
					break;
				}
				
			}
			for(int i = 0; i< 24;i++){
				
			}
		con.close();	
		}catch(Exception e){
			System.out.println("Erros in CtStatisticNumberofCrises");
		}
	
		
	}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString(){
	return this.NumberofSendingCrises.value.toString();
}
}
