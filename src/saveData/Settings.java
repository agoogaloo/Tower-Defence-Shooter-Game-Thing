package saveData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static int screenShake, scale, camSpeed, aimLook;
	private static int effectVol, musicVol;
	private static String towerInfo;
	private static Properties propertiesFile=new Properties();
	private static String settingsPath="res/saves/settings.properties", defaultSettings = "res/saves/defaultSettings.properties" ;
	
	public Settings() {
		reload();
	}
	
	private static Properties loadProperties(String path) {
		Properties file=new Properties();
		try {
			file.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			System.out.println("settings file at '"+path+"' not found");
		} catch (IOException e) {
			System.out.println("uh oh something bad happened");
			e.printStackTrace();
		}
		return file;
	}
	
	public static void writeProperties(Properties newProperties) {
		//this method rewrites the settings file to a new one that is given to it
		try {
			newProperties.store(new FileWriter(settingsPath), "this file holds all the settings like screen shake");
			System.out.println("stored changes"+newProperties.getProperty("screenShake"));
		} catch (IOException e) {
			System.out.println("couldnt update properties file");
			e.printStackTrace();
		}
		//updates everything to all the new settings
		reload();
	}
	
	public static void reload() {
		propertiesFile=loadProperties(settingsPath);
		
		//setting all the variables from the properties file
		screenShake=Integer.parseInt(propertiesFile.getProperty("screenShake"));
		towerInfo=propertiesFile.getProperty("towerInfo");
		scale=Integer.parseInt(propertiesFile.getProperty("pixelScale"));
		camSpeed=Integer.parseInt(propertiesFile.getProperty("cameraSpeed"));
		aimLook=Integer.parseInt(propertiesFile.getProperty("aimLook"));
		effectVol=Integer.parseInt(propertiesFile.getProperty("effectVol"));
		musicVol=Integer.parseInt(propertiesFile.getProperty("musicVol"));
		
		
	}
	
	public static void resetData(){
		Properties defaultFile = loadProperties(defaultSettings);
		System.out.println(defaultFile.getProperty("towerInfo"));
		writeProperties(defaultFile);
		
		
	}
	
	
	
	//getters for all the settings
	public static int getScreenShake() {
		return screenShake;
	}
	public static int getScale() {
		return scale;
	}
	public static String getTowerInfo() {
		return towerInfo;
	}
	
	public static Properties getProperties() {
		return propertiesFile;
	}
	public static int getCamSpeed() {
		return camSpeed;
	}
	public static int getAimLook() {
		return aimLook;
	}
	
	
	
}
