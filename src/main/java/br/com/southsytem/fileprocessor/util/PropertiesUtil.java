package br.com.southsytem.fileprocessor.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import br.com.southsytem.fileprocessor.exceptions.BusinessException;


/**
 * Classe responsável por recuperar as configurações do arquivo config.properties.
 * @author luis
 *
 */
public class PropertiesUtil {

	
	/**
	 * Get the extensions.allowed value or a list with the default '.dat' if error occurs.  
	 * @return a list with the extensions allowed
	 */
	public static List<String> getExtensionsAllowed() {
		List<String> extensions = new ArrayList<>();
		try {
			extensions.addAll(Arrays.asList(getProperty("extensions.allowed").toLowerCase().split(",")));
		}catch (Exception e) {
			extensions.add(".dat");
		}
		return extensions;
	}
	
	
	/**
	 * Get the home.env.variable value or default 'HOMEPATH' if error occurs.  
	 * @return
	 */
	public static String getHomeVariable(){
		try {
			return getProperty("home.env.variable");
		}catch (Exception e) {
			return "HOMEPATH";
		}
	}
	
	
	/**
	 * Get the base.directory value or default 'data' if error occurs.  
	 * @return
	 */
	public  static String getBaseDirectory() {
		try {
			return getProperty("base.directory");
		}catch (Exception e) {
			return "data";
		}
	}

	
	/**
	 * Get the output.directory value or default 'out' if error occurs.  
	 * @return
	 */
	public  static String getOutputDirectory() {
		try {
			return getProperty("output.directory");
		}catch (Exception e) {
			return "out";
		}
	}
	
	
	/**
	 * Get the input.directory value or default 'in' if error occurs.  
	 * @return
	 */
	public  static String getInputDirectory() {
		try {
			return getProperty("input.directory");
		}catch (Exception e) {
			return "in";
		}
	}

	
	/**
	 * Get the error.directory value or default 'err' if error occurs.  
	 * @return
	 */
	public  static String getErrorDirectory() {
		try {
			return getProperty("error.directory");
		}catch (Exception e) {
			return "err";
		}
	}
		
	
	/**
	 * Get the log.directory value or default 'log' if error occurs.  
	 * @return
	 */
	public  static String getLogDirectory() {
		try {
			return getProperty("log.directory");
		}catch (Exception e) {
			return "log";
		}
	}
	/**
	 * Retrieves the value of a property with the name informed.
	 * 
	 * @param name
	 * @return
	 * @throws BusinessException if property name does not exists in config file.
	 */
	private  static String getProperty(String name) throws BusinessException {
		Properties prop = getInstance();
		if(prop.keySet().contains(name)) {
			return prop.getProperty(name);
		}else {
			 throw new BusinessException("Unable to find the key '" + name + " in 'config.properties' file.");
		}
		
	}

	/**
	 * Retrieves a 'Properties' instance with the reference to 'config.properties' file. 
	 * @return Properties instance
	 * @throws BusinessException if file is not found or error occurs.
	 */
	private  static Properties getInstance()  throws BusinessException {
		Properties prop = new Properties();
		// PropertiesUtil pu = new PropertiesUtil();
		try (InputStream input = prop.getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				throw new BusinessException("Unable to find 'config.properties' file. Using Default values.");
			}
			prop.load(input);
			return prop;
		} catch (Exception e) {
			throw new BusinessException("Unable to load 'config.properties' file. Using Default values.");
		}
	}

}
