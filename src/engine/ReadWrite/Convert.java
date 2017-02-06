package engine.ReadWrite;

/**
 * @author Daniel Amos Grenehed
 */
public class Convert {

	String[] numbers = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." };

	/**
	 * checks
	 * 
	 * @param text
	 * @return boolean
	 */
	public boolean isNumber(String text) {

		if (text.length() > 1) {
			return isNumbers(text);
		}

		for (int i = 0; i < numbers.length; i++) {
			if (text.equals(numbers[i])) {
				// System.out.println(text + " is a Number");
				return true;
			}
		}

		// System.out.println(text + " is not a Number");
		return false;
	}

	/**
	 * checks if the given string is only made up of digits, this i less
	 * effective than the isNumber() method so please use that instead
	 * 
	 * @param text
	 * @return boolean *
	 */
	public boolean isNumbers(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (!isNumber(text.substring(i, i + 1)))
				return false;
		}
		return true;
	}

	/**
	 * finds a string that is pure numbers and converts it into a double
	 * 
	 * @param text
	 * @return double
	 */
	public double getNumber(String text) {
		boolean numfun = false;
		int start = 0, end = 0;
		for (int i = 0; i < text.length(); i++) {
			if (isNumber(text.substring(i, i + 1))) {
				numfun = true;
				start = i;
				break;
			}
		}
		if (numfun) {
			for (int i = start; i < text.length(); i++) {
				if (isNumber(text.substring(i, i + 1))) {
					end = i;
				} else {

				}
			}
			// System.out.println("start:" + start + " end:" + end);
			return Double.parseDouble(text.substring(start, end + 1));
		}
		return 0;
	}

	public double getDoubleFromArray(String[] text, String value) {
		return getNumber(getString(text, value));
	}

	public boolean getBooleanFromArray(String[] text, String value) {
		return getBoolean(getString(text, value));
	}

	String[] tf = { "TRUE", "FALSE" };

	/**
	 * this method should return a boolean from the given text string but is
	 * badly written so please use getboolean() instead as it is a better
	 * written an more effective method that does exactly the same.
	 * 
	 * @param text
	 * @return boolean
	 */
	public boolean getBoolean(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (text.substring(i, i + tf[0].length()).toUpperCase().equals(tf[0])) {
				// equals true
				return true;
			}
			if (text.substring(i, i + tf[1].length()).toUpperCase().equals(tf[1])) {
				// equals false
				return false;
			}
		}
		System.out.println("Unable to find a boolean in string : " + text);
		return false;
	}

	/**
	 * Effective method for getting a boolean from a string
	 * 
	 * @param text
	 * @return boolean
	 */
	public boolean getboolean(String text) {
		if (text.toLowerCase().contains(tf[0].toLowerCase())) {
			return true;
		} else if (text.toLowerCase().contains(tf[1].toLowerCase())) {
			return false;
		} else {
			System.out.println("no boolean in string, returning false");
			return false;
		}
	}

	/**
	 * @param text,
	 *            sw
	 * @return the string from text containing the string sw
	 */
	public String getString(String[] text, String sw) {
		for (String a : text)
			if (a.toLowerCase().contains(sw.toLowerCase()))
				return a;
		System.out.println("Unable to find " + sw + " in array!");
		return "";
	}

	/**
	 * 
	 * */
	public String getRestOfString(String first, String s) {
		if (s.substring(0, first.length()).equals(first)) {
			return s.substring(first.length());
		} else {
			for (int i = 1; i < s.length() - first.length(); i++) {
				if (s.substring(i, i + first.length()).equals(first)) {
					return s.substring(i + first.length());
				}
			}
			return null;
		}

	}

	/**
	 * 
	 * */
	public String getSubString(String[] text, String sw) {
		return getRestOfString(sw, getString(text, sw));
	}
}
