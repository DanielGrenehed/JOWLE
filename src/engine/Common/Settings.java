package engine.Common;

import javax.swing.JOptionPane;

import engine.Main;
import engine.ReadWrite.Convert;
import engine.ReadWrite.Read;
import engine.ReadWrite.Write;
import engine.Window.Frame;
import engine.Window.WindowOptions;

public class Settings {

	Convert convert;
	Main mn;
	WindowOptions wo;
	Frame frame;

	public Settings(Main fmn, Convert cv, WindowOptions wl, Frame f) {
		mn = fmn;
		convert = cv;
		wo = wl;
		frame = f;
	}

	boolean setupSuccess = true;

	String[] SettingsList = { "Threads", "Fullscreen", "Borderless", "Scale", "CustomFrameSize", "Width", "Height",
			"AudioChannels", "Developer", "ExceptionLogger", "Antialiasing", "GameOrderXML" };

	/**
	 * sets up the game-window and game settings from the settings-file from
	 * Main
	 */
	public void setup() {
		String[] stringsettings = Read.ReadText(mn.getSettingsFile());
		double mem;

		mem = convert.getDoubleFromArray(stringsettings, SettingsList[5]);
		mn.setWIDTH((int) legalize(mem, 100)); // sets width

		mem = convert.getDoubleFromArray(stringsettings, SettingsList[6]);
		mn.setHEIGHT((int) legalize(mem, 100)); // sets height

		mem = convert.getDoubleFromArray(stringsettings, SettingsList[0]);
		mn.setNumberOfThreads((int) legalize(mem, 6)); // threads

		mn.setFullscreen(convert.getBooleanFromArray(stringsettings, SettingsList[1]));// sets
																						// fullscreen
		mn.setBorderless(convert.getBooleanFromArray(stringsettings, SettingsList[2]));// sets
																						// borderless

		mem = convert.getDoubleFromArray(stringsettings, SettingsList[3]);
		mn.setScale(legalize(mem, 1.0));// set scale

		if (!mn.getFullscreen())
			mn.setCustomFrameSize(convert.getBooleanFromArray(stringsettings, SettingsList[4])); // sets
																									// custom
																									// frame
																									// size

		mem = convert.getDoubleFromArray(stringsettings, SettingsList[7]);

		mn.setDevelopermode(convert.getBooleanFromArray(stringsettings, SettingsList[8]));// sets
																							// developermode
		mn.setLogExceptions(convert.getBooleanFromArray(stringsettings, SettingsList[9]));// log
																							// exceptions,
																							// not
																							// working
																							// yet
		mn.setAntialiased(convert.getBooleanFromArray(stringsettings, SettingsList[10])); // set
																							// antialiased

		mn.setOrderDoc(convert.getSubString(stringsettings, SettingsList[11] + ": "));

		if (mn.getFullscreen()) {
			wo.setFullscreen();
		} else if (mn.getCustomframeSize()) {
			wo.setCustomSize(mn.getWIDTH(), mn.getHEIGHT());
		} else {
			wo.setAsScreenFormat(mn.getWIDTH());
		}

		if (mn.getBorderless()) {
			wo.setBorderless(true);
		}

		mn.setWIDTH(wo.getWIDTH());
		mn.setHEIGHT(wo.getHEIGHT());

		if (!setupSuccess) {
			if (JOptionPane.showConfirmDialog(frame,
					"Was not able to Complete setup! Want to reset the " + mn.getSettingsFile() + "?") == 1) {
				setupSuccess = true;
			} else {
				System.exit(0);
			}
		}
	}

	/**
	 * returns a double that is equal or higher than the minimum
	 * 
	 * @param n,
	 *            minimum
	 */
	public double legalize(double n, double minimum) {
		if (n < minimum) {
			return minimum;
		}
		return n;
	}

	/**
	 * Saves all the settings from memory
	 */
	public void save() {
		if (setupSuccess) {
			String[] settingsS = new String[SettingsList.length];

			for (int i = 0; i < settingsS.length; i++) {
				settingsS[i] = SettingsList[i] + ": ";
			}
			settingsS[0] += mn.getNumberOfThreads();
			settingsS[1] += mn.getFullscreen();
			settingsS[2] += mn.getBorderless();
			settingsS[3] += mn.getScale();
			settingsS[4] += mn.getCustomframeSize();
			settingsS[5] += mn.getWIDTH();
			settingsS[6] += mn.getHEIGHT();
			settingsS[7] += mn.getNumberOfAudioChannels();
			settingsS[8] += mn.isDevelopermode();
			settingsS[9] += mn.isLogExceptions();
			settingsS[10] += mn.isAntialiased();
			if (mn.getOrderDoc() == null | mn.getOrderDoc() == "")
				settingsS[11] += "order.xml";
			else {
				settingsS[11] += mn.getOrderDoc();
			}
			Write.writeStringArray(settingsS, mn.getSettingsFile());
		}
	}

}
