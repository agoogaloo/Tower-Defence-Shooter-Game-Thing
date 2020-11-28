package saveData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class SaveData {
	private static boolean finishedTutorial;
	private static Properties propertiesFile=new Properties();
	private static String settingsPath="res/saves/saveData.properties";
	public SaveData() {
		reload();
	}
	
	private static Properties loadProperties(String path) {
		Properties file=new Properties();
		try {
			file.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			System.out.println("save file at '"+path+"' not found");
		} catch (IOException e) {
			System.out.println("uh oh something bad happened");
			e.printStackTrace();
		}
		return file;
	}
	public static void writeproperties(Properties newProperties) {
		//this method rewrites the settings file to a new one that is given to it
		try {
			newProperties.store(new FileWriter(settingsPath), "this file holds all the save data (curently only if you've beaten the tutorial)");
		} catch (IOException e) {
			System.out.println("couldnt updtate proprties file");
			e.printStackTrace();
		}
		//updates everything to all the new settings
		reload();
	}
	public static void reload() {
		propertiesFile=loadProperties(settingsPath);
		
		//setting all the variables from the properties file
		finishedTutorial=Boolean.valueOf(propertiesFile.getProperty("tutorial"));
		System.out.println(Boolean.valueOf("true")+", "+propertiesFile.getProperty("tutorial"));
	}
	
	
	public static boolean isFinishedTutorial() {
		return finishedTutorial;
	}
	public static void tutorialDone() {
		finishedTutorial=true;
		propertiesFile.setProperty("tutorial", "true");
		writeproperties(propertiesFile);
	}
	
	public static Properties getProperties() {
		return propertiesFile;
	}
	
	
}
