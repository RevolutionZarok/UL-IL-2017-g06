package lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbAbstract;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

public class TestCase_db_table_statistic extends DbAbstract  {


	/** The logger used to log issues or information. */
	static Logger log = Log4JUtils.getInstance().getLogger();
	
	
	/**
	 * The main method, which runs the test.
	 *
	 * @param args the arguments passed to the system, these are ignored
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server is not bound correctly in RMI settings
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {

		log.info("---- test insert-------");
		//**********************************************************
		//set up id
		DtCrisisID aId = new DtCrisisID(new PtString("1"));
		
		//**********************************************************
		//set up crisis' type
		EtCrisisType aType = EtCrisisType.small;
		
		
		
		//**********************************************************
		//set up instant
		int d,m,y,h,min,sec;
		d=26; m=11;	 y=2017;
		DtDate aDtDate = ICrashUtils.setDate(y, m, d);
		h=10; min=10; sec=16;
		DtTime aDtTime = ICrashUtils.setTime(h, min, sec);
		DtDateAndTime aInstant = new DtDateAndTime(aDtDate,aDtTime);
	
	
		//**********************************************************
		//set up comment
		DtComment aDtComment = new DtComment(new PtString("1 bicycle involved in an accident."));
		
		
		CtCrisis aCtCrisis = new CtCrisis();
		aCtCrisis.init(aId, aType,null, null, aInstant, aDtComment);
		//set null since it don´t need this for my test case only the time, type and id
		DbCrises.insertCrisis(aCtCrisis);
		

		log.info("---- test select order -------");
		//SQL statement
		/*"SELECT * FROM " + dbName + ".crises ORDER BY instant DESC WHERE id = "
		+ criseId;*/
		CtCrisis aCtCrisis3 = DbCrises.getCrisis(aId.value.getValue());
		log.debug("The retrieved crisis' id is " + aCtCrisis3.id.value.getValue());
		log.debug("The retrieved crisis' type is " + aCtCrisis3.type.toString());
		log.debug("The retrieved time is " + aCtCrisis3.instant.time.toString());
		log.debug("The retrieved crisis' comment is " + aCtCrisis3.comment.value.getValue());
		
		DbCrises.deleteCrisis(aCtCrisis3);
		
	}
	
	
}
