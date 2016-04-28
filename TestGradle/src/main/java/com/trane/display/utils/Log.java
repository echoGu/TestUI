package com.trane.display.utils;

/**
 * This class is designed to log different kinds of informations when programming is running.
 * 
 * @author irblir
 * @since 2016-04-22
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class Log {
	private final Class<?> clazz;
	private Logger logger;
    private String configfile = System.getProperty("user.dir")
			+ "\\config"
			+"\\log4j2.xml";

    public Log(Class<?> clazz) 
    {
		this.clazz = clazz;
		config();
		logger = LogManager.getLogger(this.clazz);
	}
    
	public void config()
	{
        ConfigurationSource source = null;
		try 
		{
			source = new ConfigurationSource(new FileInputStream(configfile));
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
        Configurator.initialize(null, source);
	}
	
	public void info(String message) 
	{
		logger.info(clazz.getCanonicalName() + ": " + message);
	}

	public void debug(String message) 
	{
		logger.debug(clazz.getCanonicalName() + ": " + message);
	}


	public void error(String message) 
	{
		logger.error(clazz.getCanonicalName() + ": " + message);
	}


	public void trace(String message) 
	{
		logger.trace(clazz.getCanonicalName() + ": " + message);
	}


	public void warn(String message) 
	{
		logger.warn(clazz.getCanonicalName() + ": " + message);
	}


	public void fatal(String message) 
	{
		logger.fatal(clazz.getCanonicalName() + ": " + message);
	}
	
	
	

	
}
