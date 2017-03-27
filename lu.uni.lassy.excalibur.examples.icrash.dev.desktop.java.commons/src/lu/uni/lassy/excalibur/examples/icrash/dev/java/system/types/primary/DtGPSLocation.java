/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;
import java.io.Serializable;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class DtGPSLocation, which holds a datatype of the GPS location.
 */
public class DtGPSLocation implements Serializable, JIntIs {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/** The datatype latitude. */
		public DtLatitude latitude;
		
		/** The datatype longitude. */
		public DtLongitude longitude;
		
		/**
		 * *****************
		 * Taken from:
		 * http://stackoverflow.com/questions/120283/how-can-i-measure-distance-and-create-a-bounding-box-based-on-two-latitudelongi
		 * 
		 * ****************
		 *
		 * @param latitudeOne The 1st latitude to measure from
		 * @param longitudeOne The 1st longitude to measure from
		 * @param latitudeTwo The 2nd latitude to measure from
		 * @param longitudeTwo The 2nd longitude to measure from
		 * @return distance, in a straight line, from the two points
		 */ 
		static Double distanceBetweenTwoLocationsInKm(Double latitudeOne, Double longitudeOne, 
														Double latitudeTwo, Double longitudeTwo) {
	        if (latitudeOne == null || latitudeTwo == null || longitudeOne == null || longitudeTwo == null) {
	            return null;
	        }
	        Double earthRadius = 6371.0;
	        Double diffBetweenLatitudeRadians = Math.toRadians(latitudeTwo - latitudeOne);
	        Double diffBetweenLongitudeRadians = Math.toRadians(longitudeTwo - longitudeOne);
	        Double latitudeOneInRadians = Math.toRadians(latitudeOne);
	        Double latitudeTwoInRadians = Math.toRadians(latitudeTwo);
	        Double a = Math.sin(diffBetweenLatitudeRadians / 2) * Math.sin(diffBetweenLatitudeRadians / 2) + Math.cos(latitudeOneInRadians) * Math.cos(latitudeTwoInRadians) * Math.sin(diffBetweenLongitudeRadians / 2)
	                * Math.sin(diffBetweenLongitudeRadians / 2);
	        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	        return (earthRadius * c);
    }

		/**
		 * Instantiates a new datatype GPS location.
		 *
		 * @param aLatitude The datatype of latitude to assign to the GPS location
		 * @param aLongitude The datatpye of longitude to assign to the GPS location
		 */
		public DtGPSLocation(DtLatitude aLatitude,DtLongitude aLongitude){
			latitude = aLatitude;
			longitude = aLongitude;
		}
		
		/* (non-Javadoc)
		 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#is()
		 */
		public PtBoolean is(){
			return new PtBoolean(this.latitude.is().getValue() && this.longitude.is().getValue());
		}
		
		/**
		 * Checks if this and another passed latitude and longitude are near to each other.
		 *
		 * @param aLatitude The latitude to check if this is near to
		 * @param aLongitude The longitude to check if this is near to
		 * @return If it is within a certain distance (As a straight line), then return true. Otherwise return false
		 */
		public PtBoolean isNearTo(DtLatitude aLatitude,DtLongitude aLongitude){
	
			double lat1,lat2,long1,long2,dist;
			
			lat1 = this.latitude.value.getValue();
			lat2 = aLatitude.value.getValue();
			long1 = this.longitude.value.getValue();
			long2 = aLongitude.value.getValue();
	
			dist = distanceBetweenTwoLocationsInKm(lat1,long1,lat2,long2);
	
			if(dist*1000 < 100)
				return new PtBoolean(true);
			else
				return new PtBoolean(false);
		}
		
		/* (non-Javadoc)
		 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#getExpectedDataStructure()
		 */
		public PtString getExpectedDataStructure(){
			return new PtString("Expected strucutre of the GPS consisits of:\n" + this.latitude.getExpectedDataStructure().getValue() + "\n" + this.longitude.getExpectedDataStructure().getValue()); 
		}


}
