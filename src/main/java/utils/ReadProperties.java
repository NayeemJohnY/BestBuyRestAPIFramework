package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	public static String propertyValue;
	
	/**
	 * This method read the properties form the properties file
	 * @param key - String - key of the property value
	 * @return propertyValue - String - the value of given property key
	 */
	public static String getProperties(String key) {
		try {
			FileInputStream fileInput = new FileInputStream("./src/main/resources/Settings.properties");
			Properties prop = new Properties();
			prop.load(fileInput);
			propertyValue = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			propertyValue = null;
		}

		return propertyValue;
	}

}
