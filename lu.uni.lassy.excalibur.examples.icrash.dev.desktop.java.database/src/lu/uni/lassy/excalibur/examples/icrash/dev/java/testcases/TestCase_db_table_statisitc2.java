package lu.uni.lassy.excalibur.examples.icrash.dev.java.testcases;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbAlerts;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db.DbCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

public class TestCase_db_table_statisitc2 {

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
		Random rn = new Random();
		Integer val = new Integer(rn.nextInt(100));
		DtAlertID aId = new DtAlertID(new PtString(val.toString()));
		
		//**********************************************************
		//set up status
		EtAlertStatus aStatus = EtAlertStatus.pending;

		//**********************************************************
		//set up location
		DtLatitude aDtLatitude = new DtLatitude(new PtReal(49.627675));
		DtLongitude aDtLongitude = new DtLongitude(new PtReal(6.159590));
		DtGPSLocation aDtGPSLocation = new DtGPSLocation(aDtLatitude,aDtLongitude);
		
		
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
		DtComment aDtComment = new DtComment(new PtString("13 cars involved in an accident."));
		
		
		CtAlert aCtAlert = new CtAlert();
		aCtAlert.init(aId, aStatus,aDtGPSLocation,aInstant, aDtComment);
		
		DbAlerts.insertAlert(aCtAlert);
		log.info("---- test select  order -------");
		//SQL statement
		/*"SELECT * FROM " + dbName + ".alerts ORDER BY instant DESC WHERE id = "
		+ alertId;*/
		CtAlert aCtAlert3 = DbAlerts.getAlert(aId.value.getValue());
		log.debug("The retrieved crisis' id is " + aCtAlert3.id.value.getValue());
		log.debug("The retrieved crisis' type is " + aCtAlert3.status.toString());
		log.debug("The retrieved time is " + aCtAlert3.instant.time.toString());
		log.debug("The retrieved crisis' comment is " + aCtAlert3.comment.value.getValue());
		DbAlerts.deleteAlert(aCtAlert3);
		

		
	}
}
