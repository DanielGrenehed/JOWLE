package engine.Common;

import engine.Main;

/**
 * This is a switch triggered by a given key
 * 
 * @author Daniel Amos Grenehed
 */
public class KeyButton {

	boolean isPressed = false;
	boolean wasPressed = false;
	double Key;
	Main mn;

	/**
	 * @param key,
	 *            fmn
	 */
	public KeyButton(double key, Main fmn) {
		mn = fmn;
		setKey(key);
	}

	/**
	 * changes what key triggers the switch
	 * 
	 * @param key
	 */
	public void setKey(double key) {
		this.Key = key;
	}

	/**
	 * @return the state of the boolean switch
	 */
	public boolean isOn() {
		if (mn.isPressed(Key) && !wasPressed) {
			isPressed = !isPressed;
			wasPressed = true;
		} else if (wasPressed && !mn.isPressed(Key)) {
			wasPressed = false;
		}

		return isPressed;
	}

	/**
	 * turns the switch on/off
	 * */
	public void toggle() {
		isPressed = !isPressed;
	}

}
