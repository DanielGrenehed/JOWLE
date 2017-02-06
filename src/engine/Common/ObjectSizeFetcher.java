package engine.Common;

import java.lang.instrument.Instrumentation;

/**
 * @author Daniel Amos Grenehed
 * @see http://stackoverflow.com/questions/9368764/calculate-size-of-object-in-java
 * 
 * */
public class ObjectSizeFetcher {
	private Instrumentation instrumentation;

	public ObjectSizeFetcher() {
		this.instrumentation = new Instrument();
	}
	
	/**
	 * Changes instrumentation
	 * @param inst
	 * */
	public void premain(Instrumentation inst) {
		this.instrumentation = inst;
	}

	/**
	 * @return the size of the object o
	 * @param o
	 * */
	public long getObjectSize(Object o) {
		return instrumentation.getObjectSize(o);
	}
}
