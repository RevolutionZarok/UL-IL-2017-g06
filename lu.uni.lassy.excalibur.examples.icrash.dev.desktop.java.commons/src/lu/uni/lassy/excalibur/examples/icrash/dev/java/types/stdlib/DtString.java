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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib;
import java.io.Serializable;

/**
 * The Class DtString, which holds a datatype of the primitive type of string.
 */
public class DtString implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/** The value of the primitive type. */
		public PtString value;
				
		/**
		 * Instantiates a new datatype of string.
		 *
		 * @param s The primitive type of string to create the datatype with
		 */
		public DtString(PtString s){
			value = s;
		}


		/**
		 * Are this and the other DtString provided considered equal?.
		 *
		 * @param s The datatype string to compare with
		 * @return If they are equal, returns true
		 */
		public PtBoolean eq(DtString s){
			boolean res = s.value.getValue().equals(this.value.getValue());
		
			if(res)
				return new PtBoolean(true);
			else
				return new PtBoolean(false);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){
			return value.getValue();
		}
}
