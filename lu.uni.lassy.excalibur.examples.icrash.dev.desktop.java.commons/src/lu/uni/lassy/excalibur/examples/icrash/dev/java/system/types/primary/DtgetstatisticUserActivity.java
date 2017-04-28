package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

public class DtgetstatisticUserActivity extends DtInteger implements JIntIs{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	public DtgetstatisticUserActivity(PtInteger v) {
		super(v);
	}
	
	/** The minimum number a longitude value could be. */
	private long _minNumber = 0;
	
	/** The maximum number a longitude value could be. */
	private long _maxNumber = 1500;
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#is()
	 */
	public PtBoolean is(){
		//http://www.geomidpoint.com/latlon.html
		return new PtBoolean(this.value.getValue() >= _minNumber && this.value.getValue() <= _maxNumber);
	}

}
