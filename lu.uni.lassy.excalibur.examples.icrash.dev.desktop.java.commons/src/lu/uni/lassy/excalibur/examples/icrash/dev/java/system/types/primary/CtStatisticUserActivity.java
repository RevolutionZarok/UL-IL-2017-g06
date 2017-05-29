package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtHour;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public class CtStatisticUserActivity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	/** */
	public DtStatisticUserActivity NumberOfUser;
	
	public DtTime Time;
	
	/** The date and time of the accident that is associated to the crisis. */
	public DtDateAndTime instant;
	
	public int counter;
	
	public CtStatisticUserActivity(DtStatisticUserActivity aNumberOf, DtTime aTime, DtDateAndTime ainstant){
		this.NumberOfUser = aNumberOf;
		this.Time = aTime;
		this.instant= ainstant;
		//return new PtBoolean(true);
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.NumberOfUser.getValue().toString();
	}
	
	public PtBoolean isSendTOAdministrator(ActAdministrator aActAdministrator)throws RemoteException {
		aActAdministrator.iegetStatisticUserActivity(this);
		return new PtBoolean(true);
	}
	@Override 
	public boolean equals(Object obj){
		if(!(obj instanceof CtStatisticUserActivity)){
			return false;
		}
		CtStatisticUserActivity aCtStatisticUserActivity = (CtStatisticUserActivity) obj;
		if(!(aCtStatisticUserActivity.NumberOfUser.getValue().toString().equals(this.NumberOfUser.getValue().toString())))
			return false;
		if(!(aCtStatisticUserActivity.instant.toString().equals(this.instant.toString())))
			return false;
		if(!(aCtStatisticUserActivity.Time.toString().equals(this.Time.toString())))
			return false;
		return true;
	}
	public void iecalculeStatisticUserActivity(){
		System.out.println("1111");
		try{
			Class.forName("org.postgresql.Driver" );
			Connection con = java.sql.DriverManager.getConnection("","icrashfx","il2_icrash");
			PreparedStatement s = (PreparedStatement) con.createStatement();
			ArrayList<String> Arraylist = new ArrayList<String>();
			
			ResultSet res;
			res = s.executeQuery("SELECT * FROM StatisticLogin WHERE instant = ?");
			s.setInt(1,24);
			for (int i = 0; res.next() ;i++){
				Arraylist.add(res.getString("instant"));
			}
			
			for (int j = 0; j <Arraylist.size(); j++){
				System.out.println("The ist is"+ Arraylist.get(j));
			}
			
		con.close();	
		}catch(Exception e){
			System.out.println("Erros in CtStatisticUserActivity");
		}
	
		
	}
	
	public DtStatisticUserActivity getNumberOfUser(){
		return NumberOfUser;
	}
	public DtTime getTime(){
		return Time; 
	}
	public DtHour geDateAndTime(){
		return instant.time.hour;
	}
}
